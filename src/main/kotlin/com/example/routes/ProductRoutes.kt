package com.example.routes

import com.example.models.Producto
import io.ktor.server.application.* 
import io.ktor.server.response.* 
import io.ktor.server.routing.* 

// Lista de productos hardcodeada. Simula nuestra base de datos por ahora. 
val productList = listOf( 
    Producto(1, "Tomate", "Tomate fresco de la huerta", 1.50, "Verduras", "https://res.cloudinary.com/dsyccyhqc/image/upload/v1716317929/tomate_vl9abf.png", 100), 
    Producto(2, "Lechuga", "Lechuga iceberg crujiente", 0.99, "Verduras", "https://res.cloudinary.com/dsyccyhqc/image/upload/v1716317928/lechuga_cgvmlg.png", 50), 
    Producto(3, "Manzana", "Manzana Fuji dulce y jugosa", 2.10, "Frutas", "https://res.cloudinary.com/dsyccyhqc/image/upload/v1716317928/manzana_w8c13k.png", 80) 
) 

fun Route.productRouting() { 
    route("/api") { 
        get("/productos") { 
            call.respond(productList) 
        } 
    } 
}