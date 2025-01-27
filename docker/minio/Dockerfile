FROM minio/minio:latest

COPY /docker/minio/init_minio.sh /usr/bin/init_minio-1.sh

RUN chmod 777 /usr/bin/init_minio-1.sh

ENTRYPOINT ["/usr/bin/init_minio-1.sh"]