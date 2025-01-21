package com.example.projectakhir_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiDetail
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiEntry
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiHome
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiUpdate
import com.example.projectakhir_pam.ui.view.Siswa.DetailView
import com.example.projectakhir_pam.ui.view.Siswa.HomeView
import com.example.projectakhir_pam.ui.view.Siswa.InsertSisView
import com.example.projectakhir_pam.ui.view.Siswa.UpdateView

// mengatur navigasi halaman

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        // untuk memulai navigasi dari HomeScreen dan menyediakan rute ke EntryMhsScreen
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // HOME SISWA
        composable(DestinasiHome.route)
        {
            HomeView(
                navigateToItemEntry =
                {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick =
                {
                    navController.navigate("${DestinasiDetail.route}/$it")
                },
                onEditClick = {
                    navController.navigate("${DestinasiUpdate.route}/$it")
                }
            )
        }

        // TAMBAH SISWA
        composable(DestinasiEntry.route) {
            InsertSisView(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL SISWA
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiDetail.id_siswa)
            id_siswa?.let { id_siswa ->
                DetailView(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // UPDATE SISWA
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdate.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiUpdate.id_siswa)
            id_siswa?.let { id_siswa ->
                UpdateView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHome.route) }
                )
            }
        }
    }
}