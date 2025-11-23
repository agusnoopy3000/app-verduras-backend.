# --- Fase 1: Construcción ---
# Usa una imagen base con JDK y Gradle para construir el proyecto
FROM gradle:8.5.0-jdk17-alpine AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /home/gradle/project

# Copia los archivos de configuración de Gradle para cachear dependencias
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Descarga las dependencias. Esto se cachea si los archivos no cambian.
RUN gradle dependencies

# Copia el resto del código fuente
COPY . .

# Construye la aplicación, creando un "fat JAR" que contiene todo lo necesario
# FIX: Se añade un mecanismo de reintento para sobrevivir a fallos de red intermitentes en Render.
RUN gradle build -x test || (echo "Build failed, retrying after 5s..." && sleep 5 && gradle build -x test) || (echo "Build failed, retrying after 10s..." && sleep 10 && gradle build -x test)

# --- Fase 2: Ejecución ---
# Usa una imagen base mucho más ligera solo con Java para ejecutar la app
# FIX: Se actualiza a una imagen de Eclipse Temurin, más moderna y disponible.
FROM eclipse-temurin:17-jdk-alpine

# Expone el puerto 8080 (aunque Render lo sobreescribirá con la variable PORT)
EXPOSE 8080

# Copia solo el JAR construido de la fase anterior
COPY --from=build /home/gradle/project/build/libs/*-all.jar /app/app.jar

# El comando que se ejecutará cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
