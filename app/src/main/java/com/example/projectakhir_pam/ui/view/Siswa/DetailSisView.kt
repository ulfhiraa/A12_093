package com.example.projectakhir_pam.ui.view.Siswa

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
                ComponentDetailSis(
                    judul = "ID Siswa",
                    isinya = siswa.id_siswa,
                    icon = Icons.Default.AccountBox,
                    iconBackground = MaterialTheme.colorScheme.primaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailSis(
                    judul = "Nama Siswa",
                    isinya = siswa.namaSiswa,
                    icon = Icons.Default.Face,
                    iconBackground = MaterialTheme.colorScheme.secondaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailSis(
                    judul = "Email",
                    isinya = siswa.email,
                    icon = Icons.Default.Email,
                    iconBackground = MaterialTheme.colorScheme.tertiaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailSis(
                    judul = "Nomor Telepon",
                    isinya = siswa.noTelpSiswa,
                    icon = Icons.Default.Phone,
                    iconBackground = MaterialTheme.colorScheme.errorContainer
                )
            }
        }
    }
}

@Composable
fun ComponentDetailSis(
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
