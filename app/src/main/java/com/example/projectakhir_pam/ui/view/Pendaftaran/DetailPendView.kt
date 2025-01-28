package com.example.projectakhir_pam.ui.view.Pendaftaran

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.data.KursusList
import com.example.projectakhir_pam.data.SiswaList
import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.DetailPendUiState
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.DetailPendViewModel
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.toPend
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// detail view : untuk menampilkan detail data pendaftaran

object DestinasiDetailPend : DestinasiNavigasi {
    override val route = "detailPend"
    override val titleRes = "Detail Pendaftaran"
    const val id_pendaftaran = "id_pendaftaran"
    val routeWithArgs = "$route/{$id_pendaftaran}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPendView( // untuk menampilkan detail pendaftaran
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DetailPendViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailPend.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPendaftaranById()
                }
            )
        }
    ) { innerPadding ->

        BodyDetailPend(
            detailpendUiState = viewModel.detailPendUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailPend( // untuk menampilkan data detail pendaftaran berdasarkan state UI
    modifier: Modifier = Modifier,
    detailpendUiState: DetailPendUiState,
) {
    when {
        detailpendUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailpendUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailpendUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailpendUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPend(
                    pendaftaran = detailpendUiState.detailPendUiEvent.toPend(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailPend(
    modifier: Modifier = Modifier,
    pendaftaran: Pendaftaran,
    viewModel: DetailPendViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Mengambil informasi pendaftaran yang sedang diproses dari ViewModel
    val pendaftaran = viewModel.detailPendUiState.detailPendUiEvent

    // Mengambil data siswa dan kursus dari list yang sudah ada
    val siswaList = SiswaList.DataSiswa() // Daftar siswa
    val kursusList = KursusList.DataKursus() // Daftar kursus

    // Mencari nama siswa berdasarkan id_siswa yang ada dalam data pendaftaran
    val namaSiswa = siswaList.find {
        it.first == pendaftaran.id_siswa
    }
        ?.second ?: "siswa not found" // Jika tidak ditemukan, tampilkan "siswa not found"

    // Mencari nama kursus berdasarkan id_kursus yang ada dalam data pendaftaran
    val namaKursus = kursusList.find {
        it.first == pendaftaran.id_kursus
    }
        ?.second ?: "kursus not found" // Jika tidak ditemukan, tampilkan "kursus not found"

    // Format input (waktu UTC) yang diambil dari pendaftaran
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoDateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set zona waktu input ke UTC

    // Format output untuk menampilkan waktu dalam format WIB
    val displayDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("id", "ID"))
    displayDateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Set zona waktu ke WIB (Asia/Jakarta)

    // Mengonversi waktu UTC yang diambil dari pendaftaran ke dalam objek Date
    val utcDate = isoDateFormat.parse(pendaftaran.tglDaftar)

    // Mengubah waktu UTC menjadi waktu WIB sesuai format yang telah ditentukan
    val formattedDate = utcDate?.let { displayDateFormat.format(it) } ?: pendaftaran.tglDaftar

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(15.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Menampilkan komponen detail untuk setiap informasi pendaftaran
                ComponentDetailPend(
                    judul = "ID Pendaftaran",
                    isinya = pendaftaran.id_pendaftaran,
                    icon = Icons.Default.AccountBox,
                    iconBackground = MaterialTheme.colorScheme.primaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "ID Siswa",
                    isinya = pendaftaran.id_siswa,
                    icon = Icons.Default.Person,
                    iconBackground = MaterialTheme.colorScheme.secondaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "Nama Siswa",
                    isinya = namaSiswa,
                    icon = Icons.Default.Face,
                    iconBackground = MaterialTheme.colorScheme.tertiaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "ID Kursus",
                    isinya = pendaftaran.id_kursus,
                    icon = Icons.Default.List,
                    iconBackground = MaterialTheme.colorScheme.errorContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "Nama Kursus",
                    isinya = namaKursus,
                    icon = Icons.Default.Star,
                    iconBackground = MaterialTheme.colorScheme.primary
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "Tanggal dan waktu pendaftaran",
                    isinya = formattedDate,
                    icon = Icons.Default.DateRange,
                    iconBackground = MaterialTheme.colorScheme.secondary
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailPend(
                    judul = "Status pendaftaran",
                    isinya = pendaftaran.status,
                    icon = Icons.Default.Check,
                    iconBackground = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun ComponentDetailPend(
    judul: String,
    isinya: String,
    icon: ImageVector,
    iconBackground: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = judul,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 1f)
                )
            )
            Text(
                text = isinya,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
