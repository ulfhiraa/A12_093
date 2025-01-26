package com.example.projectakhir_pam.ui.view.Siswa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel

object DestinasiHomeSis : DestinasiNavigasi {
    override val route = "homeSiswa" // rute navigasi
    override val titleRes = "Home Siswa" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSisView( // tampilan utama yang menampilkan daftar siswa
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailSisClick: (String) -> Unit = {},
    onEditSisClick: (String) -> Unit = {},
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
                canNavigateBack = true,
                navigateUp = navigateBack,
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
                    Text(text = "Tambah Siswa") // Menambahkan teks "Tambah"
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
            onDetailSisClick = onDetailSisClick,
            onDeleteSisClick = {
                viewModel.deleteSis(it.id_siswa.toString()) // Mengambil data siswa dari repository
                viewModel.getSis() // Menghapus siswa berdasarkan ID yang dipilih
            },
            onEditSisClick = onEditSisClick
        )
    }
}

@Composable
fun HomeStatus(
    homeSisUiState: HomeSisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteSisClick: (Siswa) -> Unit = {},
    onDetailSisClick: (String) -> Unit,
    onEditSisClick: (String) -> Unit
) {
    when (homeSisUiState) {
        is HomeSisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeSisUiState.Success -> {
            if (homeSisUiState.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center)
                {
                    Text(text = "Tidak ada Data Siswa")
                }
            } else {
                SiswaTable(
                    siswaList = homeSisUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailSisClick = { onDetailSisClick(it.id_siswa.toString()) },
                    onDeleteSisClick = onDeleteSisClick,
                    onEditSisClick = { onEditSisClick(it.id_siswa.toString()) }
                )
            }
        }
        is HomeSisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun SiswaTable(
    siswaList: List<Siswa>,
    modifier: Modifier = Modifier,
    onDetailSisClick: (Siswa) -> Unit,
    onDeleteSisClick: (Siswa) -> Unit = {},
    onEditSisClick: (Siswa) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }  // State untuk menampilkan dialog
    var sisToDelete by remember { mutableStateOf<Siswa?>(null) }  // Menyimpan siswa yang akan dihapus

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
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableSis(text = "ID", width = 60.dp, isHeader = true)
                TableSis(text = "Nama", width = 60.dp, isHeader = true)
                TableSis(text = "Email", width = 60.dp, isHeader = true)
                TableSis(text = "No. Telp", width = 85.dp, isHeader = true)
                TableSis(text = "Aksi", width = 85.dp, isHeader = true)
            }
            Divider(color = Color.Gray, thickness = 1.dp) // Garis pemisah yang lebih soft
        }

        // Baris Data Siswa
        items(siswaList) { siswa ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        if (siswaList.indexOf(siswa) % 2 == 0) Color(0xFFF3F4F6)
                        else
                            Color(0xFFEDEEF2)
                    ) // Alternating row colors
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableSis(text = siswa.id_siswa.toString(), width = 50.dp)
                TableSis(text = siswa.namaSiswa.take(5) + "...", width = 50.dp)
                TableSis(text = siswa.email.take(5) + "...", width = 60.dp)
                TableSis(text = siswa.noTelpSiswa.take(5) + "...", width = 70.dp)
                // Kolom Aksi
                Column(
                    modifier = Modifier.width(85.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        onClick = { onDetailSisClick(siswa) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp) // Memperkecil padding dalam tombol
                    ) {
                        Text("Detail", style = MaterialTheme.typography.bodySmall)
                    }
                    OutlinedButton(
                        onClick = { onEditSisClick(siswa) },
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
                            sisToDelete = siswa
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

    // Dialog konfirmasi untuk menghapus data siswa
    if (showDialog && sisToDelete != null) {
        DeleteConfirmationDialog(
            siswa = sisToDelete,
            onConfirm = {
                onDeleteSisClick(sisToDelete!!)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun TableSis(text: String, width: Dp, isHeader: Boolean = false) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(8.dp),
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
fun DeleteConfirmationDialog(
    siswa: Siswa?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data siswa " +
                "${siswa?.namaSiswa}?") },
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
