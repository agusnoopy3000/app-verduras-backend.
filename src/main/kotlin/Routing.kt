package com.example

import com.example.routes.productRouting
import io.ktor.server.application.* 
import io.ktor.server.response.* 
import io.ktor.server.routing.* 

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("¡El backend de App Verduras está vivo!")
        }

        // ¡Registramos nuestra nueva ruta de productos aquí!
        productRouting()
    }
}
