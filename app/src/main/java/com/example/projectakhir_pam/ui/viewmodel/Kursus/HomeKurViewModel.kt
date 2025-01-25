package com.example.projectakhir_pam.ui.viewmodel.Kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.repository.KursusRepository
import kotlinx.coroutines.launch
import java.io.IOException

// untuk  mengelola data dan status tampilan (UI) terkait data kursus

sealed class HomeKurUiState {
    data class Success(val kursus: List<Kursus>) : HomeKurUiState()
    object Error : HomeKurUiState()
    object Loading : HomeKurUiState()
}

class HomeKurViewModel(private val kur: KursusRepository) : ViewModel() {
    var kurUIState: HomeKurUiState by mutableStateOf(HomeKurUiState.Loading)
        private set

    // cari data
    var searchResult: List<Kursus> by mutableStateOf(emptyList())
        private set


    init {
        getKur() // untuk mengambil daftar kursus dari repository (KursusRepository)
    }

    // Mengambil semua kursus
    fun getKur() {
        viewModelScope.launch {
            kurUIState = HomeKurUiState.Loading
            kurUIState = try {
                HomeKurUiState.Success(kur.getKursus().data)
            } catch (e: IOException) {
                HomeKurUiState.Error
            } catch (e: HttpException) {
                HomeKurUiState.Error
            }
        }
    }

    //untuk menghapus data kursus berdasarkan ID yang diberikan
    fun deleteKur(id_kursus: String) {
        viewModelScope.launch {
            try {
                kur.deleteKursus(id_kursus)
            } catch (e: IOException){
                HomeKurUiState.Error
            } catch (e: HttpException){
                HomeKurUiState.Error
            }
        }
    }

    // Pencarian kursus berdasarkan nama, kategori, atau instruktur
    fun searchKur(nama_kursus: String? = null, kategori: String? = null, id_instruktur: String? = null) {
        viewModelScope.launch {
            kurUIState = HomeKurUiState.Loading
            try {
                val response = kur.searchKursus(nama_kursus, kategori, id_instruktur)
                searchResult = response.data
            } catch (e: IOException) {
                kurUIState = HomeKurUiState.Error
            } catch (e: HttpException) {
                kurUIState = HomeKurUiState.Error
            }
        }
    }
}