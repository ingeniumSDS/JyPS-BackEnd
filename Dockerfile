# Etapa 1: Construcción (Build)
FROM gradle:8.14-jdk21 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .

# Usamos bootJar para asegurar que el manifest esté correcto
RUN gradle bootJar -x test --no-daemon

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Wallet de Oracle
COPY src/main/resources/wallet /app/wallet

# --- CAMBIO CLAVE ---
# Usamos un comodín para capturar el JAR ejecutable sin importar el nombre exacto
# pero evitando el -plain.jar
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8080

# Aseguramos que el archivo tenga permisos de lectura
ENTRYPOINT ["java", "-jar", "/app/app.jar"]