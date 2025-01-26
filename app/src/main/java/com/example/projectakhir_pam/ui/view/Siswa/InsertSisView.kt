package com.example.projectakhir_pam.ui.view.Siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.FormErrorState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertSisUiEvent
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertSisViewModel
import kotlinx.coroutines.launch

// insert view : menambahkan data siswa baru

// untuk mendefinisikan rute navigasi ke layar 'Tambah Siswa'
object DestinasiEntrySis : DestinasiNavigasi {
    override val route = "tambahSiswa"
    override val titleRes = "Tambah Siswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertSisView( //  untuk menambahkan data siswa dengan form input, navigasi kembali, dan tombol simpan
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertSisViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.
        nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntrySis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertSisUiState = viewModel.sisuiState,
            onSisValueChange = viewModel::updateInsertSisState,
            onSaveClick = {
                coroutineScope.
                launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertSis()
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
fun EntryBody( // untuk mengatur tata letak dan logika formulir input data siswa serta tombol simpan
    insertSisUiState: InsertSisUiState,
    onSisValueChange: (InsertSisUiEvent) -> Unit,
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
            insertSisUiEvent = insertSisUiState.insertSisUiEvent,
            onValueChange = onSisValueChange,
            errorState = insertSisUiState.isEntryValid,
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
fun FormInput( // untuk menampilkan elemen input form seperti ID siswa, nama, email, dan nomor telepon dengan validasi
    insertSisUiEvent: InsertSisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertSisUiEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // TEXTFIELD ID Siswa
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = insertSisUiEvent.id_siswa,
//            onValueChange = {
//                onValueChange(insertSisUiEvent.copy(id_siswa = it))
//            },
//            label = { Text("ID Siswa")},
//            isError = errorState.id_siswa != null,
//            placeholder = { Text("Masukkan ID Siswa")},
//        )
//        Text(
//            text = errorState.id_siswa ?: "",
//            color = Color.Red
//        )

        // TEXTFIELD Nama Siswa
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertSisUiEvent.namaSiswa,
            onValueChange = {
                onValueChange(insertSisUiEvent.copy(namaSiswa = it))
            },
            label = { Text("Nama Siswa")},
            isError = errorState.namaSiswa != null,
            placeholder = { Text("Masukkan Nama Siswa")},
        )
        Text(
            text = errorState.namaSiswa ?: "",
            color = Color.Red
        )

        // TEXTFIELD Email
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertSisUiEvent.email,
            onValueChange = {
                onValueChange(insertSisUiEvent.copy(email = it))
            },
            label = { Text("Email")},
            isError = errorState.email != null,
            placeholder = { Text("Masukkan Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // tipe keyboard email
        )
        Text(
            text = errorState.email ?: "",
            color = Color.Red
        )

        // TEXTFIELD Nomor Telepon
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertSisUiEvent.noTelpSiswa,
            onValueChange = {
                onValueChange(insertSisUiEvent.copy(noTelpSiswa = it))
            },
            label = { Text("Nomor Telepon")},
            isError = errorState.noTelpSiswa != null,
            placeholder = { Text("Masukkan Nomor Telepon")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // tipe keyboard email
        )
        Text(
            text = errorState.noTelpSiswa ?: "",
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