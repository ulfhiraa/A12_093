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

    // Menambahkan headers untuk setiap permintaan HTTP.
    // Headers ini memberitahu server tentang format data yang diharapkan.

    @Headers(
        "Accept: application/json",  // Mengharapkan respons dalam format JSON.
        "Content-Type: application/json"  // Mengirim data dalam format JSON.
    )

    // hubungkan dengan file .js

    @GET(".") // mengambil seluruh data instruktur
    suspend fun  getInstruktur(): InstrukturResponse

    @GET("{id_instruktur}") // mengambil satu data
    suspend fun  getInstrukturById(@Path("id_instruktur") id_instruktur: String): InstrukturResponseDetail

    @POST("store") // menambah data
    suspend fun insertInstruktur(@Body instruktur: Instruktur)

    @PUT("{id_instruktur}") // mengubah data
    suspend fun updateInstruktur(@Path("id_instruktur") id_instruktur: String, @Body instruktur: Instruktur)

    @DELETE("{id_instruktur}") // menghapus data
    suspend fun deleteInstruktur(@Path("id_instruktur") id_instruktur: String): Response<Void>
}