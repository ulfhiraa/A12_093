package com.example.projectakhir_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// model untuk mendefinisikan informasi entitas dan atribut

@Serializable
data class Instruktur(
//    @SerialName("id_instruktur")
    val id_instruktur: String,

    @SerialName("nama_instruktur")
    val namaInstruktur: String,
    val email: String,

    @SerialName("nomor_telepon")
    val noTelpInst: String,
    val deskripsi: String
)


// untuk membungkus respons JSON yang diterima dari API saat mengakses daftar instruktur.
// Respons ini memiliki status, pesan, dan data berupa list dari objek Instruktur.

// ADD - response JSON (untuk merespons daftar instruktur dari API )
@Serializable
data class InstrukturResponse(
    val status: Boolean,
    val message: String,
    val data: List<Instruktur>
)

// untuk membungkus respons JSON yang diterima dari API saat mengakses detail informasi dari satu instruktur.
// Respons ini memiliki status, pesan, dan data yang berisi objek Instruktur yang spesifik.

// untuk merespons detail dari satu instruktur
@Serializable
data class InstrukturResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Instruktur
)