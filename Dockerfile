FROM openjdk:23-jdk-oracle AS builder

WORKDIR /compileddir

COPY mvnw . 
COPY pom.xml . 
COPY .mvn .mvn 
COPY src src

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

ENV PORT=8080
EXPOSE ${PORT}

FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app
WORKDIR ${WORK_DIR}


COPY --from=builder /compileddir/target/investmentportfolio-0.0.1-SNAPSHOT.jar investmentportfolio.jar

ENV PORT=8080
EXPOSE ${PORT}

ENV REDIS_DB_PUBLISHINGHOST= 
ENV REDIS_DB_HOST=junction.proxy.rlwy.net
ENV REDIS_DB_PORT=6379
ENV REDIS_DB_USERNAME= 
ENV REDIS_DB_PASSWORD=

ENV SPRING_PROFILE=prd

# Run the Spring Boot application with the specified profile
ENTRYPOINT ["java", "-jar", "investmentportfolio.jar", "--spring.profiles.active=${SPRING_PROFILE}"]
