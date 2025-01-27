package com.example.projectakhir_pam.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// pendaftaran data model dan response classes untuk menangani respon API
// mendefinisikan informasi entitas dan atribut

@Serializable
data class Pendaftaran(
    val id_pendaftaran: String,
    val id_siswa: String,
    val id_kursus: String,

    @SerialName("tanggal_pendaftaran")
    val tglDaftar: String,

    val status: String,
    val kategori: String
)

// ADD - response JSON
@Serializable
data class PendaftaranResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pendaftaran>
)

@Serializable
data class PendaftaranResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Pendaftaran
)