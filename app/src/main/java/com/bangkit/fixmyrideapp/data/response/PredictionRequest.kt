package com.bangkit.fixmyrideapp.data.response


data class PredictionRequest(
    val gejala1: String,
    val gejala2: String,
    val gejala3: String,
    val jenis_motor: String,
    val merk_motor: String,
    val tahun: Int,
    val km_motor: Int,
    val pernah_servis: String
)