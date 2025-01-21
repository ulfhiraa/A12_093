package com.example.projectakhir_pam.ui.viewmodel.Instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.repository.InstrukturRepository
import kotlinx.coroutines.launch
import java.io.IOException

// HomeViewModel : mengelola data dan status tampilan (UI) terkait data instruktur

sealed class HomeInstUiState {
    data class Success(val instruktur: List<Instruktur>) : HomeInstUiState()
    object Error : HomeInstUiState()
    object Loading : HomeInstUiState()
}

class HomeInstViewModel(private val inst: InstrukturRepository) : ViewModel() {
    var instUIState: HomeInstUiState by mutableStateOf(HomeInstUiState.Loading)
        private set

    init {
        getInst() // untuk mengambil daftar instruktur dari repository (InstrukturRepository)
    }

    fun getInst() {
        viewModelScope.launch {
            instUIState = HomeInstUiState.Loading
            instUIState = try {
                HomeInstUiState.Success(inst.getInstruktur().data)
            } catch (e: IOException) {
                HomeInstUiState.Error
            } catch (e: HttpException) {
                HomeInstUiState.Error
            }
        }
    }

    //untuk menghapus data instruktur berdasarkan ID yang diberikan
    fun deleteInst(id_instruktur: String) {
        viewModelScope.launch {
            try {
                inst.deleteInstruktur(id_instruktur)
            } catch (e: IOException){
                HomeInstUiState.Error
            } catch (e: HttpException){
                HomeInstUiState.Error
            }
        }
    }
}