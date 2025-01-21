package com.example.projectakhir_pam.dependenciesinjection

import com.example.projectakhir_pam.repository.InstrukturRepository
import com.example.projectakhir_pam.repository.NetworkInstrukturRepository
import com.example.projectakhir_pam.repository.NetworkSiswaRepository
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.service.InstrukturService
import com.example.projectakhir_pam.service.SiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// untuk mendeklarasikan siswaRepository
interface CourseContainer{
    val siswaRepository: SiswaRepository
    val instrukturRepository: InstrukturRepository
}

// untuk menyediakan
class BimbelContainer : CourseContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/siswa/" // url data siswa
//    private val baseUrl = "http://10.0.2.2:3000/api/instruktur/" // url data instruktur
//    private val baseUrl = "http://10.0.2.2:3000/api/pendaftaran/" // url data pendaftaran
//    private val baseUrl = "http://10.0.2.2:3000/api/kursus/" // url data kursus

    private val json = Json { ignoreUnknownKeys = true } //  mengatur format data JSON.

    //menggunakan Retrofit (sebuah pustaka untuk memudahkan komunikasi dengan API)
    // untuk membuat repositori yang mengelola data siswa dari API.

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Membuat layanan untuk mengakses data siswa melalui API
    // inisialisasi SiswaService untuk mengakses API siswa.
    private val siswaService: SiswaService by lazy {
        retrofit.create(SiswaService::class.java)
    }

    private val instrukturService: InstrukturService by lazy {
        retrofit.create(InstrukturService::class.java)
    }

    override val siswaRepository: SiswaRepository by lazy {
        NetworkSiswaRepository(siswaService)
    }

    override val instrukturRepository: InstrukturRepository by lazy {
        NetworkInstrukturRepository(instrukturService)
    }
}