package com.example.projectakhir_pam.ui.view.Instruktur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstUiState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

/*
Home view untuk menampilkan daftar siswa dengan fitur CRUD, dan status UI (loading,success,error)
*/

object DestinasiHomeInst : DestinasiNavigasi {
    override val route = "homeInstruktur" // rute navigasi
    override val titleRes = "Home Instruktur" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeInstView(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailInstClick: (String) -> Unit = {},
    onEditInstClick: (String) -> Unit = {},
    viewModel: HomeInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                // Menampilkan judul "Home Instruktur" dan tombol refresh untuk memuat ulang data instruktur
                title = DestinasiHomeInst.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getInst()
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                FloatingActionButton(
                    onClick = navigateToItemEntry,
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Menempatkan FAB di bawah tengah
                        .padding(8.dp) // Jarak FAB dari tepi
                        .background(Color.Transparent), // Latar belakang transparan
                    elevation = FloatingActionButtonDefaults.elevation(14.dp) // Hilangkan bayangan
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Instruktur",
                            tint = Color.Black // Warna ikon
                        )
                        Text(
                            text = "Tambah Instruktur",
                            color = Color.Black // Warna teks
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeInstUiState = viewModel.instUIState,
            retryAction = { viewModel.getInst() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDetailInstClick = onDetailInstClick,
            onDeleteInstClick = {
                viewModel.deleteInst(it.id_instruktur ) // Mengambil data instruktur dari repository
                viewModel.getInst() // Menghapus instruktur berdasarkan ID yang dipilih
            },
            onEditInstClick = onEditInstClick
        )
    }
}

@Composable
fun HomeStatus( // menampilkan UI sesuai dengan status data instruktur
    homeInstUiState: HomeInstUiState,
    retryAction: () -> Unit, // untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier,
    onDeleteInstClick: (Instruktur) -> Unit = {},
    onDetailInstClick: (String) -> Unit,
    onEditInstClick: (String) -> Unit
) {
    when (homeInstUiState) {
        //Menampilkan gambar loading.
        is HomeInstUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Menampilkan daftar instruktur jika data berhasil diambil.
        is HomeInstUiState.Success ->
            if (
                homeInstUiState.instruktur.isEmpty()){
                return Box (
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center)
                {
                    Text(text = "Tidak ada Data Instruktur" )
                }
            } else {
                InstTabel(
                    instrukturList = homeInstUiState.instruktur,
                    modifier = modifier.fillMaxWidth(),
                    onDetailInstClick = { onDetailInstClick(it.id_instruktur) },
                    onDeleteInstClick = { onDeleteInstClick(it) },
                    onEditInstClick = { onEditInstClick(it.id_instruktur)}
                )
            }

        // Menampilkan pesan error dengan tombol retry jika pengambilan data gagal.
        is HomeInstUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading( modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(4.dp),
        painter = painterResource(R.drawable.load),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.load),
            contentDescription = "Error Image",
            modifier = Modifier
                .size(100.dp) // Atur ukuran gambar menjadi 100x100 dp
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction)
        {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun InstTabel(
    instrukturList: List<Instruktur>,
    modifier: Modifier = Modifier,
    onDetailInstClick: (Instruktur) -> Unit,
    onDeleteInstClick: (Instruktur) -> Unit = {},
    onEditInstClick: (Instruktur) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }  // State untuk menampilkan dialog
    var instToDelete by remember { mutableStateOf<Instruktur?>(null) }  // Menyimpan instruktur yang akan dihapus

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Header Tabel
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableInst(text = "ID", width = 35.dp, isHeader = true)
                TableInst(text = "Nama", width = 40.dp, isHeader = true)
                TableInst(text = "Email", width = 50.dp, isHeader = true)
                TableInst(text = "No.HP", width = 50.dp, isHeader = true)
                TableInst(text = "Deskripsi", width = 70.dp, isHeader = true)
                TableInst(text = "Aksi", width = 30.dp, isHeader = true)
            }
            Divider(color = Color.Gray, thickness = 1.dp) // Garis pemisah yang lebih soft
        }

        // Baris Data Instruktur
        items(instrukturList) { inst ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        if (instrukturList.indexOf(inst) % 2 == 0) Color(0xFFF3F4F6)
                        else
                            Color(0xFFEDEEF2)
                    ) // Alternating row colors
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableInst(text = inst.id_instruktur.toString(), width = 35.dp)
                TableInst(text = inst.namaInstruktur.take(4) + "...", width = 40.dp)
                TableInst(text = inst.email.take(4) + "...", width = 50.dp)
                TableInst(text = inst.noTelpInst.take(4) + "...", width = 50.dp)
                TableInst(text = inst.deskripsi.take(4) + "...", width = 35.dp)

                // Kolom Aksi
                Column(
                    modifier = Modifier.width(65.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        onClick = { onDetailInstClick(inst) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp) // Memperkecil padding dalam tombol
                    ) {
                        Text("Detail", style = MaterialTheme.typography.bodySmall)
                    }
                    OutlinedButton(
                        onClick = { onEditInstClick(inst) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text("Edit", style = MaterialTheme.typography.bodySmall)
                    }
                    OutlinedButton(
                        onClick =
                        {
                            instToDelete = inst
                            showDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text("Hapus", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Divider(color = Color(0xFFDDDDDD), thickness = 1.dp) // Garis pemisah soft
        }
    }

    // Dialog konfirmasi untuk menghapus data instruktur
    if (showDialog && instToDelete != null) {
        DeleteConfirmationDialog(
            instruktur = instToDelete,
            onConfirm = {
                onDeleteInstClick(instToDelete!!)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun TableInst(
    text: String,
    width: Dp,
    isHeader: Boolean = false)
{
    Box(
        modifier = Modifier
            .width(width),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = if (isHeader) {
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}


@Composable
fun DeleteConfirmationDialog(
    instruktur: Instruktur?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data instruktur " +
                "${instruktur?.namaInstruktur}?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Hapus")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
