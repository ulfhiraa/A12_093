package com.example.projectakhir_pam.ui.view.Instruktur

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
        floatingActionButton = { // Tombol untuk menambahkan data instruktur
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Instruktur"
                    )
                    Text(text = "Tambah Instruktur")
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
    when (
        homeInstUiState) {
        //Menampilkan gambar loading.
        is HomeInstUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Menampilkan daftar instruktur jika data berhasil diambil.
        is HomeInstUiState.Success ->
            if (
                homeInstUiState.instruktur.isEmpty()){
                return Box (modifier = modifier.
                fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data Instruktur" )
                }
            }else {
                InstLayout(
                    instruktur = homeInstUiState.instruktur,
                    modifier = modifier.fillMaxWidth(),
                    onDetailInstClick = {
                        onDetailInstClick(it.id_instruktur)
                    },
                    onDeleteInstClick = {
                        onDeleteInstClick(it)
                    },
                    onEditInstClick = { instruktur -> onEditInstClick(instruktur.id_instruktur) }
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
fun InstLayout(
    instruktur: List<Instruktur>,
    modifier: Modifier = Modifier,
    onDetailInstClick: (Instruktur) -> Unit,
    onDeleteInstClick: (Instruktur) -> Unit = {},
    onEditInstClick: (Instruktur) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }  // State untuk menampilkan dialog
    var instToDelete by remember { mutableStateOf<Instruktur?>(null) }  // Menyimpan instruktur yang akan dihapus

    // Card utama yang membungkus seluruh tabel
    Card(
        modifier = modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            InstHeader() // Header tabel dengan nama kolom

            Divider(color = Color.Gray, thickness = 1.dp) // Garis pemisah

            // Data Instruktur
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(instruktur) { instItem ->
                    InstCard(
                        instItem = instItem,
                        onDetailInstClick = onDetailInstClick,
                        onEditInstClick = onEditInstClick,
                        onDeleteInstClick = {
                            instToDelete = instItem
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    // Dialog konfirmasi untuk menghapus instruktur
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
fun InstHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ID Instruktur",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = "Nama",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = "No Telp",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = "Deskripsi",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.7f)
        )
    }
}

@Composable
fun InstCard(
    instItem: Instruktur,
    onDetailInstClick: (Instruktur) -> Unit,
    onEditInstClick: (Instruktur) -> Unit,
    onDeleteInstClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
    ) {
        // Data Instruktur (Preview)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = instItem.id_instruktur,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = instItem.namaInstruktur.take(5) + "...", // Hanya tampilkan 5 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1.5f)
            )
            Text(
                text = instItem.email.take(5) + "...", // Hanya tampilkan 5 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1.5f)
            )
            Text(
                text = instItem.noTelpInst.take(5) + "...", // Hanya tampilkan 5 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1.5f)
            )
            Text(
                text = instItem.deskripsi.take(5) + "...", // Hanya tampilkan 5 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1.7f)
            )
        }

        // Tombol Aksi; Detail, Edit, Hapus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            // Button Detail
            OutlinedButton(
                onClick = { onDetailInstClick(instItem) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Detail",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Detail")
            }

            // Button Edit
            OutlinedButton(
                onClick = { onEditInstClick(instItem) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Edit")
            }

            // Button Hapus
            OutlinedButton(
                onClick = onDeleteInstClick,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Hapus")
            }
        }
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
