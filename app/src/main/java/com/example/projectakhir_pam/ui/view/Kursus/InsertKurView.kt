package com.example.projectakhir_pam.ui.view.Kursus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.data.InstrukturList
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.customwidget.DropdownData
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Kursus.FormErrorKurState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.InsertKurUiEvent
import com.example.projectakhir_pam.ui.viewmodel.Kursus.InsertKurUiState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.InsertKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// insert view : untuk menampilkan form input data kursus baru

// untuk mendefinisikan rute navigasi ke layar 'Tambah Kursus'
object DestinasiEntryKur : DestinasiNavigasi {
    override val route = "tambahKursus"
    override val titleRes = "Tambah Kursus"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertKurView( //  untuk menambahkan data kursus dengan form input, navigasi kembali, dan tombol simpan
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.
        nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryKur.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertKurUiState = viewModel.kuruiState,
            onKurValueChange = viewModel::updateInsertKurState,
            onSaveClick = {
                coroutineScope.
                launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertKur()
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
fun EntryBody( // untuk mengatur tata letak dan logika formulir input data kursus serta tombol simpan
    insertKurUiState: InsertKurUiState,
    onKurValueChange: (InsertKurUiEvent) -> Unit,
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
            insertKurUiEvent = insertKurUiState.insertKurUiEvent,
            onValueChange = onKurValueChange,
            errorState = insertKurUiState.isEntryValid,
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
    insertKurUiEvent: InsertKurUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKurUiEvent) -> Unit = {},
    errorState: FormErrorKurState = FormErrorKurState(),
    enabled: Boolean = true
){

    var chosenDropdown by remember { mutableStateOf("") }

    val kategori = listOf("Saintek", "Soshum") // list kategori saintek/soshum

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
//        // TEXTFIELD ID Kursus
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = insertKurUiEvent.id_kursus,
//            onValueChange = {
//                onValueChange(insertKurUiEvent.copy(id_kursus = it))
//            },
//            label = { Text("ID Kursus") },
//            isError = errorState.id_kursus != null,
//            placeholder = { Text("Masukkan ID Kursus") },
//        )
//        Text(
//            text = errorState.id_kursus ?: "",
//            color = Color.Red
//        )

        // TEXTFIELD Nama Kursus
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertKurUiEvent.namaKursus,
            onValueChange = {
                onValueChange(insertKurUiEvent.copy(namaKursus = it))
            },
            label = { Text("Nama Kursus") },
            isError = errorState.namaKursus != null,
            placeholder = { Text("Masukkan Nama Kursus") },
        )
        Text(
            text = errorState.namaKursus ?: "",
            color = Color.Red
        )

        // TEXTFIELD Deskripsi
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertKurUiEvent.deskripsi,
            onValueChange = {
                onValueChange(insertKurUiEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text("Masukkan Deskripsi") }
        )
        Text(
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )

        Text(text = "Kategori")

        // RADIO BUTTON Kategori
        Row (
            modifier = modifier.fillMaxWidth()
        ){
            kategori.forEach{ kt ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = insertKurUiEvent.kategori == kt,
                        onClick = {
                            onValueChange(insertKurUiEvent.copy(kategori = kt))
                        },
                    )
                    Text(
                        text = kt,
                    )
                }
            }
        }
        Text(
            text = errorState.kategori ?: "",
            color = Color.Red
        )

        // TEXTFIELD Harga
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertKurUiEvent.harga?.toString() ?: "", // Konversi Double ke String, default kosong jika null
            onValueChange = {
                val newValue = it.toDoubleOrNull() // Coba konversi ke Double
                onValueChange(insertKurUiEvent.copy(harga = newValue)) // Update jika valid, null jika gagal
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan Harga") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Tipe keyboard angka
        )
        // Menampilkan pesan error jika ada
        Text(
            text = errorState.harga ?: "",
            color = Color.Red
        )

        // DROPDOWN INSTRUKTUR
        DropdownData(
            selectedValue = insertKurUiEvent.id_instruktur,
            options = InstrukturList.DataInstruktur() ,
            label = "Pilih Instruktur",
            onValueChangeEvent = { selected ->
               chosenDropdown  = selected
                onValueChange(insertKurUiEvent.copy(id_instruktur = selected))
            }
        )
        Text(
            text = errorState.id_instruktur ?: "",  // Menampilkan pesan error jika ada
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