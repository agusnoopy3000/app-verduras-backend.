package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
// import com.kborowy.authprovider.firebase.firebase // Not using for now
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.io.File

// Define the missing Principal to fix the compilation error.
data class MyAuthenticatedUser(val id: String) : Principal

fun Application.configureSecurity() {

    install(Authentication) {
        // The firebase auth provider from the template is not configured and causes a compilation error.
        // We will comment it out for now. We will configure it properly later.
        /*
        firebase {
            adminFile = File("path/to/admin/file.json")
            realm = "My Server"
            validate { token ->
                MyAuthenticatedUser(id = token.uid)
            }
        }
        */

        // This is a standard JWT configuration template.
        // Please read the jwt property from the config file if you are using EngineMain
        val jwtAudience = "jwt-audience"
        val jwtDomain = "https://jwt-provider-domain/"
        val jwtRealm = "ktor sample app"
        val jwtSecret = "secret"
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}