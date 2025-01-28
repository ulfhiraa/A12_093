package com.example.projectakhir_pam.ui.view.Instruktur

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
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
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp) // padding seperti di ItemDetailKur
            .shadow(15.dp), // memberi shadow seperti pada ItemDetailKur
        elevation = CardDefaults.cardElevation(12.dp), // memberikan elevation yang sama dengan ItemDetailKur
        shape = RoundedCornerShape(10.dp) // shape yang sama dengan ItemDetailKur
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
                ComponentDetailInst(
                    judul = "ID Instruktur",
                    isinya = instruktur.id_instruktur,
                    icon = Icons.Default.AccountBox,
                    iconBackground = MaterialTheme.colorScheme.primaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailInst(
                    judul = "Nama Instruktur",
                    isinya = instruktur.namaInstruktur,
                    icon = Icons.Default.Face,
                    iconBackground = MaterialTheme.colorScheme.secondaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailInst(
                    judul = "Email",
                    isinya = instruktur.email,
                    icon = Icons.Default.Email,
                    iconBackground = MaterialTheme.colorScheme.tertiaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailInst(
                    judul = "Nomor Telepon",
                    isinya = instruktur.noTelpInst,
                    icon = Icons.Default.Phone,
                    iconBackground = MaterialTheme.colorScheme.errorContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailInst(
                    judul = "Deskripsi Keahlian",
                    isinya = instruktur.deskripsi,
                    icon = Icons.Default.Info,
                    iconBackground = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ComponentDetailInst( // untuk menampilkan judul dan isi data instruktur
    modifier: Modifier = Modifier,
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
                .background(iconBackground, shape = CircleShape),
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
