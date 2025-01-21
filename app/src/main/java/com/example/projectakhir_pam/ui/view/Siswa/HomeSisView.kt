package com.example.projectakhir_pam.ui.view.Siswa

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
import androidx.compose.material.icons.filled.Edit
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
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel

/*
Home view untuk menampilkan daftar siswa dengan fitur CRUD, dan status UI (loading,success,error)
*/
object DestinasiHomeSis : DestinasiNavigasi {
    override val route = "homeSiswa" // rute navigasi
    override val titleRes = "Home Siswa" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView( // tampilan utama yang menampilkan daftar siswa
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    viewModel: HomeSisViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                // Menampilkan judul "Home Siswa" dan tombol refresh untuk memuat ulang data siswa.
                title = DestinasiHomeSis.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getSis()
                }
            )
        },
        floatingActionButton = { // Tombol untuk menambahkan data siswa
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
                        contentDescription = "Add Siswa"
                    )
                    Text(text = "Tambah Data") // Menambahkan teks "Tambah"
                }
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeSisUiState = viewModel.sisUIState,
            retryAction = { viewModel.getSis() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteSis(it.id_siswa ) // Mengambil data siswa dari repository
                viewModel.getSis() // Menghapus siswa berdasarkan ID yang dipilih
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomeStatus( // menampilkan UI sesuai dengan status data siswa
    homeSisUiState: HomeSisUiState,
    retryAction: () -> Unit, // untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier,
    onDeleteClick: (Siswa) -> Unit = {},
    onDetailClick: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    when (
        homeSisUiState) {
        //Menampilkan gambar loading.
        is HomeSisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Menampilkan daftar siswa jika data berhasil diambil.
        is HomeSisUiState.Success ->
            if (
                homeSisUiState.siswa.isEmpty()){
                return Box (modifier = modifier.
                fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data Siswa" )
                }
            }else {
                SisLayout(
                    siswa = homeSisUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_siswa)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = { siswa -> onEditClick(siswa.id_siswa) }
                )
            }

        // Menampilkan pesan error dengan tombol retry jika pengambilan data gagal.
        is HomeSisUiState.Error -> OnError(
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
            painter = painterResource(id = R.drawable.failed),
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
fun SisLayout(
    siswa: List<Siswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Siswa) -> Unit,
    onDeleteClick: (Siswa) -> Unit = {},
    onEditClick: (Siswa) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }  // State untuk menampilkan dialog
    var siswaToDelete by remember { mutableStateOf<Siswa?>(null) }  // Menyimpan siswa yang akan dihapus

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
            SisHeader() // Header tabel dengan nama kolom

            Divider(color = Color.Gray, thickness = 1.dp) // Garis pemisah

            // Data siswa
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(siswa) { siswaItem ->
                    SisCard(
                        siswaItem = siswaItem,
                        onDetailClick = onDetailClick,
                        onEditClick = onEditClick,
                        onDeleteClick = {
                            siswaToDelete = siswaItem
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    // Dialog konfirmasi untuk menghapus siswa
    if (showDialog && siswaToDelete != null) {
        DeleteConfirmationDialog(
            siswa = siswaToDelete,
            onConfirm = {
                onDeleteClick(siswaToDelete!!)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun SisHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ID Siswa",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = "Nama Siswa",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(3f)
        )
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = "No Telepon",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun SisCard(
    siswaItem: Siswa,
    onDetailClick: (Siswa) -> Unit,
    onEditClick: (Siswa) -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
    ) {
        // Data siswa (Preview)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = siswaItem.id_siswa,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = siswaItem.namaSiswa.take(10) + "...", // Hanya tampilkan 10 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = siswaItem.email.take(6) + "...", // Hanya tampilkan 10 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = siswaItem.noTelpSiswa.take(5) + "...", // Hanya tampilkan 7 karakter
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(2f)
            )
        }

        // Tombol Aksi; Detail, Edit, Hapus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Button Detail
            OutlinedButton(
                onClick = { onDetailClick(siswaItem) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Text(text = "Detail")
            }

            // Button Edit
            OutlinedButton(
                onClick = { onEditClick(siswaItem) },
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
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Text(text = "Hapus")
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    siswa: Siswa?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data siswa ${siswa?.namaSiswa}?") },
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
