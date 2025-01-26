FROM minio/minio:latest

COPY docker/minio/init_minio.sh /usr/bin/init_minio.sh

RUN chmod +x /usr/bin/init_minio.sh

ENTRYPOINT ["/usr/bin/init_minio.sh"]