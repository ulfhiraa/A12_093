package com.example.projectakhir_pam.ui.view.Kursus

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Kursus.UpdateKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// update view : untuk menampilkan update data

object DestinasiUpdateKur: DestinasiNavigasi {
    override val route = "updateKur"
    override val titleRes = "Update Kursus" // judul halaman
    const val id_kursus = "id_kursus"
    val routeWithArgs = "$route/{$id_kursus}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKurView( // untuk memperbarui data kursus dengan navigasi kembali
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Kursus",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertKurUiState = updateViewModel.updateKurUiState,
            onKurValueChange = updateViewModel::updateInsertKurState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateKur()
                    onNavigateUp()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}