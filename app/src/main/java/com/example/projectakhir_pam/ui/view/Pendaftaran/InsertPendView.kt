package com.example.projectakhir_pam.ui.view.Pendaftaran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.data.KursusList
import com.example.projectakhir_pam.data.SiswaList
import com.example.projectakhir_pam.data.Status
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.customwidget.DropdownData
import com.example.projectakhir_pam.ui.customwidget.DropdownDataInt
import com.example.projectakhir_pam.ui.customwidget.DropdownStatus
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.FormErrorPendState
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.InsertPendUiEvent
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.InsertPendUiState
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.InsertPendViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// insert view : untuk menampilkan form input data pendaftaran baru

// untuk mendefinisikan rute navigasi ke layar 'Tambah Pendaftaran'
object DestinasiEntryPend : DestinasiNavigasi {
    override val route = "tambahPend"
    override val titleRes = "Tambah Pendaftaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPendView( //  untuk menambahkan data pendaftaran dengan form input, navigasi kembali, dan tombol simpan
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPendViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.
        nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryPend.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertPendUiState = viewModel.penduiState,
            onPendValueChange = viewModel::updateInsertPendState,
            onSaveClick = {
                coroutineScope.
                launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertPend()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody( // untuk mengatur tata letak dan logika formulir input data pendaftaran serta tombol simpan
    insertPendUiState: InsertPendUiState,
    onPendValueChange: (InsertPendUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier:
    Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .padding(12.dp)
    ) {
        FormInput(
            insertPendUiEvent = insertPendUiState.insertPendUiEvent,
            onValueChange = onPendValueChange,
            errorState = insertPendUiState.isEntryValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInput( // untuk menampilkan elemen input form dengan validasi
    insertPendUiEvent: InsertPendUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPendUiEvent) -> Unit = {},
    errorState: FormErrorPendState = FormErrorPendState(),
    enabled: Boolean = true
){

    var listStatus by remember { mutableStateOf("") } // menampilkan pilihan status

    var chosenDropdown by remember { mutableStateOf("") }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // DROPDOWN ID SISWA (Display Nama Siswa)
        DropdownDataInt(
            selectedValue = insertPendUiEvent.id_siswa,
            options = SiswaList.DataSiswa() ,
            label = "Pilih Siswa",
            onValueChangeEvent = { selected ->
                chosenDropdown  = selected
                onValueChange(insertPendUiEvent.copy(id_siswa = selected))
            }
        )
        Text(
            text = errorState.id_siswa ?: "",  // Menampilkan pesan error jika ada
            color = Color.Red
        )

        // DROPDOWN ID Kursus (Display Nama Kursus)
        DropdownData(
            selectedValue = insertPendUiEvent.id_kursus,
            options = KursusList.DataKursus() ,
            label = "Pilih Kursus",
            onValueChangeEvent = { selected ->
                chosenDropdown  = selected
                onValueChange(insertPendUiEvent.copy(id_kursus = selected))
            }
        )
        Text(
            text = errorState.id_kursus ?: "",  // Menampilkan pesan error jika ada
            color = Color.Red
        )

        // DROPDOWN STATUS PENDAFTARAN
        DropdownStatus(
            selectedValue = insertPendUiEvent.status,
            options = Status.listStatus,
            label = "Pilih Status Pendaftaran",
            onValueChangeEvent = { selected ->
                listStatus = selected
                onValueChange(insertPendUiEvent.copy(status = selected))
            }
        )

        Text(
            text = errorState.status ?: "",  // Menampilkan pesan error jika ada
            color = Color.Red
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}