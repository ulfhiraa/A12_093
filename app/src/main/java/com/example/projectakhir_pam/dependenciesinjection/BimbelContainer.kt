package com.example.projectakhir_pam.dependenciesinjection

import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.repository.InstrukturRepository
import com.example.projectakhir_pam.repository.KursusRepository
import com.example.projectakhir_pam.repository.NetworkInstrukturRepository
import com.example.projectakhir_pam.repository.NetworkKursusRepository
import com.example.projectakhir_pam.repository.NetworkPendaftaranRepository
import com.example.projectakhir_pam.repository.NetworkSiswaRepository
import com.example.projectakhir_pam.repository.PendaftaranRepository
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.service.InstrukturService
import com.example.projectakhir_pam.service.KursusService
import com.example.projectakhir_pam.service.PendaftaranService
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
    val pendaftaranRepository: PendaftaranRepository // pendaftaran
}

// untuk menyediakan akses ke API
class BimbelContainer : CourseContainer{
    private val baseUrlSiswa = "http://10.0.2.2:3000/api/siswa/" // url data siswa
    private val baseUrlInst = "http://10.0.2.2:3000/api/instruktur/" // url data instruktur
    private val baseUrlPend = "http://10.0.2.2:3000/api/pendaftaran/" // url data pendaftaran
    private val baseUrlKursus = "http://10.0.2.2:3000/api/kursus/" // url data kursus

    // Mendeklarasikan pengaturan format data JSON
    private val json = Json { ignoreUnknownKeys = true } // Mengatur agar format JSON bisa menerima data yang tidak dikenali

    //menggunakan Retrofit (sebuah pustaka untuk memudahkan komunikasi dengan API)
    // untuk membuat repositori yang mengelola data siswa dari API.

    // Retrofit siswa
    private val siswaRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlSiswa)
        .build()

    // Retrofit  instruktur
    private val instrukturRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlInst)
        .build()

    // Retrofit Kursus
    private val kursusRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlKursus)
        .build()

    // Retrofit Pendaftaran
    private val pendaftaranRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlPend)
        .build()


    // Membuat layanan untuk mengakses data siswa melalui API
    // inisialisasi SiswaService untuk mengakses API siswa
    private val siswaService: SiswaService by lazy {
        siswaRetrofit.create(SiswaService::class.java)
    }

    // inisialisasi INSTRUKTUR SERVICE untuk mengakses API Instruktur
    private val instrukturService: InstrukturService by lazy {
        instrukturRetrofit.create(InstrukturService::class.java)
    }

    // inisialisasi KURSUS SERVICE untuk mengakses API Kursus
    private val kursusService: KursusService by lazy {
        kursusRetrofit.create(KursusService::class.java)
    }

    // inisialisasi PENDAFTARAN SERVICE untuk mengakses API Pendaftaran
    private val pendaftaranService: PendaftaranService by lazy {
        pendaftaranRetrofit.create(PendaftaranService::class.java)
    }

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
    override val pendaftaranRepository: PendaftaranRepository by lazy {
        NetworkPendaftaranRepository(pendaftaranService)
    }

    // by lazy untuk menunda pembuatan atau inisialisasi sebuah objek sampai objek tersebut benar-benar diperlukan.

}
