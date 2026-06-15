package com.example.ap2_dev_mobile.dto

import java.util.UUID

data class TransacaoRequest(
    val item: String,
    val dataCompra: String,
    val dataVencimento: String,
    val total: Float,
    val categoria: CategoriaResponse?,
    val contaFinanceira: ContaFinanceiraResponse?,
    val tipo: String,
    val descricao: String
)
