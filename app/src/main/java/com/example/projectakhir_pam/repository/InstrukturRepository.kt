package com.example.projectakhir_pam.repository

import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.model.InstrukturResponse
import com.example.projectakhir_pam.service.InstrukturService
import java.io.IOException

// mendefinisikan operasi CRUD
interface InstrukturRepository {
    // suspend fun; asinkron dan bisa "menunggu" tanpa menghentikan eksekusi program secara keseluruhan
    suspend fun getInstruktur(): InstrukturResponse

    suspend fun getInstrukturById(id_instruktur: String): Instruktur

    suspend fun insertInstruktur(instruktur: Instruktur)

    suspend fun updateInstruktur(id_instruktur: String, instruktur: Instruktur)

    suspend fun deleteInstruktur(id_instruktur: String)
}

// Mengimplementasikan interface InstrukturRepository dan menyediakan implementasi untuk setiap metode yang ada
// di dalam interface tersebut, dengan melakukan panggilan ke API melalui InstrukturService.

class NetworkInstrukturRepository(
    private val instrukturApiService: InstrukturService // Inisialisasi API service yang digunakan untuk berkomunikasi dengan server.
) : InstrukturRepository { // Mengimplementasikan interface InstrukturRepository

    // Mengambil daftar instruktur dari API
    override suspend fun getInstruktur(): InstrukturResponse {
        // Memanggil API untuk mendapatkan data instruktur
        return instrukturApiService.getInstruktur()
    }

    // Mengambil data instruktur berdasarkan ID
    override suspend fun getInstrukturById(id_instruktur: String): Instruktur {
        // Memanggil API untuk mendapatkan instruktur berdasarkan ID
        return instrukturApiService.getInstrukturById(id_instruktur).data
    }

    // Menambahkan instruktur baru melalui API
    override suspend fun insertInstruktur(instruktur: Instruktur) {
        // Memanggil API untuk menambahkan instruktur baru
        instrukturApiService.insertInstruktur(instruktur)
    }

    // Mengupdate data instruktur berdasarkan ID
    override suspend fun updateInstruktur(id_instruktur: String, instruktur: Instruktur) {
        // Memanggil API untuk memperbarui data instruktur
        instrukturApiService.updateInstruktur(id_instruktur, instruktur)
    }

    // Menghapus instruktur berdasarkan ID
    override suspend fun deleteInstruktur(id_instruktur: String) {
        try {
            // Memanggil API untuk menghapus instruktur berdasarkan ID
            val response = instrukturApiService.deleteInstruktur(id_instruktur)

            // Mengecek apakah respons dari server berhasil
            if (!response.isSuccessful) {
                // Jika gagal, lempar exception dengan pesan error dan status code dari server
                throw IOException(
                    "Failed to delete instruktur. HTTP Status Code: ${response.code()}"
                )
            } else {
                // Jika berhasil, tampilkan pesan sukses
                response.message()
                println(response.message()) // Menampilkan pesan sukses ke log
            }
        } catch (e: Exception) {
            // Jika terjadi error, lempar exception
            throw e
        }
    }
}
