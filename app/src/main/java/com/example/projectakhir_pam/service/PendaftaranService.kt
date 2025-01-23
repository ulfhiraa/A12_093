package com.example.projectakhir_pam.service

import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.model.PendaftaranResponse
import com.example.projectakhir_pam.model.PendaftaranResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// untuk mendefinisikan endpoint API yang digunakan oleh aplikasi untuk berkomunikasi dengan server


interface PendaftaranService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // hubungkan dengan file .js

    @GET(".")
    suspend fun getPendaftaran(): PendaftaranResponse

    @GET("{id_pendaftaran}")
    suspend fun getPenById(@Path("id_pendaftaran") id_pendaftaran: String): PendaftaranResponseDetail

    @POST("store")
    suspend fun insertPendaftaran(@Body pendaftaran: Pendaftaran)

    @PUT("{id_pendaftaran}")
    suspend fun updatePendaftaran(@Path("id_pendaftaran") id_pendaftaran: String, @Body pendaftaran: Pendaftaran)

    @DELETE("{id_pendaftaran}")
    suspend fun deletePendaftaran(@Path("id_pendaftaran") id_pendaftaran: String): Response<Void>
}