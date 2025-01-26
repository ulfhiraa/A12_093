package com.example.projectakhir_pam.ui.view.Siswa

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
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.DetailSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.DetailSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.toSis

// detail view : agar detail dapat ditampilkan dan data dapat diedit, dan dihapus

object DestinasiDetailSis : DestinasiNavigasi {
    override val route = "detailSiswa"
    override val titleRes = "Detail Siswa"
    const val id_siswa = "id_siswa"
    val routeWithArgs = "$route/{$id_siswa}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSisView( // untuk menampilkan detail mahasiswa
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DetailSisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailSis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getSiswaById()
                }
            )
        }
    ) { innerPadding ->

        BodyDetailSis(
            detailsisUiState = viewModel.detailSisUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailSis( // untuk menampilkan data detail siswa berdasarkan state UI
    modifier: Modifier = Modifier,
    detailsisUiState: DetailSisUiState,
) {
    when {
        detailsisUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailsisUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailsisUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailsisUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailSis(
                    siswa = detailsisUiState.detailSisUiEvent.toSis(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailSis( // untuk menampilkan informasi siswa dalam kartu
    modifier: Modifier = Modifier,
    siswa: Siswa,
){
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
            ComponentDetailSis(judul = "Id Siswa", isinya = siswa.id_siswa.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailSis(judul = "Nama Siswa", isinya = siswa.namaSiswa)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailSis(judul = "Email", isinya = siswa.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailSis(judul = "Nomor Telepon", isinya = siswa.noTelpSiswa)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailSis( // untuk menampilkan judul dan isi data siswa
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