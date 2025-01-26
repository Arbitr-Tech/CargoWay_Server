#!/bin/sh
set -e

minio server --address ":${MINIO_API_PORT}" --console-address ":${MINIO_WEBUI_PORT}" /data &

sleep 5

mc alias set local http://localhost:${MINIO_API_PORT} ${MINIO_ROOT_USER} ${MINIO_ROOT_PASSWORD}
mc mb -p local/${MINIO_BUCKET_NAME}
mc anonymous set public local/${MINIO_BUCKET_NAME}

echo "Бакет '${MINIO_BUCKET_NAME}' создан и доступен публично."

wait