package com.example.projectakhir_pam.data

// untuk menyimpan data status pendaftaran
// Objek Status berisi daftar status yang tidak dapat diubah (immutable)
object Status {
    // listStatus adalah daftar status yang hanya berisi dua nilai: "Aktif" dan "Tidak Aktif"
    // Daftar ini tidak bisa diubah setelah dibuat (immutable)
    val listStatus = listOf( // listOf membuat daftar yang tidak dapat diubah
        "Aktif", // Status pertama
        "Tidak Aktif" // Status kedua
    )
}

