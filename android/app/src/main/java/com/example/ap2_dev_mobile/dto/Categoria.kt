package com.example.ap2_dev_mobile.dto

import java.util.UUID

data class Categoria(
    val id: UUID, val nome: String, val cor: String, val usuario: Usuario
)
