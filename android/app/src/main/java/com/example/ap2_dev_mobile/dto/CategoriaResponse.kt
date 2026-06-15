package com.example.ap2_dev_mobile.dto

import java.util.UUID

data class CategoriaResponse(
    val id: UUID, val nome: String, val cor: String, val lancalmentosQtd: Int
)
