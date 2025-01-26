package com.example.projectakhir_pam.ui.viewmodel.Pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.projectakhir_pam.repository.PendaftaranRepository
import com.example.projectakhir_pam.model.Pendaftaran

// untuk  mengelola data dan status tampilan (UI) terkait data pendaftaran

sealed class HomePendUiState {
    data class Success(val pendaftaran: List<Pendaftaran>) : HomePendUiState()
    object Error : HomePendUiState()
    object Loading : HomePendUiState()
}

class HomePendViewModel(private val pend: PendaftaranRepository) : ViewModel() {
    var pendUIState: HomePendUiState by mutableStateOf(HomePendUiState.Loading)
        private set

    init {
        getPend() // untuk mengambil daftar pendaftaran dari repository (PendaftaranRepository)
    }

    fun getPend() {
        viewModelScope.launch {
            pendUIState = HomePendUiState.Loading
            pendUIState = try {
                HomePendUiState.Success(pend.getPendaftaran().data)
            } catch (e: IOException) {
                HomePendUiState.Error
            } catch (e: HttpException) {
                HomePendUiState.Error
            }
        }
    }

    //untuk menghapus data pendaftaran berdasarkan ID yang diberikan
    fun deletePend(id_pendaftaran: String) {
        viewModelScope.launch {
            try {
                pend.deletePendaftaran(id_pendaftaran)
            } catch (e: IOException){
                HomePendUiState.Error
            } catch (e: HttpException){
                HomePendUiState.Error
            }
        }
    }
}