package com.example.projectakhir_pam.ui.view.Kursus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurUiState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

/*
Home view untuk menampilkan daftar kursus dengan fitur CRUD, dan status UI (loading,success,error)
*/

object DestinasiHomeKur : DestinasiNavigasi {
    override val route = "homeKursus" // rute navigasi
    override val titleRes = "Home Kursus" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKurView( // tampilan utama yang menampilkan daftar kursus
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailKurClick: (String) -> Unit = {},
    onEditKurClick: (String) -> Unit = {},
    viewModel: HomeKurViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // State untuk pencarian
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                // Menampilkan judul "Home Kursus" dan tombol refresh untuk memuat ulang data kursus.
                title = DestinasiHomeKur.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKur()
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
                    elevation = FloatingActionButtonDefaults.elevation(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Kursus",
                            tint = Color.Black // Warna ikon
                        )
                        Text(
                            text = "Tambah Kursus",
                            color = Color.Black // Warna teks
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Pencarian
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari Kursus") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ikon Pencarian",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true
            )

            HomeStatus(
                homeKurUiState = viewModel.kurUIState,
                retryAction = { viewModel.getKur() },
                modifier = Modifier
                    .padding(horizontal = 16.dp) // Padding yang konsisten
                    .fillMaxSize(),
                onDetailKurClick = onDetailKurClick,
                onDeleteKurClick = {
                    viewModel.deleteKur(it.id_kursus)
                    viewModel.getKur()
                },
                onEditKurClick = onEditKurClick,
                searchQuery = searchQuery // Menyaring kursus berdasarkan pencarian
            )
        }
    }
}

@Composable
fun HomeStatus( // menampilkan UI sesuai dengan status data kursus
    homeKurUiState: HomeKurUiState, // status data kursus
    retryAction: () -> Unit, // untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier,
    onDeleteKurClick: (Kursus) -> Unit = {},
    onDetailKurClick: (String) -> Unit,
    onEditKurClick: (String) -> Unit,
    searchQuery: String // sebagai parameter pencarian filter berdasar id inst, nama kursus, kategori
) {
    when (homeKurUiState) {
        is HomeKurUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeKurUiState.Success -> {
            val filteredKursus = homeKurUiState.kursus.filter {
                it.namaKursus.contains(searchQuery, ignoreCase = true) || // nama kursus
                        it.kategori.contains(searchQuery, ignoreCase = true) || // kategori
                        it.id_instruktur.contains(searchQuery, ignoreCase = true) // id instruktur
            }
            //Fungsi contains digunakan untuk memeriksa kecocokan string secara case-insensitive (mengabaikan perbedaan huruf besar/kecil).

            if (filteredKursus.isEmpty()) { //
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data Kursus") // kalau tidak ada yang cocok
                }
            } else {
                KurLayout(
                    kursus = filteredKursus,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onDetailKurClick = { onDetailKurClick(it.id_kursus) },
                    onDeleteKurClick = { onDeleteKurClick(it) },
                    onEditKurClick = { onEditKurClick(it.id_kursus) }
                )
            }
        }

        is HomeKurUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())

//        else -> { /* State tidak dikenal */ }
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
fun KurLayout(
    kursus: List<Kursus>,
    modifier: Modifier = Modifier,
    onDetailKurClick: (Kursus) -> Unit,
    onEditKurClick: (Kursus) -> Unit,
    onDeleteKurClick: (Kursus) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var kursusToDelete by remember { mutableStateOf<Kursus?>(null) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(kursus) { kurItem ->
            KurCard(
                kurItem = kurItem,
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailKurClick = onDetailKurClick,
                onEditKurClick = onEditKurClick,
                onDeleteKurClick = {
                    kursusToDelete = kurItem
                    showDialog = true
                }
            )
        }
    }

    if (showDialog && kursusToDelete != null) {
        DeleteConfirmationDialog(
            kursus = kursusToDelete,
            onConfirm = {
                onDeleteKurClick(kursusToDelete!!)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun KurCard(
    kurItem: Kursus,
    modifier: Modifier = Modifier,
    onDetailKurClick: (Kursus) -> Unit,
    onEditKurClick: (Kursus) -> Unit,
    onDeleteKurClick: (Kursus) -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 13.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Detail Kursus (Kiri)
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = kurItem.namaKursus,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "ID Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${kurItem.id_kursus}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Kategori Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = " ${kurItem.kategori}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Harga Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Rp${String.format("%.2f", kurItem.harga)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Deskripsi Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = kurItem.deskripsi.take(10) + "...",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Instruktur Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${kurItem.id_instruktur}",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Kolom Tombol (Kanan)
            Column(
                modifier = Modifier
                    .weight(1.7f), // Atur agar kolom tombol hanya mengambil 1 bagian dari total ruang
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { onDetailKurClick(kurItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Detail",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Detail")
                }

                OutlinedButton(
                    onClick = { onEditKurClick(kurItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Edit")
                }

                OutlinedButton(
                    onClick = { onDeleteKurClick(kurItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Hapus")
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    kursus: Kursus?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data kursus " +
                "${kursus?.namaKursus}?") },
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

