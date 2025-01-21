package com.example.projectakhir_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiDetailSis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiEntrySis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiHomeSis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiUpdateSis
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
        startDestination = DestinasiHomeSis.route,
        modifier = Modifier,
    ) {
        // HOME SISWA
        composable(DestinasiHomeSis.route)
        {
            HomeView(
                navigateToItemEntry =
                {
                    navController.navigate(DestinasiEntrySis.route)
                },
                onDetailClick =
                {
                    navController.navigate("${DestinasiDetailSis.route}/$it")
                },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateSis.route}/$it")
                }
            )
        }

        // TAMBAH SISWA
        composable(DestinasiEntrySis.route) {
            InsertSisView(navigateBack = {
                navController.navigate(DestinasiHomeSis.route) {
                    popUpTo(DestinasiHomeSis.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL SISWA
        composable(
            DestinasiDetailSis.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailSis.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiDetailSis.id_siswa)
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
            DestinasiUpdateSis.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateSis.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiUpdateSis.id_siswa)
            id_siswa?.let { id_siswa ->
                UpdateView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHomeSis.route) }
                )
            }
        }
    }
}