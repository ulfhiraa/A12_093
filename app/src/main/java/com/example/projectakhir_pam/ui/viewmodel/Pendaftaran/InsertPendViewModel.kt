package com.example.projectakhir_pam.ui.viewmodel.Pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.repository.PendaftaranRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Menambahkan waktu
val date = Date() // Ambil tanggal saat ini
val formattedDate = dateFormat.format(date)

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
            id_pendaftaran = if (event.id_pendaftaran.isNotEmpty()) null else "ID Pendaftaran tidak boleh kosong",
            id_siswa = if (event.id_siswa.isNotEmpty()) null else "ID Siswa tidak boleh kosong",
            id_kursus = if (event.id_kursus.isNotEmpty()) null else "ID Kursus tidak boleh kosong",
            tglDaftar = if (event.tglDaftar.isNotEmpty()) null else "Tanggal Pendaftaran tidak boleh kosong"
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

// Fungsi untuk mendapatkan tanggal dan waktu saat ini dengan format yang konsisten
fun getCurrentDateTime(): String = dateFormat.format(Date())

// Fungsi untuk mengubah String tanggal dengan waktu ke format Date
fun parseDateTime(dateStr: String): String {
    return try {
        val parsedDate = dateFormat.parse(dateStr) ?: Date()
        dateFormat.format(parsedDate)
    } catch (e: Exception) {
        e.printStackTrace() // Menangani kesalahan jika parsing gagal
        dateFormat.format(Date()) // Mengembalikan tanggal dan waktu saat ini jika terjadi kesalahan
    }
}

data class InsertPendUiState(
    val insertPendUiEvent: InsertPendUiEvent = InsertPendUiEvent(),
    val isEntryValid: FormErrorPendState = FormErrorPendState()
)

data class FormErrorPendState(
    val id_pendaftaran: String? = null,
    val id_siswa: String? = null,
    val id_kursus: String? = null,
    val tglDaftar: String? = null,
) {
    fun isValid(): Boolean {
        return id_pendaftaran == null && id_siswa == null
                && id_kursus == null && tglDaftar == null
    }
}

// Data class untuk menyimpan informasi input pengguna terkait data pendaftaran
data class InsertPendUiEvent(
    val id_pendaftaran: String = "",
    val id_siswa: String = "",
    val id_kursus: String = "",
    val tglDaftar: String = getCurrentDateTime(), // Menggunakan fungsi untuk mendapatkan tanggal dan waktu default
)

// Konversi dari event input pengguna (InsertPendUiEvent) menjadi entitas pendaftaran
fun InsertPendUiEvent.toPend(): Pendaftaran = Pendaftaran(
    id_pendaftaran = id_pendaftaran,
    id_siswa = id_siswa,
    id_kursus = id_kursus,
    tglDaftar = parseDateTime(tglDaftar) // Menggunakan fungsi parseDateTime untuk menangani konversi tanggal dan waktu
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
    tglDaftar = dateFormat.format(tglDaftar), // Konversi dari Date ke String
)
