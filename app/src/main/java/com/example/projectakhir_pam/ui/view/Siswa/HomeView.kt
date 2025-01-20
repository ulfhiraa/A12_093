package com.example.projectakhir_pam.ui.view.Siswa

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.ui.customwidget.CustomeTopAppBar
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeViewModel

/*
Home view untuk menampilkan daftar siswa dengan fitur CRUD, dan status UI (loading,success,error)
*/


object DestinasiHome : DestinasiNavigasi {
    override val route = "homeSiswa" // rute navigasi
    override val titleRes = "Home Siswa" // judul yang akan ditampilkan di halaman
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( // tampilan utama yang menampilkan daftar siswa
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                // Menampilkan judul "Home Siswa" dan tombol refresh untuk memuat ulang data siswa.
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getSis()
                }
            )
        },
        floatingActionButton = { // Tombol untuk menambahkan data siswa
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.
                padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Siswa")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.sisUIState,
            retryAction = { viewModel.getSis() }, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteSis(it.id_siswa ) // Mengambil data siswa dari repository
                viewModel.getSis() // Menghapus siswa berdasarkan ID yang dipilih
            }
        )
    }
}

@Composable
fun HomeStatus( // menampilkan UI sesuai dengan status data siswa
    homeUiState: HomeUiState,
    retryAction: () -> Unit, // untuk mencoba lagi jika pengambilan data gagal
    modifier: Modifier = Modifier,
    onDeleteClick: (Siswa) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (
        homeUiState) {
        //Menampilkan gambar loading.
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Menampilkan daftar siswa jika data berhasil diambil.
        is HomeUiState.Success ->
            if (
                homeUiState.siswa.isEmpty()){
                return Box (modifier = modifier.
                fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data Siswa" )
                }
            }else {
                SisLayout(
                    siswa = homeUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_siswa)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }

        // Menampilkan pesan error dengan tombol retry jika pengambilan data gagal.
        is HomeUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading( modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(50.dp),
       painter = painterResource(R.drawable.load),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
          painter = painterResource(id = R.drawable.failed),
            contentDescription = "Error Image",
            modifier = Modifier
                .size(100.dp) // Atur ukuran gambar menjadi 100x100 dp
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction)
        {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun SisLayout( // Menampilkan pesan error dengan tombol retry jika pengambilan data gagal.
    siswa: List<Siswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Siswa) -> Unit,
    onDeleteClick: (Siswa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(siswa) { siswa ->
            SisCard(
                siswa = siswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(siswa) }, //navigasi ke detail siswa saat data diklik
                onDeleteClick = {
                    onDeleteClick(siswa)
                }
            )
        }
    }
}

@Composable
fun SisCard( //  untuk menampilkan detail siswa dalam bentuk card
    siswa: Siswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Siswa) -> Unit = {}
){
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = siswa.namaSiswa,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(siswa)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = siswa.id_siswa,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = siswa.email,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = siswa.noTelpSiswa,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}