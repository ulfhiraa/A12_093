package com.example.projectakhir_pam.ui.view.Kursus

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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.data.InstrukturList
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
fun ItemDetailKur(
    modifier: Modifier = Modifier,
    kursus: Kursus,
    viewModel: DetailKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val kursus = viewModel.detailKurUiState.detailKurUiEvent

    val instrukturList = InstrukturList.DataInstruktur()
    val namaInstruktur = instrukturList.find { it.first == kursus.id_instruktur }?.second ?: "Instruktur not found"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
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
                ComponentDetailKur(
                    judul = "ID Kursus",
                    isinya = kursus.id_kursus,
                    icon = Icons.Default.AccountBox,
                    iconBackground = MaterialTheme.colorScheme.primaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "Nama Kursus",
                    isinya = kursus.namaKursus,
                    icon = Icons.Default.Star,
                    iconBackground = MaterialTheme.colorScheme.secondaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "Deskripsi",
                    isinya = kursus.deskripsi,
                    icon = Icons.Default.Info,
                    iconBackground = MaterialTheme.colorScheme.tertiaryContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "Kategori",
                    isinya = kursus.kategori,
                    icon = Icons.Default.List,
                    iconBackground = MaterialTheme.colorScheme.errorContainer
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "Harga",
                    isinya = "Rp${kursus.harga}",
                    icon = Icons.Default.ShoppingCart,
                    iconBackground = MaterialTheme.colorScheme.primary
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "ID Instruktur",
                    isinya = kursus.id_instruktur,
                    icon = Icons.Default.Person,
                    iconBackground = MaterialTheme.colorScheme.secondary
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                ComponentDetailKur(
                    judul = "Nama Instruktur",
                    isinya = namaInstruktur,
                    icon = Icons.Default.Face,
                    iconBackground = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun ComponentDetailKur(
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
