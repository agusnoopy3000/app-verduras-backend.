package com.example.models

import kotlinx.serialization.Serializable

@Serializable // ¡Esto es crucial para que se pueda convertir a JSON!
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imagenUrl: String,
    var stock: Int
)
