FROM openjdk:17-slim

WORKDIR /app

# Копируем JAR файл
COPY target/*.jar app.jar

# Копируем исходные файлы для тестирования бэкапа
COPY src/main/resources/testfiles/* /app/data/

# Запускаем наши приложения
CMD ["java", "-jar", "app.jar"]