package com.example.projectakhir_pam.repository

import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.model.SiswaResponse
import com.example.projectakhir_pam.service.SiswaService
import okio.IOException

// repository : sebagai perantara antara aplikasi dan penyimpanan data

// mendefinisikan operasi CRUD
interface SiswaRepository {
    // mengambil data seluruh siswa
    suspend fun getSiswa(): SiswaResponse

    // mengambil data siswa berdasarkan id_siswa
    suspend fun getSiswaById(id_siswa: String):Siswa

    // menambah data
    suspend fun insertSiswa(siswa: Siswa)

    // mengubah data
    suspend fun updateSiswa(id_siswa: String, siswa: Siswa)

    // menghapus data
    suspend fun deleteSiswa(id_siswa: String)
}

// untuk mengelola komunikasi antara aplikasi dan API yang menyediakan data siswa

class NetworkSiswaRepository(
    private val siswaApiService: SiswaService
) : SiswaRepository{

    override suspend fun getSiswa(): SiswaResponse {
        return siswaApiService.getSiswa()
    }

    override suspend fun getSiswaById(id_siswa: String): Siswa {
        return siswaApiService.getSiswaById(id_siswa).data
    }

    override suspend fun insertSiswa(siswa: Siswa) {
        siswaApiService.insertSiswa(siswa)
    }

    override suspend fun updateSiswa(id_siswa: String, siswa: Siswa) {
        siswaApiService.updateSiswa(id_siswa, siswa)
    }

    override suspend fun deleteSiswa(id_siswa: String) {
        try {
            val response = siswaApiService.deleteSiswa(id_siswa)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete siswa. HTTP Status Code:" +
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