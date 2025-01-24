package com.example.projectakhir_pam.ui.viewmodel.Instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.repository.InstrukturRepository
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiDetailInst
import kotlinx.coroutines.launch

// Detail viewmodel : untuk mengelola pengambilan dan penghapusan data instruktur berdasarkan id dan pembaruan UI State

class DetailInstViewModel(savedStateHandle: SavedStateHandle,
                          private val instrukturRepository: InstrukturRepository
) : ViewModel()
{
    private val id_instruktur: String = checkNotNull(savedStateHandle[DestinasiDetailInst.id_instruktur])

    var detailInstUiState: DetailInstUiState by mutableStateOf(DetailInstUiState())
        private set

    init {
        getInstrukturById()
    }

    fun getInstrukturById() {
        viewModelScope.launch {
            detailInstUiState = DetailInstUiState(isLoading = true)
            try {
                val result = instrukturRepository.getInstrukturById(id_instruktur)
                detailInstUiState = DetailInstUiState(
                    detailInstUiEvent = result.toDetailInstUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailInstUiState = DetailInstUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }
}

data class DetailInstUiState(
    val detailInstUiEvent: InsertInstUiEvent = InsertInstUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailInstUiEvent == InsertInstUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailInstUiEvent != InsertInstUiEvent()
}

fun Instruktur.toDetailInstUiEvent(): InsertInstUiEvent {
    return InsertInstUiEvent(
        id_instruktur = id_instruktur,
        namaInstruktur = namaInstruktur,
        email = email,
        noTelpInst = noTelpInst,
        deskripsi = deskripsi
    )
}