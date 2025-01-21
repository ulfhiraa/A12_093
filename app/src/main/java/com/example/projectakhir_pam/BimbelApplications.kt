package com.example.projectakhir_pam

import android.app.Application
import com.example.projectakhir_pam.dependenciesinjection.BimbelContainer
//import com.example.projectakhir_pam.dependenciesinjection.InstContainer
//import com.example.projectakhir_pam.dependenciesinjection.InstrukturContainer

// untuk inisialisasi container dari aplikasi
class BimbelApplications:Application() {
    lateinit var bimbelContainer: BimbelContainer // siswa
//    lateinit var instContainer: InstContainer // instruktur

    override fun onCreate() {
        super.onCreate()
        bimbelContainer= BimbelContainer() // inisialisasi container siswa
//        instContainer= InstrukturContainer() // inisialisasi container instruktur
    }
}