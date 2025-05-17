package com.example.s04quezada

import android.os.SystemClock

class CronometroInterno {

    private var tiempoInicio: Long = 0L
    private var tiempoPausado: Long = 0L
    private var corriendo: Boolean = false

    fun iniciar() {
        if (!corriendo) {
            tiempoInicio = SystemClock.elapsedRealtime() - tiempoPausado
            corriendo = true
        }
    }

    fun pausar() {
        if (corriendo) {
            tiempoPausado = SystemClock.elapsedRealtime() - tiempoInicio
            corriendo = false
        }
    }

    fun reiniciar() {
        tiempoInicio = 0L
        tiempoPausado = 0L
        corriendo = false
    }

    fun obtenerTiempoTranscurrido(): Long {
        return if (corriendo) {
            SystemClock.elapsedRealtime() - tiempoInicio
        } else {
            tiempoPausado
        }
    }
}
