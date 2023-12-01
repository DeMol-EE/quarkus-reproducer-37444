# syntax = docker/dockerfile:1.0-experimental
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 as build
COPY --chown=quarkus:quarkus pom.xml /opt/me/pom.xml
COPY --chown=quarkus:quarkus mvnw /opt/me/mvnw
COPY --chown=quarkus:quarkus .mvn /opt/me/.mvn
WORKDIR /opt/me
RUN ./mvnw \
 --batch-mode \
 quarkus:go-offline
COPY --chown=quarkus:quarkus . /opt/me
RUN ./mvnw \
 --batch-mode \
 -Dquarkus.package.type=native \
 -Dquarkus.container-image.build=true \
 package

FROM quay.io/quarkus/quarkus-micro-image:2.0 as release
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --from=build --chown=1001:root /opt/me/target/*-runner /work/application
EXPOSE 8080
USER 1001
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
