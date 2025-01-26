package com.example.projectakhir_pam.ui.viewmodel.Instruktur

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.repository.InstrukturRepository
import kotlinx.coroutines.launch

// untuk mengelola input data instruktur
class InsertInstViewModel(private val inst: InstrukturRepository): ViewModel(){
    var instuiState by mutableStateOf(InsertInstUiState())
        private set

    // untuk memperbarui status UI berdasarkan input terbaru dari pengguna
    fun updateInsertInstState(insertInstUiEvent: InsertInstUiEvent){
        instuiState = InsertInstUiState(insertInstUiEvent = insertInstUiEvent)
    }

    // validasi input pengguna
    fun validateFields(): Boolean {
        val event = instuiState.insertInstUiEvent
        val errorState = FormErrorState(
            id_instruktur = if (event.id_instruktur.isNotEmpty()) null else "ID Instruktur tidak boleh kosong",
            namaInstruktur = if (event.namaInstruktur.isNotEmpty()) null else "Nama Instruktur tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email tidak boleh kosong",
            noTelpInst = if (event.noTelpInst.isNotEmpty()) null else "Nomor Telepon tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi keahlian tidak boleh kosong"
        )
        instuiState = instuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // untuk menyimpan data instruktur ke dalam repository secara asynchronous dengan penanganan kesalahan
    fun insertInst(){
        viewModelScope.launch {
            try {
                inst.insertInstruktur(instuiState.insertInstUiEvent.toInst())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

// mewakili keadaan (state) UI untuk halaman input data instruktur
data class InsertInstUiState(
    val insertInstUiEvent: InsertInstUiEvent = InsertInstUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

// data class  untuk menangani error pada form
data class FormErrorState(
    val id_instruktur: String? = null,
    val namaInstruktur: String? = null,
    val email: String? = null,
    val noTelpInst: String? = null,
    val deskripsi: String? = null
){
    fun isValid(): Boolean {
        return id_instruktur == null && namaInstruktur == null
                && email == null && noTelpInst == null && deskripsi == null
    }
}

//  menyimpan informasi inputan pengguna terkait data instruktur
data class InsertInstUiEvent(
    val id_instruktur: String = "",
    val namaInstruktur: String = "",
    val email: String = "",
    val noTelpInst: String = "",
    val deskripsi: String = ""
)

// konversi dari event input pengguna (InsertUiEvent) menjadi entitas instruktur (Instruktur)
fun InsertInstUiEvent.toInst(): Instruktur = Instruktur(
    id_instruktur = id_instruktur,
    namaInstruktur = namaInstruktur,
    email = email,
    noTelpInst = noTelpInst,
    deskripsi = deskripsi
)

//  konversi dari entitas instruktur (Instruktur) menjadi status UI (InsertUiState)
fun Instruktur.toUiStateInst(): InsertInstUiState = InsertInstUiState(
    insertInstUiEvent = toInsertUiEvent()
)

// konversi dari entitas instruktur (Instruktur) menjadi event input pengguna (InsertUiEvent)
fun Instruktur.toInsertUiEvent(): InsertInstUiEvent = InsertInstUiEvent(
    id_instruktur = id_instruktur,
    namaInstruktur = namaInstruktur,
    email = email,
    noTelpInst = noTelpInst,
    deskripsi = deskripsi
)