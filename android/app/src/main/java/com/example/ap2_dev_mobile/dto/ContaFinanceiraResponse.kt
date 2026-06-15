package com.example.ap2_dev_mobile.dto

import java.util.UUID

data class ContaFinanceiraResponse(
    val id: UUID,  val nome: String, val saldo: Float, val lancamentosQtd: Int
)
