package com.example.projectakhir_pam.ui.view.Pendaftaran

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.UpdatePendViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// update view : untuk menampilkan update data

object DestinasiUpdatePend: DestinasiNavigasi {
    override val route = "updatePend"
    override val titleRes = "Update Pendaftaran" // judul halaman
    const val id_pendaftaran = "id_pendaftaran"
    val routeWithArgs = "$route/{$id_pendaftaran}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePendView( // untuk memperbarui data pendaftaran dengan navigasi kembali
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdatePendViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Pendaftaran",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertPendUiState = updateViewModel.updatePendUiState,
            onPendValueChange = updateViewModel::updateInsertPendState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updatePend()
                    onNavigateUp()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}