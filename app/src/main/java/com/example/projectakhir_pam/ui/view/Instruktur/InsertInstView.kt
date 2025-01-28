package com.example.projectakhir_pam.ui.view.Instruktur

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
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.FormErrorState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.InsertInstUiEvent
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.InsertInstUiState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.InsertInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// insert view : untuk menampilkan form input data instruktur baru

// untuk mendefinisikan rute navigasi ke layar 'Tambah Instruktur'
object DestinasiEntryInst : DestinasiNavigasi {
    override val route = "tambahInst"
    override val titleRes = "Tambah Instruktur" // judul halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertInstView( // Fungsi untuk tampilan halaman input data instruktur
    navigateBack: () -> Unit, // Fungsi untuk kembali ke halaman sebelumnya
    modifier: Modifier = Modifier, // Modifier untuk mengubah tampilan komponen
    viewModel: InsertInstViewModel = viewModel(factory = PenyediaViewModel.Factory) // Menggunakan ViewModel untuk mengelola data
) {
    val coroutineScope = rememberCoroutineScope() // Membuat scope untuk menjalankan operasi asinkron (seperti validasi dan menyimpan data)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Mengatur perilaku scroll untuk top app bar

    // mengatur layout topbar dan body
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Menambahkan scroll behavior ke layout
        topBar = {
            // Menambahkan AppBar di bagian atas halaman dengan judul dan tombol kembali
            CustomeTopAppBar(
                title = DestinasiEntryInst.titleRes, // Judul untuk halaman
                canNavigateBack = true, // Mengaktifkan tombol kembali
                scrollBehavior = scrollBehavior, // Menggunakan scroll behavior agar AppBar bisa menghilang saat scroll
                navigateUp = navigateBack // Menentukan fungsi yang dijalankan saat tombol kembali ditekan
            )
        }
    ) { innerPadding ->
        // Bagian Body halaman untuk menampilkan form input
        EntryBody(
            insertInstUiState = viewModel.instuiState, // Menyediakan state untuk menampilkan UI berdasarkan status data
            onInstValueChange = viewModel::updateInsertInstState, // Fungsi untuk mengupdate data instruktur saat ada perubahan input
            onSaveClick = { // Fungsi yang dijalankan saat tombol simpan ditekan
                coroutineScope.launch { // Menjalankan proses asinkron
                    if (viewModel.validateFields()) { // Memvalidasi form input
                        viewModel.insertInst() // Jika valid, data disimpan
                        navigateBack() // Kembali ke halaman sebelumnya
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding) // Menambahkan padding agar form tidak tertutup oleh elemen lain
                .verticalScroll(rememberScrollState()) // Membuat form bisa digulirkan secara vertikal
                .fillMaxWidth() // Membuat form memanfaatkan seluruh lebar layar
        )
    }
}

@Composable
fun EntryBody( // untuk mengatur tata letak dan logika formulir input data instruktur serta tombol simpan
    insertInstUiState: InsertInstUiState,
    onInstValueChange: (InsertInstUiEvent) -> Unit,
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
            insertInstUiEvent = insertInstUiState.insertInstUiEvent, // Input data instruktur
            onValueChange = onInstValueChange, // Menghandle perubahan data input
            errorState = insertInstUiState.isEntryValid, // Validasi form
            modifier = Modifier.fillMaxWidth() // Membuat lebar penuh untuk input
        )
        Button(
            onClick = onSaveClick, // menyimpan data instruktur
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
    insertInstUiEvent: InsertInstUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertInstUiEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // TEXTFIELD Nama Instruktur
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertInstUiEvent.namaInstruktur,
            onValueChange = {
                onValueChange(insertInstUiEvent.copy(namaInstruktur = it))
            },
            label = { Text("Nama Instruktur") },
            isError = errorState.namaInstruktur != null,
            placeholder = { Text("Masukkan Nama Instruktur") },
        )
        Text(
            text = errorState.namaInstruktur ?: "",
            color = Color.Red
        )

        // TEXTFIELD Email
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertInstUiEvent.email,
            onValueChange = {
                onValueChange(insertInstUiEvent.copy(email = it))
            },
            label = { Text("Email") },
            isError = errorState.email != null,
            placeholder = { Text("Masukkan Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // tipe keyboard email
        )
        Text(
            text = errorState.email ?: "",
            color = Color.Red
        )

        // TEXTFIELD Nomor Telepon
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertInstUiEvent.noTelpInst,
            onValueChange = {
                onValueChange(insertInstUiEvent.copy(noTelpInst = it))
            },
            label = { Text("Nomor Telepon") },
            isError = errorState.noTelpInst != null,
            placeholder = { Text("Masukkan Nomor Telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // tipe keyboard email
        )
        Text(
            text = errorState.noTelpInst ?: "",
            color = Color.Red
        )

        // TEXTFIELD Deskripsi
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertInstUiEvent.deskripsi,
            onValueChange = {
                onValueChange(insertInstUiEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi Keahlian") },
            isError = errorState.deskripsi != null,
            placeholder = { Text("Masukkan Deskripsi Keahlian") }
        )
        Text(
            text = errorState.deskripsi ?: "",
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