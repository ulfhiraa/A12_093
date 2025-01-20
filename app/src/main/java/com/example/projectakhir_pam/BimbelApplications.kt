package com.example.projectakhir_pam

import android.app.Application
import com.example.projectakhir_pam.dependenciesinjection.InstContainer
import com.example.projectakhir_pam.dependenciesinjection.InstrukturContainer
import com.example.projectakhir_pam.dependenciesinjection.SisContainer
import com.example.projectakhir_pam.dependenciesinjection.SiswaContainer

// untuk inisialisasi container dari aplikasi
class BimbelApplications:Application() {
    lateinit var sisContainer: SisContainer // siswa
    lateinit var instContainer: InstContainer // instruktur

    override fun onCreate() {
        super.onCreate()
        sisContainer= SiswaContainer() // inisialisasi container siswa
        instContainer= InstrukturContainer() // inisialisasi container instruktur
    }
}