package com.example.projectakhir_pam.ui.view.Instruktur

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
import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.DetailInstUiState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.DetailInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.toInst
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

// detail view : agar detail dapat ditampilkan dan data dapat diedit, dan dihapus

object DestinasiDetailInst : DestinasiNavigasi {
    override val route = "detailInst"
    override val titleRes = "Detail Instruktur"
    const val id_instruktur = "id_instruktur"
    val routeWithArgs = "$route/{$id_instruktur}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailInstView( // untuk menampilkan detail instruktur
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DetailInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailInst.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getInstrukturById()
                }
            )
        }
    ) { innerPadding ->

        BodyDetailInst(
            detailinstUiState = viewModel.detailInstUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailInst( // untuk menampilkan data detail instruktur berdasarkan state UI
    modifier: Modifier = Modifier,
    detailinstUiState: DetailInstUiState,
) {
    when {
        detailinstUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailinstUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailinstUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailinstUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailInst(
                    instruktur = detailinstUiState.detailInstUiEvent.toInst(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailInst( // untuk menampilkan informasi instruktur dalam kartu
    modifier: Modifier = Modifier,
    instruktur: Instruktur,
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
            ComponentDetailInst(judul = "Id Instruktur", isinya = instruktur.id_instruktur)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailInst(judul = "Nama Instruktur", isinya = instruktur.namaInstruktur)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailInst(judul = "Email", isinya = instruktur.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailInst(judul = "Nomor Telepon", isinya = instruktur.noTelpInst)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailInst(judul = "Deskripsi Keahlian", isinya = instruktur.deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailInst( // untuk menampilkan judul dan isi data instruktur
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
