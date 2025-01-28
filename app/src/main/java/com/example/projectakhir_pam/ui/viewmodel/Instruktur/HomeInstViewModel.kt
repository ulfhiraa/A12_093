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
    data class Success(val instruktur: List<Instruktur>) : HomeInstUiState() // Menyimpan daftar instruktur jika berhasil
    object Error : HomeInstUiState() // Status error
    object Loading : HomeInstUiState() // Status loading saat data sedang dimuat
}

class HomeInstViewModel(private val inst: InstrukturRepository) : ViewModel() {
    var instUIState: HomeInstUiState by mutableStateOf(HomeInstUiState.Loading)
        private set

    init {
        getInst() // untuk mengambil daftar instruktur dari repository (InstrukturRepository)
    }

    // Fungsi untuk mengambil data instruktur
    fun getInst() {
        viewModelScope.launch {
            instUIState = HomeInstUiState.Loading // Menetapkan status ke loading saat mengambil data
            instUIState = try {
                HomeInstUiState.Success(inst.getInstruktur().data) // Menyimpan data instruktur jika berhasil
            } catch (e: IOException) {
                HomeInstUiState.Error // Menyimpan status error jika terjadi IOException
            } catch (e: HttpException) {
                HomeInstUiState.Error // Menyimpan status error jika terjadi HttpException
            }
        }
    }

    // Fungsi untuk menghapus data instruktur berdasarkan ID
    fun deleteInst(id_instruktur: String) {
        viewModelScope.launch {
            try {
                inst.deleteInstruktur(id_instruktur) // Memanggil fungsi untuk menghapus instruktur dari repository
            } catch (e: IOException){
                HomeInstUiState.Error // Menyimpan status error jika terjadi IOException
            } catch (e: HttpException){
                HomeInstUiState.Error // Menyimpan status error jika terjadi HttpException
            }
        }
    }
}