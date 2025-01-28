package com.example.projectakhir_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// model untuk mendefinisikan informasi entitas dan atribut

@Serializable
data class Instruktur( //  untuk menyimpan data instruktur
    val id_instruktur: String,

    @SerialName("nama_instruktur") // pengenalan nama atribut di database (alias)
    val namaInstruktur: String,
    val email: String,

    @SerialName("nomor_telepon")
    val noTelpInst: String,
    val deskripsi: String
)

// ADD - response JSON (untuk merespons daftar instruktur dari API )
@Serializable
data class InstrukturResponse(
    val status: Boolean, // Menyimpan status dari respons API, misalnya true jika berhasil dan false jika gagal.
    val message: String, // Menyimpan pesan terkait status respons, bisa berupa pesan keberhasilan atau error.
    val data: List<Instruktur> // Ini berisi data dari API yang diubah menjadi objek-objek Instruktur
)

// untuk merespons detail dari satu instruktur
@Serializable
data class InstrukturResponseDetail(
//  digunakan ketika API hanya mengirimkan informasi tentang satu instruktur
    val status: Boolean,
    val message: String, // Menyimpan pesan terkait status respons.
    val data: Instruktur
)