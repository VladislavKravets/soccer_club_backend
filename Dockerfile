# Використовуємо базовий образ Ubuntu для збірки
FROM ubuntu:latest AS build

# Оновлюємо пакети та встановлюємо необхідні залежності
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Завантажуємо та встановлюємо Gradle
ENV GRADLE_VERSION=7.4
RUN wget -q "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" && \
    unzip "gradle-${GRADLE_VERSION}-bin.zip" -d /opt && \
    rm -f "gradle-${GRADLE_VERSION}-bin.zip"
ENV GRADLE_HOME=/opt/gradle-${GRADLE_VERSION}
ENV PATH=$PATH:$GRADLE_HOME/bin

# Копіюємо всі файли з кореневої директорії додатку в контейнер
WORKDIR /app
COPY . .

# Виконуємо збірку додатку
RUN gradle build --no-daemon

# Використовуємо базовий образ OpenJDK для запуску
FROM openjdk:17-jdk-slim

# Переміщуємо зібраний JAR файл з контейнера збірки в образ для запуску
COPY --from=build /app/build/libs/*.jar /app.jar

# Вказуємо, що контейнер використовує порт 8080
EXPOSE 8080

# Вказуємо точку входу для запуску додатку
ENTRYPOINT ["java", "-jar", "/app.jar"]
