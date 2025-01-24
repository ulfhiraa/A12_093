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
import com.example.projectakhir_pam.ui.viewmodel.Kursus.DetailKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.InsertKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.UpdateKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.DetailPendViewModel
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.HomePendViewModel
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.InsertPendViewModel
import com.example.projectakhir_pam.ui.viewmodel.Pendaftaran.UpdatePendViewModel
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

        // == DATA KURSUS ==

        // HOME KURSUS
        initializer {
            HomeKurViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .kursusRepository
            )
        }

        // INSERT KURSUS
        initializer {
            InsertKurViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .kursusRepository
            )
        }

        // DETAIL KURSUS
        initializer {
            DetailKurViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .kursusRepository
            )
        }

        // UPDATE KURSUS
        initializer {
            UpdateKurViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .kursusRepository
            )
        }

        // == DATA PENDAFTARAM ==

        // HOME PENDAFTARAN
        initializer {
            HomePendViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .pendaftaranRepository
            )
        }

        // INSERT PENDAFTARAN
        initializer {
            InsertPendViewModel(
                BimbelApplications()
                    .bimbelContainer
                    .pendaftaranRepository
            )
        }

        // DETAIL PENDAFTARAN
        initializer {
            DetailPendViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .pendaftaranRepository
            )
        }

        // UPDATE PENDAFTARAN
        initializer {
            UpdatePendViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .bimbelContainer
                    .pendaftaranRepository
            )
        }
    }
    fun CreationExtras.BimbelApplications(): BimbelApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BimbelApplications)
}