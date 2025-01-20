package com.example.projectakhir_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir_pam.BimbelApplications
import com.example.projectakhir_pam.ui.viewmodel.Siswa.DetailViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.InsertViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.UpdateViewModel

// inisialisasi view model operasi CRUD
object PenyediaViewModel{
    val Factory = viewModelFactory {
        // HOME
        initializer {
            HomeViewModel(
                BimbelApplications()
                    .container
                    .siswaRepository
            )
        }

        // INSERT
        initializer {
            InsertViewModel(
                BimbelApplications()
                    .container
                    .siswaRepository
            )
        }

        // DETAIL
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .container
                    .siswaRepository
            )
        }

        // UPDATE
        initializer {
            UpdateViewModel(
                createSavedStateHandle(),
                BimbelApplications()
                    .container
                    .siswaRepository
            )
        }
    }

    fun CreationExtras.BimbelApplications(): BimbelApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BimbelApplications)
}