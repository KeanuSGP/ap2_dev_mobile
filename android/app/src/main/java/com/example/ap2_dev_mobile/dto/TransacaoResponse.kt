package com.example.ap2_dev_mobile.dto

import java.util.UUID

data class TransacaoResponse(
    val id: UUID, val item: String, val total: Float, val dataCompra: String, val dataVencimento: String, val tipo: String, val status: String, val categoria: Categoria, val contaFinanceira: ContaFinanceiraResponse, val descricao: String
)
