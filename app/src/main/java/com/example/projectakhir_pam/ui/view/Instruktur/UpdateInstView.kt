package com.example.projectakhir_pam.ui.view.Instruktur

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
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.UpdateInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

// update view : untuk menampilkan update data

object DestinasiUpdateInst: DestinasiNavigasi {
    override val route = "updateInst"
    override val titleRes = "Update Instruktur" // judul halaman
    const val id_instruktur = "id_instruktur"
    val routeWithArgs = "$route/{$id_instruktur}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInstView( // Fungsi untuk menampilkan tampilan pembaruan data instruktur
    navigateBack: () -> Unit, // Fungsi untuk menavigasi kembali
    onNavigateUp: () -> Unit, // Fungsi untuk mengarahkan ke tampilan sebelumnya setelah update
    modifier: Modifier = Modifier,
    updateViewModel: UpdateInstViewModel = viewModel(factory = PenyediaViewModel.Factory) //  ViewModel untuk mengelola data
) {
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Menentukan  scroll pada app bar

    Scaffold( // Menyusun tampilan dasar
        topBar = {
            CustomeTopAppBar( // Menampilkan app bar dengan judul "Update Instruktur"
                title = "Update Instruktur",
                canNavigateBack = true, // Tombol untuk navigasi kembali
                scrollBehavior = scrollBehavior, // Mengatur perilaku scroll
                navigateUp = navigateBack // navigasi kembali
            )
        },
        modifier = modifier
    ) { innerPadding ->
        // inner padding membantu menciptakan ruang di dalam Scaffold agar konten tampak lebih teratur dan tidak terlalu dekat dengan batas tampilan.

        EntryBody( // Menampilkan form input untuk memperbarui data instruktur
            insertInstUiState = updateViewModel.updateInstUiState, // State yang menyimpan data instruktur
            onInstValueChange = updateViewModel::updateInsertInstState, // Fungsi untuk mengubah data instruktur
            onSaveClick = {
                coroutineScope.launch { // Menjalankan operasi update data secara asinkron
                    updateViewModel.updateInst() // Mengupdate data instruktur
                    onNavigateUp() // Navigasi kembali setelah data berhasil diupdate
                }
            },
            modifier = Modifier
                .padding(innerPadding) // Memberikan padding untuk konten
                .verticalScroll(rememberScrollState()) // Mengizinkan scroll vertikal pada form
        )
    }
}
