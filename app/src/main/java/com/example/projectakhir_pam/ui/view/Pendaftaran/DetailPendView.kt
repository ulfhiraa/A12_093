package com.example.projectakhir_pam.ui.view.Pendaftaran

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun ItemDetailPend( // untuk menampilkan informasi pendaftaran dalam card
    modifier: Modifier = Modifier,
    pendaftaran: Pendaftaran,
){

    // Format input (waktu UTC)
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoDateFormat.timeZone = TimeZone.getTimeZone("UTC") // Pastikan UTC sebagai zona waktu input

    // Format output (menampilkan waktu WIB)
    val displayDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("id", "ID"))
    displayDateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Set ke WIB (Asia/Jakarta)

    // Mengonversi waktu UTC ke Date
    val utcDate = isoDateFormat.parse(pendaftaran.tglDaftar)

    // Format menjadi waktu WIB
    val formattedDate = utcDate?.let { displayDateFormat.format(it) } ?: pendaftaran.tglDaftar

    Card(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailPend(judul = "ID Pendaftaran", isinya = pendaftaran.id_pendaftaran)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPend(judul = "ID Siswa", isinya = pendaftaran.id_siswa)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPend(judul = "ID Kursus", isinya = pendaftaran.id_kursus)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPend(judul = "Tanggal dan waktu pendaftaran", isinya = formattedDate)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailPend( // untuk menampilkan judul dan isi data pendaftaran
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}