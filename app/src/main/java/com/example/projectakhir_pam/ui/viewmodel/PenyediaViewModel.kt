package com.example.projectakhir_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir_pam.BimbelApplications
import com.example.projectakhir_pam.ui.viewmodel.Siswa.DetailSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertSisViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.UpdateSisViewModel

// inisialisasi view model operasi CRUD
object PenyediaViewModel{
    val Factory = viewModelFactory {
        // HOME
        initializer {
            HomeSisViewModel(
                BimbelApplications()
                    .sisContainer
                    .siswaRepository
            )
        }

        // INSERT
        initializer {
            InsertSisViewModel(
                BimbelApplications()
                    .sisContainer
                    .siswaRepository
            )
        }

        // DETAIL
        initializer {
            DetailSisViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .sisContainer
                    .siswaRepository
            )
        }

        // UPDATE
        initializer {
            UpdateSisViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .sisContainer
                    .siswaRepository
            )
        }
    }

    fun CreationExtras.BimbelApplications(): BimbelApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BimbelApplications)
}