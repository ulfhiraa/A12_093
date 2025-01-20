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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertUiEvent
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertViewModel
import kotlinx.coroutines.launch

// insert view : menambahkan data siswa baru

// untuk mendefinisikan rute navigasi ke layar 'Tambah Siswa'
object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Tambah Siswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySisScreen( //  untuk menambahkan data siswa dengan form input, navigasi kembali, dan tombol simpan
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.
        nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onMhsValueChange = viewModel::updateInsertSisState,
            onSaveClick = {
                coroutineScope.
                launch {
                    viewModel.insertSis()
                    navigateBack()
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
    insertUiState: InsertUiState,
    onMhsValueChange: (InsertUiEvent) -> Unit,
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
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onMhsValueChange,
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
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // TEXTFIELD ID Siswa
        OutlinedTextField(
            value = insertUiEvent.id_siswa,
            onValueChange = { onValueChange(insertUiEvent.copy(id_siswa = it)) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // tipe keyboard angka
            label = { Text("ID Siswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // TEXTFIELD Nama Siswa
        OutlinedTextField(
            value = insertUiEvent.namaSiswa,
            onValueChange = { onValueChange(insertUiEvent.copy(namaSiswa = it)) },
            label = { Text("Nama Siswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // TEXTFIELD Email
        OutlinedTextField(
            value = insertUiEvent.email,
            onValueChange = { onValueChange(insertUiEvent.copy(email = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // tipe keyboard email
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // TEXTFIELD Nomor Telepon
        OutlinedTextField(
            value = insertUiEvent.noTelpSiswa,
            onValueChange = { onValueChange(insertUiEvent.copy(noTelpSiswa = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // tipe keyboard angka
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
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