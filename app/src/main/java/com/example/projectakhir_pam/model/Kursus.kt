package com.example.projectakhir_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Kursus data model dan response classes untuk menangani respon API
// mendefinisikan informasi entitas dan atribut

@Serializable
data class Kursus(
    val id_kursus: String,

    @SerialName("nama_kursus")
    val namaKursus: String,
    val deskripsi: String,
    val kategori: String,
    val harga: String,
    val id_instruktur: String
)

// ADD - response JSON
@Serializable
data class KursusResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kursus>
)

@Serializable
data class KursusResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Kursus
)