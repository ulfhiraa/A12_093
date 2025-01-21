//package com.example.projectakhir_pam.dependenciesinjection
//
//import com.example.projectakhir_pam.repository.InstrukturRepository
//import com.example.projectakhir_pam.repository.NetworkInstrukturRepository
//import com.example.projectakhir_pam.service.InstrukturService
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import kotlinx.serialization.json.Json
//import okhttp3.MediaType.Companion.toMediaType
//import retrofit2.Retrofit
//
//// untuk mengelola koneksi API Instruktur dan menyediakan akses ke data melalui repository instruktur
//
//interface InstContainer{
//    val instrukturRepository: InstrukturRepository
//}
//
//class InstrukturContainer : InstContainer{
//    private val baseUrl = "http://10.0.2.2:3000/api/siswa/" // dipanggil api siswa
//
//    private val json = Json { ignoreUnknownKeys = true }
//
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//        .baseUrl(baseUrl)
//        .build()
//
//    private val instrukturService: InstrukturService by lazy {
//        retrofit.create(InstrukturService::class.java)
//    }
//
//    override val instrukturRepository: InstrukturRepository by lazy {
//        NetworkInstrukturRepository(instrukturService)
//    }
//}