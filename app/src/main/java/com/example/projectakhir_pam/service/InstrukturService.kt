package com.example.projectakhir_pam.service

import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.model.InstrukturResponse
import com.example.projectakhir_pam.model.InstrukturResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// untuk  mendefinisikan API endpoints yang berhubungan dengan operasi instruktur.

interface InstrukturService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // hubungkan dengan file .js

    @GET(".")
    suspend fun  getInstruktur(): InstrukturResponse

    @GET("{id_instruktur}")
    suspend fun  getInstrukturById(@Path("id_instruktur") id_instruktur: String): InstrukturResponseDetail

    @POST("store")
    suspend fun insertInstruktur(@Body instruktur: Instruktur)

    @PUT("{id_instruktur}")
    suspend fun updateInstruktur(@Path("id_instruktur") id_instruktur: String, @Body instruktur: Instruktur)

    @DELETE("{id_instruktur}")
    suspend fun deleteInstruktur(@Path("id_instruktur") id_instruktur: String): Response<Void>
}