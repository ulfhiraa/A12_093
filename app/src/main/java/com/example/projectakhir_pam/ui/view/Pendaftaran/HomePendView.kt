package com.example.projectakhir_pam.ui.view.Pendaftaran

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.HomePendUiState
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.HomePendViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/*
Home view untuk menampilkan daftar pendaftaran dengan fitur CRUD, dan status UI (loading,success,error)
*/

object DestinasiHomePend : DestinasiNavigasi {
    override val route = "homePend" // rute navigasi
    override val titleRes = "Home Pendaftaran" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePendView( // tampilan utama yang menampilkan daftar pendaftaran
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPendClick: (String) -> Unit = {},
    onEditPendClick: (String) -> Unit = {},
    viewModel: HomePendViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var selectedCategory by remember { mutableStateOf("Sort by") }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                // Menampilkan judul "Home Pendaftaran" dan tombol refresh untuk memuat ulang data pendaftaran.
                title = DestinasiHomePend.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPend()
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
                        .padding(10.dp) // Jarak FAB dari tepi
                        .background(Color.Transparent), // Latar belakang transparan
                    elevation = FloatingActionButtonDefaults.elevation(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Pendaftaran",
                            tint = Color.Black // Warna ikon
                        )
                        Text(
                            text = "Tambah Pendaftaran",
                            color = Color.Black // Warna teks
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            // Dropdown untuk memilih kategori
            CategoryFilterDropdown(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            // Tampilkan daftar berdasarkan kategori yang dipilih
            HomeStatus(
                homePendUiState = viewModel.pendUIState,
                retryAction = { viewModel.getPend() },
                modifier = Modifier.fillMaxSize(),
                selectedCategory = selectedCategory,
                onDetailPendClick = onDetailPendClick,
                onDeletePendClick = {
                    viewModel.deletePend(it.id_pendaftaran) // Mengambil data pendaftaran dari repository
                    viewModel.getPend() // Menghapus pendaftaran berdasarkan ID yang dipilih
                },
                onEditPendClick = onEditPendClick
            )
        }
    }
}

@Composable
fun CategoryFilterDropdown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center // Pindahkan ke pojok kanan
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            shape = RectangleShape, // Menjadikan tombol berbentuk persegi
            modifier = Modifier
                .width(120.dp) // Tentukan lebar jika diperlukan
                .height(40.dp) // Tentukan tinggi jika diperlukan
        ) {

            Spacer(modifier = Modifier.width(5.dp)) // Jarak antara ikon dan teks

            Text(text = selectedCategory)

            Icon(
                imageVector = Icons.Default.ArrowDropDown, // Ganti dengan ikon yang diinginkan
                contentDescription = "Dropdown Icon",
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("All", "Saintek", "Soshum").forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun HomeStatus( // menampilkan UI sesuai dengan status data pendaftaran
    homePendUiState: HomePendUiState,
    retryAction: () -> Unit, // untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier,
    onDeletePendClick: (Pendaftaran) -> Unit = {},
    onDetailPendClick: (String) -> Unit,
    onEditPendClick: (String) -> Unit,
    selectedCategory: String
) {
    when (homePendUiState) {
        is HomePendUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomePendUiState.Success -> {
            val filteredPendaftaran = when (selectedCategory) {
                "Saintek" -> homePendUiState.pendaftaran.filter { it.kategori == "Saintek" }
                "Soshum" -> homePendUiState.pendaftaran.filter { it.kategori == "Soshum" }
                else -> homePendUiState.pendaftaran // All
            }

            if (filteredPendaftaran.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada Data Pendaftaran")
                }
            } else {
                PendLayout(
                    pendaftaran = filteredPendaftaran,
                    modifier = modifier.fillMaxWidth(),
                    onDetailPendClick = {
                        onDetailPendClick(it.id_pendaftaran)
                    },
                    onDeletePendClick = {
                        onDeletePendClick(it)
                    },
                    onEditPendClick = { pendaftaran -> onEditPendClick(pendaftaran.id_pendaftaran) }
                )
            }
        }

        is HomePendUiState.Error -> OnError(
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
fun PendLayout(
    pendaftaran: List<Pendaftaran>,
    modifier: Modifier = Modifier,
    onDetailPendClick: (Pendaftaran) -> Unit,
    onEditPendClick: (Pendaftaran) -> Unit,
    onDeletePendClick: (Pendaftaran) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var pendToDelete by remember { mutableStateOf<Pendaftaran?>(null) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(pendaftaran) { pendItem ->
            PendCard(
                pendItem = pendItem,
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailPendClick = onDetailPendClick,
                onEditPendClick = onEditPendClick,
                onDeletePendClick = {
                    pendToDelete = pendItem
                    showDialog = true
                }
            )
        }
    }

    if (showDialog && pendToDelete != null) {
        DeleteConfirmationDialog(
            pendaftaran = pendToDelete,
            onConfirm = {
                onDeletePendClick(pendToDelete!!)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun PendCard(
    pendItem: Pendaftaran,
    modifier: Modifier = Modifier,
    onDetailPendClick: (Pendaftaran) -> Unit,
    onEditPendClick: (Pendaftaran) -> Unit,
    onDeletePendClick: (Pendaftaran) -> Unit
) {
   // variabel untuk mengatur tampilan dan format waktu
    // Format input (waktu UTC)
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoDateFormat.timeZone = TimeZone.getTimeZone("UTC") //  UTC sebagai zona waktu input

    // Format output (menampilkan waktu WIB)
    val displayDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("id", "ID"))
    displayDateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Set ke WIB (Asia/Jakarta)

    // Mengonversi waktu UTC ke Date
    val utcDate = isoDateFormat.parse(pendItem.tglDaftar)

    // Format menjadi waktu WIB
    val formattedDate = utcDate?.let { displayDateFormat.format(it) } ?: pendItem.tglDaftar

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Detail Pendaftaran (Kiri)
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(1.dp))

                // ID PENDAFTARAN
                Text(
                    text = pendItem.id_pendaftaran,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.SansSerif, // Menetapkan font sans-serif
                        fontWeight = FontWeight.Bold
                    ),
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                //NAMA SISWA relasi id_siswa
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "ID Siswa",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${pendItem.id_siswa}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(1.dp))

                // NAMA KURSUS relasi id_kursus
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "ID Kursus",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = " ${pendItem.id_kursus}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(1.dp))

                //TANGGAL PENDAFTARAN
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Tgl pendaftaran",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Memperlebar jarak antara icon dan teks
                    Text(
                        text = formattedDate, // Menampilkan tanggal waktu pendaftaran yang sudah diformat
//                        text = pendItem.tglDaftar, // Menampilkan tanggal waktu pendaftaran
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(1.dp))

                // STATUS PENDAFTARAN
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Status pendaftaran",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${pendItem.status}",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Kolom Tombol (Kanan)
            Column(
                modifier = Modifier
                    .weight(1.5f), // Atur agar kolom tombol hanya mengambil 1 bagian dari total ruang
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(1.dp))

                // KATEGORI
                val imageResource = when (pendItem.kategori) {
                    "Saintek" -> R.drawable.sigma // Gambar untuk kategori "saintek"
                    "Soshum" -> R.drawable.book // Gambar untuk kategori "soshum"
                    else -> R.drawable.failed // Gambar default jika kategori tidak sesuai
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Kategori",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Menambahkan jarak antara ikon dan teks
                    Text(
                        text = "${pendItem.kategori}",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // button detail
                OutlinedButton(
                    onClick = { onDetailPendClick(pendItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Detail",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Detail")
                }

                // button edit
                OutlinedButton(
                    onClick = { onEditPendClick(pendItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Edit")
                }

                // button hapus
                OutlinedButton(
                    onClick = { onDeletePendClick(pendItem) },
                    modifier = Modifier
                        .fillMaxWidth()
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
}

@Composable
fun DeleteConfirmationDialog(
    pendaftaran: Pendaftaran?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data pendaftaran " +
                "${pendaftaran?.id_pendaftaran}?") },
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
