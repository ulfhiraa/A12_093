package com.example.projectakhir_pam.ui.viewmodel.Siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiDetail
import kotlinx.coroutines.launch

// Detail viewmodel : untuk mengelola pengambilan dan penghapusan data siswa berdasarkan id dan pembaruan UI State

class DetailViewModel(savedStateHandle: SavedStateHandle,
                      private val siswaRepository: SiswaRepository) : ViewModel()
{
    private val id_siswa: String = checkNotNull(savedStateHandle[DestinasiDetail.id_siswa])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState())
        private set

    init {
        getSiswaById()
    }

    fun getSiswaById() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = siswaRepository.getSiswaById(id_siswa)
                detailUiState = DetailUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }

    fun deleteSis() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                siswaRepository.deleteSiswa(id_siswa)

                detailUiState = DetailUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Siswa.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        id_siswa = id_siswa,
        namaSiswa = namaSiswa,
        email = email,
        noTelpSiswa = noTelpSiswa
    )
}