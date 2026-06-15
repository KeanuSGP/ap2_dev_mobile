package com.example.ap2_dev_mobile.dto

data class DashboardResponse(
    val total: Float, val receitasMes: Float, val despesasMes: Float, val ultimasTransacoes: List<TransacaoResumoTelaInicial>
)
