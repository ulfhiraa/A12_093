package com.example.projectakhir_pam.ui.viewmodel.Siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.repository.SiswaRepository
import kotlinx.coroutines.launch

// insert viewmodel : mengelola input data siswa

class InsertSisViewModel(private val sis: SiswaRepository): ViewModel(){
    var sisuiState by mutableStateOf(InsertSisUiState())
        private set

    // untuk memperbarui status UI berdasarkan input terbaru dari pengguna
    fun updateInsertSisState(insertSisUiEvent: InsertSisUiEvent){
        sisuiState = InsertSisUiState(insertSisUiEvent = insertSisUiEvent)
    }

    // untuk menyimpan data siswa ke dalam repository secara asynchronous dengan penanganan kesalahan
    fun insertSis(){
        viewModelScope.launch {
            try {
                sis.insertSiswa(sisuiState.insertSisUiEvent.toSis())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

// mewakili keadaan (state) UI untuk halaman input data siswa
data class InsertSisUiState(
    val insertSisUiEvent: InsertSisUiEvent = InsertSisUiEvent()
)

//  menyimpan informasi inputan pengguna terkait data siswa
data class InsertSisUiEvent(
    val id_siswa: String = "",
    val namaSiswa: String = "",
    val email: String = "",
    val noTelpSiswa: String = ""
)

// konversi dari event input pengguna (InsertUiEvent) menjadi entitas siswa (Siswa)
fun InsertSisUiEvent.toSis(): Siswa = Siswa(
    id_siswa = id_siswa,
    namaSiswa = namaSiswa,
    email = email,
    noTelpSiswa = noTelpSiswa
)

//  konversi dari entitas siswa (Siswa) menjadi status UI (InsertUiState)
fun Siswa.toUiStateSis(): InsertSisUiState = InsertSisUiState(
    insertSisUiEvent = toInsertUiEvent()
)

// konversi dari entitas siswa (Siswa) menjadi event input pengguna (InsertUiEvent)
fun Siswa.toInsertUiEvent(): InsertSisUiEvent = InsertSisUiEvent(
    id_siswa = id_siswa,
    namaSiswa = namaSiswa,
    email = email,
    noTelpSiswa = noTelpSiswa
)