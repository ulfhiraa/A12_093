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

// Menandai fungsi ini menggunakan API eksperimental Material3 untuk komponen UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeInstView(
    // Fungsi yang digunakan untuk navigasi ke halaman entri instruktur baru
    navigateToItemEntry: () -> Unit,
    // Fungsi untuk kembali ke halaman sebelumnya
    navigateBack: () -> Unit,
    // Modifier untuk menyesuaikan tampilan
    modifier: Modifier = Modifier,
    // Fungsi untuk menampilkan detail instruktur ketika diklik
    onDetailInstClick: (String) -> Unit = {},
    // Fungsi untuk mengedit instruktur ketika diklik
    onEditInstClick: (String) -> Unit = {},
    // ViewModel yang digunakan untuk mengelola data dan status UI instruktur
    viewModel: HomeInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Menyiapkan behavior scroll untuk top app bar (misalnya saat scroll layar)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Scaffold adalah layout dasar untuk aplikasi dengan struktur yang umum (misalnya top bar, floating button, dll.)
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection), // Menyambungkan scroll behavior dengan Scaffold
        topBar = {
            // Menampilkan top app bar dengan judul dan tombol refresh
            CustomeTopAppBar(
                title = DestinasiHomeInst.titleRes, // Menampilkan judul yang diambil dari resource
                canNavigateBack = true, // Menunjukkan tombol kembali di top bar
                navigateUp = navigateBack, // Fungsi untuk kembali ke halaman sebelumnya
                scrollBehavior = scrollBehavior, // Menghubungkan scroll behavior
                onRefresh = {
                    // Fungsi untuk memuat ulang data instruktur
                    viewModel.getInst()
                }
            )
        },
        floatingActionButton = {
            // Membuat tombol aksi mengambang (FAB) yang berada di bagian bawah tengah layar
            Box(
                modifier = Modifier
                    .fillMaxSize() // Mengisi seluruh ruang di dalam box
            ) {
                FloatingActionButton(
                    onClick = navigateToItemEntry, // Fungsi yang dipanggil saat FAB diklik
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Menempatkan FAB di tengah bawah
                        .padding(8.dp) // Memberikan jarak antara FAB dan tepi layar
                        .background(Color.Transparent), // Latar belakang FAB transparan
                    elevation = FloatingActionButtonDefaults.elevation(14.dp) // Mengatur bayangan pada FAB
                ) {
                    // Menampilkan ikon dan teks pada FAB
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // Menyusun ikon dan teks secara vertikal
                        horizontalArrangement = Arrangement.spacedBy(4.dp) // Memberikan jarak antara ikon dan teks
                    ) {
                        // Menampilkan ikon plus di dalam FAB
                        Icon(
                            imageVector = Icons.Default.Add, // Ikon plus default
                            contentDescription = "Add Instruktur", // Deskripsi untuk aksesibilitas
                            tint = Color.Black // Warna ikon
                        )
                        // Menampilkan teks "Tambah Instruktur" di samping ikon
                        Text(
                            text = "Tambah Instruktur", // Teks yang ditampilkan
                            color = Color.Black // Warna teks
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Konten utama halaman, menampilkan status instruktur
        HomeStatus(
            homeInstUiState = viewModel.instUIState, // Status UI instruktur (misalnya loading, error, dll.)
            retryAction = { viewModel.getInst() }, // Aksi untuk mencoba memuat ulang data instruktur
            modifier = Modifier
                .padding(innerPadding) // Memberikan padding untuk menghindari tumpang tindih dengan elemen lainnya
                .fillMaxSize(), // Mengisi seluruh ruang yang tersedia
            // Fungsi yang dipanggil saat detail instruktur diklik
            onDetailInstClick = onDetailInstClick,
            // Fungsi yang dipanggil saat instruktur ingin dihapus
            onDeleteInstClick = {
                // Menghapus instruktur berdasarkan ID
                viewModel.deleteInst(it.id_instruktur)
                // Memuat ulang data instruktur setelah penghapusan
                viewModel.getInst()
            },
            // Fungsi yang dipanggil saat instruktur ingin diedit
            onEditInstClick = onEditInstClick
        )
    }
}

// Fungsi HomeStatus digunakan untuk menampilkan UI berdasarkan status data instruktur
@Composable
fun HomeStatus(
    homeInstUiState: HomeInstUiState, // Menyimpan status data instruktur (Loading, Success, Error)
    retryAction: () -> Unit, // Fungsi untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier, // Modifier untuk penataan tampilan
    onDeleteInstClick: (Instruktur) -> Unit = {}, // Fungsi yang dipanggil saat instruktur ingin dihapus
    onDetailInstClick: (String) -> Unit, // Fungsi yang dipanggil saat detail instruktur diklik
    onEditInstClick: (String) -> Unit // Fungsi yang dipanggil saat instruktur ingin diedit
) {
    // Mengecek status dari homeInstUiState
    when (homeInstUiState) {
        // Jika statusnya Loading, tampilkan gambar loading
        is HomeInstUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Jika statusnya Success, tampilkan daftar instruktur
        is HomeInstUiState.Success ->
            // Jika daftar instruktur kosong, tampilkan pesan "Tidak ada Data Instruktur"
            if (homeInstUiState.instruktur.isEmpty()) {
                return Box(
                    modifier = modifier.fillMaxSize(), // Mengisi seluruh ruang
                    contentAlignment = Alignment.Center // Menempatkan teks di tengah
                ) {
                    Text(text = "Tidak ada Data Instruktur") // Tampilkan pesan
                }
            } else {
                // Jika ada data instruktur, tampilkan dalam tabel
                InstTabel(
                    instrukturList = homeInstUiState.instruktur, // Daftar instruktur yang diambil
                    modifier = modifier.fillMaxWidth(), // Lebar tabel mengikuti ukuran layar
                    onDetailInstClick = { onDetailInstClick(it.id_instruktur) }, // Fungsi detail
                    onDeleteInstClick = { onDeleteInstClick(it) }, // Fungsi hapus
                    onEditInstClick = { onEditInstClick(it.id_instruktur) } // Fungsi edit
                )
            }

        // Jika statusnya Error, tampilkan pesan error dan tombol retry
        is HomeInstUiState.Error -> OnError(
            retryAction, // Aksi untuk mencoba lagi
            modifier = modifier.fillMaxSize() // Mengisi seluruh ruang
        )
    }
}

// Fungsi OnLoading menampilkan gambar loading
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(4.dp), // Ukuran gambar loading
        painter = painterResource(R.drawable.load), // Resource gambar loading
        contentDescription = stringResource(R.string.loading) // Deskripsi gambar untuk aksesibilitas
    )
}

// Fungsi OnError menampilkan pesan error dan tombol untuk mencoba lagi
@Composable
fun OnError(
    retryAction: () -> Unit, // Fungsi yang dipanggil saat tombol retry ditekan
    modifier: Modifier = Modifier
) {
    // Menampilkan UI error di tengah layar
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, // Mengatur agar konten berada di tengah secara vertikal
        horizontalAlignment = Alignment.CenterHorizontally // Mengatur agar konten berada di tengah secara horizontal
    ) {
        // Menampilkan gambar error
        Image(
            painter = painterResource(id = R.drawable.failed), // Resource gambar error
            contentDescription = "Error Image", // Deskripsi gambar untuk aksesibilitas
            modifier = Modifier.size(100.dp) // Menentukan ukuran gambar menjadi 100x100 dp
        )
        // Menampilkan teks error
        Text(
            text = stringResource(R.string.loading_failed), // Pesan error
            modifier = Modifier.padding(16.dp) // Memberikan padding di sekitar teks
        )
        // Tombol untuk mencoba lagi
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry)) // Teks tombol retry
        }
    }
}

// Fungsi InstTabel untuk menampilkan daftar instruktur dalam bentuk tabel
@Composable
fun InstTabel(
    instrukturList: List<Instruktur>, // Daftar instruktur yang ditampilkan
    modifier: Modifier = Modifier, // Modifier untuk penataan tampilan
    onDetailInstClick: (Instruktur) -> Unit, // Fungsi yang dipanggil saat detail instruktur diklik
    onDeleteInstClick: (Instruktur) -> Unit = {}, // Fungsi yang dipanggil saat instruktur ingin dihapus
    onEditInstClick: (Instruktur) -> Unit // Fungsi yang dipanggil saat instruktur ingin diedit
) {
    // State untuk menampilkan dialog konfirmasi penghapusan instruktur
    var showDialog by remember { mutableStateOf(false) }
    // Menyimpan instruktur yang akan dihapus
    var instToDelete by remember { mutableStateOf<Instruktur?>(null) }

    // LazyColumn untuk menampilkan daftar instruktur dalam bentuk kolom yang bisa di-scroll
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth() // Mengisi seluruh lebar layar
    ) {
        // Header Tabel, menampilkan nama kolom
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Mengisi seluruh lebar
                    .background(MaterialTheme.colorScheme.primary) // Memberikan warna latar belakang
                    .padding(vertical = 14.dp), // Memberikan padding vertikal
                horizontalArrangement = Arrangement.SpaceBetween // Menyusun elemen secara horizontal
            ) {
                // Menampilkan header kolom dengan fungsi TableInst
                TableInst(text = "ID", width = 40.dp, isHeader = true)
                TableInst(text = "Nama", width = 40.dp, isHeader = true)
                TableInst(text = "Email", width = 50.dp, isHeader = true)
                TableInst(text = "No.HP", width = 50.dp, isHeader = true)
                TableInst(text = "Deskripsi", width = 70.dp, isHeader = true)
                TableInst(text = "Aksi", width = 30.dp, isHeader = true)
            }
            // Divider untuk pemisah antar header dan data
            Divider(color = Color.Gray, thickness = 1.dp)
        }

        // Menampilkan baris data instruktur
        items(instrukturList) { inst ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Padding vertikal antara baris
                    .background(
                        // Mengubah warna latar belakang secara bergantian untuk setiap baris
                        if (instrukturList.indexOf(inst) % 2 == 0) Color(0xFFF3F4F6)
                        else Color(0xFFEDEEF2)
                    )
                    .padding(vertical = 12.dp), // Padding vertikal dalam baris
                horizontalArrangement = Arrangement.SpaceBetween // Menyusun elemen secara horizontal
            ) {
                // Menampilkan data instruktur dalam setiap kolom
                TableInst(text = inst.id_instruktur.toString(), width = 40.dp)
                TableInst(text = inst.namaInstruktur.take(4) + "...", width = 40.dp)
                TableInst(text = inst.email.take(4) + "...", width = 50.dp)
                TableInst(text = inst.noTelpInst.take(4) + "...", width = 50.dp)
                TableInst(text = inst.deskripsi.take(4) + "...", width = 35.dp)

                // Kolom untuk aksi (Detail, Edit, Hapus)
                Column(
                    modifier = Modifier.width(65.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp), // Jarak antar tombol
                    horizontalAlignment = Alignment.CenterHorizontally // Menyusun tombol di tengah secara horizontal
                ) {
                    // Tombol untuk melihat detail instruktur
                    OutlinedButton(
                        onClick = { onDetailInstClick(inst) }, // Fungsi untuk melihat detail
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp) // Memperkecil padding dalam tombol
                    ) {
                        Text("Detail", style = MaterialTheme.typography.bodySmall)
                    }
                    // Tombol untuk mengedit instruktur
                    OutlinedButton(
                        onClick = { onEditInstClick(inst) }, // Fungsi untuk mengedit instruktur
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text("Edit", style = MaterialTheme.typography.bodySmall)
                    }
                    // Tombol untuk menghapus instruktur
                    OutlinedButton(
                        onClick = {
                            instToDelete = inst // Menyimpan instruktur yang akan dihapus
                            showDialog = true // Menampilkan dialog konfirmasi
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
            // Divider untuk memisahkan setiap baris
            Divider(color = Color(0xFFDDDDDD), thickness = 1.dp)
        }
    }

    // Dialog konfirmasi untuk menghapus data instruktur
    if (showDialog && instToDelete != null) {
        DeleteConfirmationDialog(
            instruktur = instToDelete, // Instruktur yang akan dihapus
            onConfirm = {
                onDeleteInstClick(instToDelete!!) // Menghapus instruktur jika tombol konfirmasi ditekan
                showDialog = false // Menutup dialog
            },
            onDismiss = { showDialog = false } // Menutup dialog jika tombol batal ditekan
        )
    }
}

// Fungsi TableInst untuk menampilkan kolom tabel
@Composable
fun TableInst(
    text: String, // Teks yang akan ditampilkan dalam kolom
    width: Dp, // Lebar kolom
    isHeader: Boolean = false // Menandakan apakah kolom ini adalah header tabel
) {
    Box(
        modifier = Modifier
            .width(width), // Mengatur lebar kolom
        contentAlignment = Alignment.CenterStart // Menyusun teks di sebelah kiri
    ) {
        // Menampilkan teks dengan gaya sesuai header atau bukan
        Text(
            text = text,
            style = if (isHeader) {
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, // Menebalkan teks header
                    color = MaterialTheme.colorScheme.onPrimary // Warna teks header
                )
            } else {
                MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground // Warna teks untuk baris data
                )
            }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    instruktur: Instruktur?,
    onConfirm: () -> Unit, // Fungsi yang dijalankan jika pengguna mengonfirmasi penghapusan
    onDismiss: () -> Unit // Fungsi yang dijalankan jika pengguna membatalkan dialog
) {
    // Membuat dialog konfirmasi dengan AlertDialog
    AlertDialog(
        onDismissRequest = onDismiss, // Fungsi untuk membatalkan dialog jika pengguna mengklik area luar dialog
        title = { Text("Konfirmasi Hapus") }, // Judul dialog
        text = {
            // Pesan teks yang menanyakan apakah pengguna yakin akan menghapus instruktur
            Text("Apakah Anda yakin ingin menghapus data instruktur ${instruktur?.namaInstruktur}?")
        },
        confirmButton = {
            // Tombol untuk mengonfirmasi penghapusan
            Button(onClick = onConfirm) {
                Text("Hapus") // Teks yang muncul pada tombol konfirmasi
            }
        },
        dismissButton = {
            // Tombol untuk membatalkan penghapusan
            Button(onClick = onDismiss) {
                Text("Batal") // Teks yang muncul pada tombol batal
            }
        }
    )
}
