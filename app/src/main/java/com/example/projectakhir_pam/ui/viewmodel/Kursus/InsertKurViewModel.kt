package com.example.projectakhir_pam.ui.viewmodel.Kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.repository.KursusRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengelola input data kursus
class InsertKurViewModel(private val kur: KursusRepository) : ViewModel() {
    var kuruiState by mutableStateOf(InsertKurUiState())
        private set

    // Memperbarui state UI berdasarkan input terbaru pengguna
    fun updateInsertKurState(insertKurUiEvent: InsertKurUiEvent) {
        kuruiState = kuruiState.copy(insertKurUiEvent = insertKurUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = kuruiState.insertKurUiEvent
        val errorState = FormErrorKurState(
            namaKursus = if (event.namaKursus.isNotEmpty()) null else "Nama Kursus tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            kategori = if (event.kategori.isNotEmpty()) null else "Kategori tidak boleh kosong",
            harga = if (event.harga != null && event.harga > 0.0) null else "Harga tidak valid",
            id_instruktur = if (event.id_instruktur.isNotEmpty()) null else "ID Instruktur tidak boleh kosong"
        )
        kuruiState = kuruiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data kursus ke repository secara asynchronous
    fun insertKur() {
        viewModelScope.launch {
            try {
                kur.insertKursus(kuruiState.insertKurUiEvent.toKur())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Representasi state UI untuk halaman input data kursus
data class InsertKurUiState(
    val insertKurUiEvent: InsertKurUiEvent = InsertKurUiEvent(),
    val isEntryValid: FormErrorKurState = FormErrorKurState()
)

// Data class untuk menangani error pada form
data class FormErrorKurState(
    val namaKursus: String? = null,
    val deskripsi: String? = null,
    val kategori: String? = null,
    val harga: String? = null,
    val id_instruktur: String? = null
) {
    fun isValid(): Boolean {
        return  namaKursus == null
                && deskripsi == null && kategori == null
                && harga == null && id_instruktur == null
    }
}

// Data class untuk menyimpan informasi input pengguna terkait data kursus
data class InsertKurUiEvent(
    val id_kursus: String = "",
    val namaKursus: String = "",
    val deskripsi: String = "",
    val kategori: String = "",
    val harga: Double? = null, // Mengubah tipe data menjadi Double?
    val id_instruktur: String = ""
)

// Konversi dari event input pengguna (InsertKurUiEvent) menjadi entitas kursus (Kursus)
fun InsertKurUiEvent.toKur(): Kursus = Kursus(
    id_kursus = id_kursus,
    namaKursus = namaKursus,
    deskripsi = deskripsi,
    kategori = kategori,
    harga = harga ?: 0.0, // Memberikan default nilai 0.0 jika null
    id_instruktur = id_instruktur
)

// Konversi dari entitas kursus (Kursus) menjadi status UI (InsertKurUiState)
fun Kursus.toUiStateKur(): InsertKurUiState = InsertKurUiState(
    insertKurUiEvent = toInsertUiEvent()
)

// Konversi dari entitas kursus (Kursus) menjadi event input pengguna (InsertKurUiEvent)
fun Kursus.toInsertUiEvent(): InsertKurUiEvent = InsertKurUiEvent(
    id_kursus = id_kursus,
    namaKursus = namaKursus,
    deskripsi = deskripsi,
    kategori = kategori,
    harga = harga, // nilai Double
    id_instruktur = id_instruktur
)
