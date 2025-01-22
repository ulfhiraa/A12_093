package com.example.projectakhir_pam.dependenciesinjection

import com.example.projectakhir_pam.repository.InstrukturRepository
import com.example.projectakhir_pam.repository.KursusRepository
import com.example.projectakhir_pam.repository.NetworkInstrukturRepository
import com.example.projectakhir_pam.repository.NetworkKursusRepository
import com.example.projectakhir_pam.repository.NetworkSiswaRepository
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.service.InstrukturService
import com.example.projectakhir_pam.service.KursusService
import com.example.projectakhir_pam.service.SiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// untuk mengelola koneksi API Kursus dan menyediakan akses ke data melalui repository kursus
// untuk mendeklarasikan repository
interface CourseContainer{
    val siswaRepository: SiswaRepository  // siswa
    val instrukturRepository: InstrukturRepository // instruktur
    val kursusRepository: KursusRepository // kursus
}

// untuk menyediakan
class BimbelContainer : CourseContainer{
//    private val baseUrl = "http://10.0.2.2:3000/api/siswa/" // url data siswa
//    private val baseUrl = "http://10.0.2.2:3000/api/instruktur/" // url data instruktur
//    private val baseUrl = "http://10.0.2.2:3000/api/pendaftaran/" // url data pendaftaran
    private val baseUrl = "http://10.0.2.2:3000/api/kursus/" // url data kursus

    private val json = Json { ignoreUnknownKeys = true } //  mengatur format data JSON.

    //menggunakan Retrofit (sebuah pustaka untuk memudahkan komunikasi dengan API)
    // untuk membuat repositori yang mengelola data siswa dari API.

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

//    // Retrofit untuk siswa
//    private val siswaRetrofit: Retrofit = Retrofit.Builder()
//        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//        .baseUrl("http://10.0.2.2:3000/api/siswa/")
//        .build()
//
//    // Retrofit untuk instruktur
//    private val instrukturRetrofit: Retrofit = Retrofit.Builder()
//        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//        .baseUrl("http://10.0.2.2:3000/api/instruktur/")
//        .build()



    // Membuat layanan untuk mengakses data siswa melalui API
    // inisialisasi SiswaService untuk mengakses API siswa
    private val siswaService: SiswaService by lazy {
        retrofit.create(SiswaService::class.java)
    }

    // inisialisasi INSTRUKTUR SERVICE
    private val instrukturService: InstrukturService by lazy {
        retrofit.create(InstrukturService::class.java)
    }

    // inisialisasi KURSUS SERVICE
    private val kursusService: KursusService by lazy {
        retrofit.create(KursusService::class.java)
    }

    // inisialisasi PENDAFTARAN SERVICE


    // memanggil network repository

    // SISWA
    override val siswaRepository: SiswaRepository by lazy {
        NetworkSiswaRepository(siswaService)
    }

    // INSTRUKTUR
    override val instrukturRepository: InstrukturRepository by lazy {
        NetworkInstrukturRepository(instrukturService)
    }

    // KURSUS
    override val kursusRepository: KursusRepository by lazy {
        NetworkKursusRepository(kursusService)
    }

    // PENDAFTARAN
}