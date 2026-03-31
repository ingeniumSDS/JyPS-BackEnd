# Etapa 1: Construcción (Build)
# Usamos una imagen de Gradle con Java 21 (o la versión que estés usando)
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Ejecutamos el build de Gradle saltando los tests para acelerar el despliegue
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copiamos el wallet a una ruta segura dentro del contenedor
COPY src/main/resources/wallet /app/wallet
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]