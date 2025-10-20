-- Создание таблицы messages для хранения сообщений игроков
CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    text VARCHAR(256) NOT NULL
);

-- Создание индекса для быстрого поиска по UUID игрока
CREATE INDEX IF NOT EXISTS idx_messages_uuid ON messages(uuid);

-- Создание индекса для поиска по тексту
CREATE INDEX IF NOT EXISTS idx_messages_text ON messages(text);

-- Комментарии к таблице и полям
COMMENT ON TABLE messages IS 'Таблица для хранения сообщений игроков';
COMMENT ON COLUMN messages.id IS 'Уникальный идентификатор сообщения';
COMMENT ON COLUMN messages.uuid IS 'UUID игрока, отправившего сообщение';
COMMENT ON COLUMN messages.text IS 'Текст сообщения (максимум 256 символов)';
