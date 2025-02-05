package com.example.projectakhir_pam.ui.viewmodel.Pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.repository.PendaftaranRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengelola input data pendaftaran
class InsertPendViewModel(private val pend: PendaftaranRepository) : ViewModel() {
    var penduiState by mutableStateOf(InsertPendUiState())
        private set

    // Memperbarui state UI berdasarkan input terbaru pengguna
    fun updateInsertPendState(insertPendUiEvent: InsertPendUiEvent) {
        penduiState = penduiState.copy(insertPendUiEvent = insertPendUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = penduiState.insertPendUiEvent
        val errorState = FormErrorPendState(
            id_siswa = if (event.id_siswa.isNotEmpty()) null else "ID Siswa tidak boleh kosong",
            id_kursus = if (event.id_kursus.isNotEmpty()) null else "ID Kursus tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status pendaftaran tidak boleh kosong",
            kategori = if (event.kategori.isNotEmpty()) null else "kategori tidak boleh kosong"
        )
        penduiState = penduiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data pendaftaran ke repository secara asynchronous
    fun insertPend() {
        viewModelScope.launch {
            try {
                pend.insertPendaftaran(penduiState.insertPendUiEvent.toPend())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Representasi state UI untuk halaman input data pendaftaran
data class InsertPendUiState(
    val insertPendUiEvent: InsertPendUiEvent = InsertPendUiEvent(),
    val isEntryValid: FormErrorPendState = FormErrorPendState()
)

// Data class untuk menangani error pada form
data class FormErrorPendState(
    val id_siswa: String? = null,
    val id_kursus: String? = null,
    val status: String? = null,
    val kategori: String? = null
) {
    fun isValid(): Boolean {
        return  id_siswa == null
                && id_kursus == null
                && status == null
                && kategori == null
    }
}

// Data class untuk menyimpan informasi input pengguna terkait data pendaftaran
data class InsertPendUiEvent(
    val id_pendaftaran: String = "",
    val id_siswa: String = "",
    val id_kursus: String = "",
    val tglDaftar: String = "", // Tetap menggunakan String untuk input tanggal
    val status: String = "",
    val kategori: String = ""
)

// Konversi dari event input pengguna (InsertPendUiEvent) menjadi entitas pendaftaran
fun InsertPendUiEvent.toPend(): Pendaftaran = Pendaftaran(
    id_pendaftaran = id_pendaftaran,
    id_siswa = id_siswa,
    id_kursus = id_kursus,
    tglDaftar = tglDaftar, // Mengonversi String ke Date
    status = status,
    kategori = kategori
)

// Konversi dari entitas pendaftaran menjadi status UI (InsertPendUiState)
fun Pendaftaran.toUiStatePend(): InsertPendUiState = InsertPendUiState(
    insertPendUiEvent = toInsertUiEvent()
)

// Konversi dari entitas pendaftaran menjadi event input pengguna (InsertPendUiEvent)
fun Pendaftaran.toInsertUiEvent(): InsertPendUiEvent = InsertPendUiEvent(
    id_pendaftaran = id_pendaftaran,
    id_siswa = id_siswa,
    id_kursus = id_kursus,
    tglDaftar = tglDaftar,
    status = status,
    kategori = kategori
)