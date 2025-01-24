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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
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
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // TEXTFIELD ID Pendaftaran
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPendUiEvent.id_pendaftaran,
            onValueChange = {
                onValueChange(insertPendUiEvent.copy(id_pendaftaran = it))
            },
            label = { Text("ID Pendaftaran") },
            isError = errorState.id_pendaftaran != null,
            placeholder = { Text("Masukkan ID Pendaftaran") },
        )
        Text(
            text = errorState.id_pendaftaran ?: "",
            color = Color.Red
        )

        // TEXTFIELD ID Siswa
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPendUiEvent.id_siswa,
            onValueChange = {
                onValueChange(insertPendUiEvent.copy(id_siswa = it))
            },
            label = { Text("ID Siswa") },
            isError = errorState.id_siswa != null,
            placeholder = { Text("Masukkan ID Siswa") },
        )
        Text(
            text = errorState.id_siswa ?: "",
            color = Color.Red
        )

        // TEXTFIELD ID Kursus
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPendUiEvent.id_kursus,
            onValueChange = {
                onValueChange(insertPendUiEvent.copy(id_kursus = it))
            },
            label = { Text("ID Kursus") },
            isError = errorState.id_kursus != null,
            placeholder = { Text("Masukkan ID Kursus") },
        )
        Text(
            text = errorState.id_kursus ?: "",
            color = Color.Red
        )

        // TEXTFIELD Tgl Pendaftaran
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPendUiEvent.tglDaftar,
            onValueChange = {
                onValueChange(insertPendUiEvent.copy(tglDaftar = it))
            },
            label = { Text("Tanggal dan waktu pendaftaran") },
            isError = errorState.tglDaftar != null,
            placeholder = { Text("Masukkan tanggal dan waktu pendaftaran") }
        )
        Text(
            text = errorState.tglDaftar ?: "",
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