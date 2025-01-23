package com.example.projectakhir_pam.repository

import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.model.PendaftaranResponse
import com.example.projectakhir_pam.service.PendaftaranService
import java.io.IOException

// repository : sebagai perantara antara aplikasi dan penyimpanan data

// mendefinisikan operasi CRUD
interface PendaftaranRepository {
    // mengambil data seluruh pendaftaran
    suspend fun getPendaftaran(): PendaftaranResponse

    // mengambil data pendaftaran berdasarkan id_pendaftaran
    suspend fun getPendaftaranById(id_pendaftaran: String): Pendaftaran

    // menambah data
    suspend fun insertPendaftaran(pendaftaran: Pendaftaran)

    // mengubah data
    suspend fun updatePendaftaran(id_pendaftaran: String, pendaftaran: Pendaftaran)

    // menghapus data
    suspend fun deletePendaftaran(id_pendaftaran: String)
}

// untuk mengelola komunikasi antara aplikasi dan API yang menyediakan data pendaftaran

class NetworkPendaftaranRepository(
    private val pendaftaranApiService: PendaftaranService
) : PendaftaranRepository{

    override suspend fun getPendaftaran(): PendaftaranResponse {
        return pendaftaranApiService.getPendaftaran()
    }

    override suspend fun getPendaftaranById(id_pendaftaran: String): Pendaftaran {
        return pendaftaranApiService.getPenById(id_pendaftaran).data
    }

    override suspend fun insertPendaftaran(pendaftaran: Pendaftaran) {
        pendaftaranApiService.insertPendaftaran(pendaftaran)
    }

    override suspend fun updatePendaftaran(id_pendaftaran: String, pendaftaran: Pendaftaran) {
        pendaftaranApiService.updatePendaftaran(id_pendaftaran, pendaftaran)
    }

    override suspend fun deletePendaftaran(id_pendaftaran: String) {
        try {
            val response = pendaftaranApiService.deletePendaftaran(id_pendaftaran)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete pendaftaran. HTTP Status Code:" +
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