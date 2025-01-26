package com.example.projectakhir_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Siswa(
//    @SerialName("id_siswa")
//    val id_siswa: String,
    val id_siswa: Int,

    @SerialName("nama_siswa")
    val namaSiswa: String,
    val email: String,

    @SerialName("nomor_telepon")
    val noTelpSiswa: String
)


// ADD - response JSON
@Serializable
data class SiswaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Siswa>
)

@Serializable
data class SiswaResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Siswa
)