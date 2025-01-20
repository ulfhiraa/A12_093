package com.example.projectakhir_pam.service

import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.model.SiswaResponse
import com.example.projectakhir_pam.model.SiswaResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// untuk mendefinisikan endpoint API yang digunakan oleh aplikasi untuk berkomunikasi dengan server
interface SiswaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // hubungkan dengan file .js

    @GET(".")
    suspend fun  getSiswa(): SiswaResponse

    @GET("{id_siswa}")
    suspend fun  getSiswaById(@Path("id_siswa") id_siswa: String): SiswaResponseDetail

    @POST("store")
    suspend fun insertSiswa(@Body siswa: Siswa)

    @PUT("{id_siswa}")
    suspend fun updateSiswa(@Path("id_siswa") id_siswa: String, @Body siswa: Siswa)

    @DELETE("{id_siswa}")
    suspend fun deleteSiswa(@Path("id_siswa") id_siswa: String): Response<Void>
}