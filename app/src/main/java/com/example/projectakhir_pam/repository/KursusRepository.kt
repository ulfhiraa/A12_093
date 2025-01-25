package com.example.projectakhir_pam.repository


import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.model.KursusResponse
import com.example.projectakhir_pam.service.KursusService
import java.io.IOException

// repository : sebagai perantara antara aplikasi dan penyimpanan data

// mendefinisikan operasi CRUD
interface KursusRepository {
    // mengambil data seluruh kursus
    suspend fun getKursus(): KursusResponse

    // mengambil data kursus berdasarkan id_kursus
    suspend fun getKursusById(id_kursus: String): Kursus

    // mencari data berdasar kategori, id instruktur, nama kursus
    suspend fun searchKursus(
        nama_kursus: String? = null,
        kategori: String? = null,
        id_instruktur: String? = null
    ): KursusResponse

    // menambah data
    suspend fun insertKursus(kursus: Kursus)

    // mengubah data
    suspend fun updateKursus(id_kursus: String, kursus: Kursus)

    // menghapus data
    suspend fun deleteKursus(id_kursus: String)
}

// untuk mengelola komunikasi antara aplikasi dan API yang menyediakan data kursus

class NetworkKursusRepository(
    private val kursusApiService: KursusService
) : KursusRepository{

    override suspend fun getKursus(): KursusResponse {
        return kursusApiService.getKursus()
    }

    override suspend fun getKursusById(id_kursus: String): Kursus {
        return kursusApiService.getKursusById(id_kursus).data
    }

    // search
    override suspend fun searchKursus(
        nama_kursus: String?,
        kategori: String?,
        id_instruktur: String?
    ): KursusResponse {
        return kursusApiService.searchKursus(nama_kursus, kategori, id_instruktur)
    }

    override suspend fun insertKursus(kursus: Kursus) {
        kursusApiService.insertKursus(kursus)
    }

    override suspend fun updateKursus(id_kursus: String, kursus: Kursus) {
        kursusApiService.updateKursus(id_kursus, kursus)
    }

    override suspend fun deleteKursus(id_kursus: String) {
        try {
            val response = kursusApiService.deleteKursus(id_kursus)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kursus. HTTP Status Code:" +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }
}