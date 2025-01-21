package com.example.projectakhir_pam.ui.view.Instruktur

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.UpdateInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// update view : untuk menampiilkan update data

object DestinasiUpdateInst: DestinasiNavigasi {
    override val route = "updateInst"
    override val titleRes = "Update Instruktur" // judul halaman
    const val id_instruktur = "id_instruktur"
    val routeWithArgs = "$route/{$id_instruktur}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInstView( // untuk memperbarui data instruktur dengan navigasi kembali
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Instruktur",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertInstUiState = updateViewModel.updateInstUiState,
            onInstValueChange = updateViewModel::updateInsertInstState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateInst()
                    onNavigateUp()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}