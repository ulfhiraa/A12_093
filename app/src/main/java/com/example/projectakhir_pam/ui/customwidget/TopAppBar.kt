package com.example.projectakhir_pam.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//  untuk menampilkan bar atas dengan opsi navigasi, ikon refresh, dan mendukung scroll behavior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
){
    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "", modifier.clickable {
                onRefresh()
            })
        },

        modifier = modifier,
        scrollBehavior = scrollBehavior, navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        }
    )
}