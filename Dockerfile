FROM openjdk:13-jdk-alpine AS builder

WORKDIR /src

COPY . /src

RUN apk --no-cache upgrade --available && \
  apk --no-cache add \
    bash \
    curl \
    maven \
    netcat-openbsd \
    vim && \
    mkdir -p /m2/repository && \
    ./mvnw -Dmaven.test.skip -Dmaven.repo.local=/m2/repository clean install

FROM openjdk:13-jdk-alpine

WORKDIR /app

COPY --from=builder /src/target/*.jar /app/app.jar

RUN apk --no-cache upgrade --available && \
  apk --no-cache add \
    bash \
    curl \
    netcat-openbsd \
    vim

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
    