# Этап сборки
FROM maven:3.8.4-openjdk-17 as builder

# Установите рабочую директорию
WORKDIR /app

# Скопируйте только pom.xml для загрузки зависимостей
COPY pom.xml .

# Загрузите зависимости и закешируйте их
RUN mvn dependency:go-offline -B

# Теперь скопируйте исходный код
COPY src ./src

# Выполните сборку проекта
RUN mvn clean package -Dmaven.test.skip=true

# Этап выполнения
FROM eclipse-temurin:17-jre-alpine

# Установите рабочую директорию
WORKDIR /app

# Скопируйте собранный JAR файл из этапа сборки
COPY --from=builder /app/target/*.jar app.jar

# Откройте порт приложения
EXPOSE 8080

# Укажите команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
