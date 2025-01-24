package com.example.projectakhir_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir_pam.ui.view.DestinasiHome
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiDetailInst
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiEntryInst
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiHomeInst
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiUpdateInst
import com.example.projectakhir_pam.ui.view.Instruktur.DetailInstView
import com.example.projectakhir_pam.ui.view.Instruktur.HomeInstView
import com.example.projectakhir_pam.ui.view.Instruktur.InsertInstView
import com.example.projectakhir_pam.ui.view.Instruktur.UpdateInstView
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiDetailKur
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiEntryKur
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiHomeKur
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiUpdateKur
import com.example.projectakhir_pam.ui.view.Kursus.DetailKurView
import com.example.projectakhir_pam.ui.view.Kursus.HomeKurView
import com.example.projectakhir_pam.ui.view.Kursus.InsertKurView
import com.example.projectakhir_pam.ui.view.Kursus.UpdateKurView
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiDetailPend
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiEntryPend
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiHomePend
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiUpdatePend
import com.example.projectakhir_pam.ui.view.Pendaftaran.DetailPendView
import com.example.projectakhir_pam.ui.view.Pendaftaran.HomePendView
import com.example.projectakhir_pam.ui.view.Pendaftaran.InsertPendView
import com.example.projectakhir_pam.ui.view.Pendaftaran.UpdatePendView
import com.example.projectakhir_pam.ui.view.PilihanHomeView
import com.example.projectakhir_pam.ui.view.SectionHeader
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiDetailSis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiEntrySis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiHomeSis
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiUpdateSis
import com.example.projectakhir_pam.ui.view.Siswa.DetailSisView
import com.example.projectakhir_pam.ui.view.Siswa.HomeSisView
import com.example.projectakhir_pam.ui.view.Siswa.InsertSisView
import com.example.projectakhir_pam.ui.view.Siswa.UpdateSisView

// mengatur navigasi halaman

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        // untuk memulai navigasi dari HomeScreen dan menyediakan rute ke EntryMhsScreen
        navController = navController,
//        startDestination = DestinasiHomeSis.route,
//        startDestination = DestinasiHomeInst.route,
//        startDestination = DestinasiHomeKursus.route,
//        startDestination = DestinasiHomePend.route,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // MAIN HOME
        composable(
            route = DestinasiHome.route
        ){
            PilihanHomeView(
                onKursusClick = {
                    navController.navigate(DestinasiHomeKur.route)
                },
                onSiswaClick = {
                    navController.navigate(DestinasiHomeSis.route)
                },
                onInstrukturClick = {
                    navController.navigate(DestinasiHomeInst.route)
                },
                onPendaftaranClick = {
                    navController.navigate(DestinasiHomePend.route)
                }
            )
            SectionHeader()
        }

        // HOME SISWA
        composable(DestinasiHomeSis.route)
        {
            HomeSisView(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
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
            InsertSisView(
                navigateBack = {
                    navController.navigate(DestinasiHomeSis.route)
                {
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
                DetailSisView(
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
                UpdateSisView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHomeSis.route) }
                )
            }
        }

        // == DATA INSTRUKTUR ==

        // HOME INSTRUKTUR
        composable(DestinasiHomeInst.route)
        {
            HomeInstView(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToItemEntry =
                {
                    navController.navigate(DestinasiEntryInst.route)
                },
                onDetailInstClick =
                {
                    navController.navigate("${DestinasiDetailInst.route}/$it")
                },
                onEditInstClick = {
                    navController.navigate("${DestinasiUpdateInst.route}/$it")
                }
            )
        }

        // TAMBAH INSTRUKTUR
        composable(DestinasiEntryInst.route) {
            InsertInstView(navigateBack = {
                navController.navigate(DestinasiHomeInst.route) {
                    popUpTo(DestinasiHomeInst.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL INSTRUKTUR
        composable(
            DestinasiDetailInst.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailInst.id_instruktur) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_instruktur = it.arguments?.getString(DestinasiDetailInst.id_instruktur)
            id_instruktur?.let { id_instruktur ->
                DetailInstView(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // UPDATE INSTRUKTUR
        composable(
            DestinasiUpdateInst.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateInst.id_instruktur) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_instruktur = it.arguments?.getString(DestinasiUpdateInst.id_instruktur)
            id_instruktur?.let { id_instruktur ->
                UpdateInstView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHomeInst.route) }
                )
            }
        }

        // == DATA KURSUS ==

        // HOME KURSUS
        composable(DestinasiHomeKur.route)
        {
            HomeKurView(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToItemEntry =
                {
                    navController.navigate(DestinasiEntryKur.route)
                },
                onDetailKurClick =
                {
                    navController.navigate("${DestinasiDetailKur.route}/$it")
                },
                onEditKurClick = {
                    navController.navigate("${DestinasiUpdateKur.route}/$it")
                }
            )
        }

        // TAMBAH KURSUS
        composable(DestinasiEntryKur.route) {
            InsertKurView(navigateBack = {
                navController.navigate(DestinasiHomeKur.route) {
                    popUpTo(DestinasiHomeKur.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL KURSUS
        composable(
            DestinasiDetailKur.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKur.id_kursus) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_kursus = it.arguments?.getString(DestinasiDetailKur.id_kursus)
            id_kursus?.let { id_kursus ->
                DetailKurView(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
//
        // UPDATE KURSUS
        composable(
            DestinasiUpdateKur.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateKur.id_kursus) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_kursus = it.arguments?.getString(DestinasiUpdateKur.id_kursus)
            id_kursus?.let { id_kursus ->
                UpdateKurView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHomeKur.route) }
                )
            }
        }

        // == DATA PENDAFTARAN ==

        // HOME PENDAFTARAN
        composable(DestinasiHomePend.route)
        {
            HomePendView(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToItemEntry =
                {
                    navController.navigate(DestinasiEntryPend.route)
                },
                onDetailPendClick =
                {
                    navController.navigate("${DestinasiDetailPend.route}/$it")
                },
                onEditPendClick = {
                    navController.navigate("${DestinasiUpdateKur.route}/$it")
                }
            )
        }

        // TAMBAH PENDAFTARAN
        composable(DestinasiEntryPend.route) {
            InsertPendView(navigateBack = {
                navController.navigate(DestinasiHomePend.route) {
                    popUpTo(DestinasiHomePend.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL PENDAFTARAN
        composable(
            DestinasiDetailPend.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPend.id_pendaftaran) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_pendaftaran = it.arguments?.getString(DestinasiDetailPend.id_pendaftaran)
            id_pendaftaran?.let { id_pendaftaran ->
                DetailPendView(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
//
        // UPDATE PENDAFTARAN
        composable(
            DestinasiUpdatePend.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePend.id_pendaftaran) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_pendaftaran = it.arguments?.getString(DestinasiUpdatePend.id_pendaftaran)
            id_pendaftaran?.let { id_pendaftaran ->
                UpdatePendView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHomePend.route) }
                )
            }
        }
    }
}
