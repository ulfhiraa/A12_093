package com.example.projectakhir_pam.ui.viewmodel.Kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.repository.KursusRepository
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiDetailKur
import kotlinx.coroutines.launch

// Detail viewmodel : untuk mengelola pengambilan dan penghapusan data kursus berdasarkan id dan pembaruan UI State

class DetailKurViewModel(savedStateHandle: SavedStateHandle,
                          private val kursusRepository: KursusRepository
) : ViewModel()
{
    private val id_kursus: String = checkNotNull(savedStateHandle[DestinasiDetailKur.id_kursus])

    var detailKurUiState: DetailKurUiState by mutableStateOf(DetailKurUiState())
        private set

    init {
        getKursusById()
    }

    fun getKursusById() {
        viewModelScope.launch {
            detailKurUiState = DetailKurUiState(isLoading = true)
            try {
                val result = kursusRepository.getKursusById(id_kursus)
                detailKurUiState = DetailKurUiState(
                    detailKurUiEvent = result.toDetailKurUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailKurUiState = DetailKurUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }

    fun deleteKur() {
        viewModelScope.launch {
            detailKurUiState = DetailKurUiState(isLoading = true)
            try {
                kursusRepository.deleteKursus(id_kursus)

                detailKurUiState = DetailKurUiState(isLoading = false)
            } catch (e: Exception) {
                detailKurUiState = DetailKurUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailKurUiState(
    val detailKurUiEvent: InsertKurUiEvent = InsertKurUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailKurUiEvent == InsertKurUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailKurUiEvent != InsertKurUiEvent()
}

fun Kursus.toDetailKurUiEvent(): InsertKurUiEvent {
    return InsertKurUiEvent(
        id_kursus = id_kursus,
        namaKursus = namaKursus,
        deskripsi = deskripsi,
        kategori = kategori,
        harga = harga, // nilai Double
        id_instruktur = id_instruktur
    )
}