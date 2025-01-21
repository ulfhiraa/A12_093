package com.example.projectakhir_pam.ui.viewmodel.Siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.repository.SiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

// HomeViewModel : mengelola data dan status tampilan (UI) terkait data siswa
// ViewModel ini berinteraksi dengan SiswaRepository untuk mengambil atau menghapus data siswa dan memperbarui status UI.

// untuk menggambarkan status UI (apakah data sedang dimuat, berhasil dimuat, atau terjadi error).
sealed class HomeSisUiState {
    data class Success(val siswa: List<Siswa>) : HomeSisUiState()
    object Error : HomeSisUiState()
    object Loading : HomeSisUiState()
}

class HomeSisViewModel(private val sis: SiswaRepository) : ViewModel() {
    var sisUIState: HomeSisUiState by mutableStateOf(HomeSisUiState.Loading)
        private set

    init {
        getSis() // untuk mengambil daftar siswa dari repository (SiswaRepository)
    }

    fun getSis() {
        viewModelScope.launch {
            sisUIState = HomeSisUiState.Loading
            sisUIState = try {
                HomeSisUiState.Success(sis.getSiswa().data)
            } catch (e: IOException) {
                HomeSisUiState.Error
            } catch (e: HttpException) {
                HomeSisUiState.Error
            }
        }
    }

    //untuk menghapus data siswa berdasarkan ID yang diberikan
    fun deleteSis(id_siswa: String) {
        viewModelScope.launch {
            try {
                sis.deleteSiswa(id_siswa)
            } catch (e: IOException){
                HomeSisUiState.Error
            } catch (e: HttpException){
                HomeSisUiState.Error
            }
        }
    }
}