# Используем официальный образ PostgreSQL
FROM postgres:15-alpine

# Устанавливаем переменные окружения для PostgreSQL
ENV POSTGRES_DB=minecraft_messages
ENV POSTGRES_USER=minecraft_user
ENV POSTGRES_PASSWORD=minecraft_password

# Создаем директорию для инициализации БД
RUN mkdir -p /docker-entrypoint-initdb.d

# Копируем SQL скрипт для создания таблицы
COPY init.sql /docker-entrypoint-initdb.d/

# Открываем порт PostgreSQL
EXPOSE 5432

