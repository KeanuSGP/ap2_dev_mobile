package com.example.ap2_dev_mobile.dto

import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class TransacaoResumoTelaInicial(
    val id: UUID, val item: String, val valor: Float, val vencimento: String, val dataPagamento: String, val status: String
)
