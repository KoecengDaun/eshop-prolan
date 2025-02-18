# Stage Builder: Build aplikasi dan hapus write permission dari JAR
FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /src/advshop
# Salin file yang diperlukan (pastikan sudah ada file .dockerignore untuk mengecualikan file sensitif)
COPY . .
RUN chmod +x gradlew && ./gradlew clean bootJar
# Hapus write permission pada file JAR
RUN find build/libs -type f -exec chmod 500 {} \;

# Stage Runner: Siapkan image final
FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner
ARG USER_NAME=advshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}
RUN addgroup -g ${USER_GID} ${USER_NAME} && \
    adduser -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}
USER ${USER_NAME}
WORKDIR /opt/advshop
# Salin file JAR dari builder, pastikan kepemilikan sudah sesuai
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/advshop/build/libs/*.jar app.jar
# Jika perlu, pastikan lagi file JAR tidak writable
RUN chmod 500 app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
