package com.example.projectakhir_pam.repository

import com.example.projectakhir_pam.model.Instruktur
import com.example.projectakhir_pam.model.InstrukturResponse
import com.example.projectakhir_pam.service.InstrukturService
import java.io.IOException

// mendefinisikan operasi CRUD
interface InstrukturRepository {
    suspend fun getInstruktur(): InstrukturResponse

    suspend fun getInstrukturById(id_instruktur: String): Instruktur

    suspend fun insertInstruktur(instruktur: Instruktur)

    suspend fun updateInstruktur(id_instruktur: String, instruktur: Instruktur)

    suspend fun deleteInstruktur(id_instruktur: String)
}

// i mengimplementasikan interface InstrukturRepository dan menyediakan implementasi untuk setiap metode yang didefinisikan dalam interface tersebut dengan melakukan panggilan ke API melalui InstrukturService
class NetworkInstrukturRepository(
    private val instrukturApiService: InstrukturService
) : InstrukturRepository{

    override suspend fun getInstruktur(): InstrukturResponse {
        return instrukturApiService.getInstruktur()
    }

    override suspend fun getInstrukturById(id_instruktur: String): Instruktur {
        return instrukturApiService.getInstrukturById(id_instruktur).data
    }

    override suspend fun insertInstruktur(instruktur: Instruktur) {
        instrukturApiService.insertInstruktur(instruktur)
    }

    override suspend fun updateInstruktur(id_instruktur: String, instruktur: Instruktur) {
        instrukturApiService.updateInstruktur(id_instruktur, instruktur)
    }

    override suspend fun deleteInstruktur(id_instruktur: String) {
        try {
            val response = instrukturApiService.deleteInstruktur(id_instruktur)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete instruktur. HTTP Status Code:" +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }
}