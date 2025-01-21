package com.example.projectakhir_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir_pam.BimbelApplications
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.DetailInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.InsertInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.UpdateInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.DetailSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.UpdateSisViewModel

// inisialisasi view model operasi CRUD
object PenyediaViewModel{
    val Factory = viewModelFactory {
        // HOME SISWA
        initializer {
            HomeSisViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .siswaRepository
            )
        }

        // INSERT SISWA
        initializer {
            InsertSisViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .siswaRepository
            )
        }

        // DETAIL SISWA
        initializer {
            DetailSisViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .siswaRepository
            )
        }

        // UPDATE SISWA
        initializer {
            UpdateSisViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .siswaRepository
            )
        }

        // == DATA INSTRUKTUR ==

        // HOME INSTRUKTUR
        initializer {
            HomeInstViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .instrukturRepository
            )
        }

        // INSERT INSTRUKTUR
        initializer {
            InsertInstViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .instrukturRepository
            )
        }

        // DETAIL INSTRUKTUR
        initializer {
            DetailInstViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .instrukturRepository
            )
        }

        // UPDATE INSTRUKTUR
        initializer {
            UpdateInstViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .instrukturRepository
            )
        }
    }
    fun CreationExtras.BimbelApplications(): BimbelApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BimbelApplications)
}