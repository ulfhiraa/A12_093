package com.example.projectakhir_pam.ui.view.Kursus

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
import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Kursus.DetailKurUiState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.DetailKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.toKur
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel


// detail view : untuk menampilkan detail data kursus

object DestinasiDetailKur : DestinasiNavigasi {
    override val route = "detailKursus"
    override val titleRes = "Detail Kursus"
    const val id_kursus = "id_kursus"
    val routeWithArgs = "$route/{$id_kursus}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKurView( // untuk menampilkan detail kursus
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DetailKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailKur.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getKursusById()
                }
            )
        }
    ) { innerPadding ->

        BodyDetailKur(
            detailkurUiState = viewModel.detailKurUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailKur( // untuk menampilkan data detail kursus berdasarkan state UI
    modifier: Modifier = Modifier,
    detailkurUiState: DetailKurUiState,
) {
    when {
        detailkurUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailkurUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailkurUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailkurUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailKur(
                    kursus = detailkurUiState.detailKurUiEvent.toKur(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailKur( // untuk menampilkan informasi kursus dalam kartu
    modifier: Modifier = Modifier,
    kursus: Kursus,
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
            ComponentDetailKur(judul = "Id Kursus", isinya = kursus.id_kursus)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKur(judul = "Nama Kursus", isinya = kursus.namaKursus)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKur(judul = "Deskripsi", isinya = kursus.deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKur(judul = "Kategori", isinya = kursus.kategori)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKur(judul = "Harga", isinya = kursus.harga.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKur(judul = "Id Instruktur", isinya = kursus.id_instruktur)
            Spacer(modifier = Modifier.padding(4.dp))

        }
    }
}

@Composable
fun ComponentDetailKur( // untuk menampilkan judul dan isi data kursus
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