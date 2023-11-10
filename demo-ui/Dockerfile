ARG JAVA_VERSION=17
ARG JDK_IMAGE=eclipse-temurin
ARG JDK_IMAGE_TAG=${JAVA_VERSION}-jdk
ARG JRE_IMAGE=eclipse-temurin
ARG JRE_IMAGE_TAG=${JAVA_VERSION}-jre
ARG APP_PORT=8080

FROM ${JDK_IMAGE}:${JDK_IMAGE_TAG} as build
WORKDIR /src
COPY pom.xml ./mvnw ./
COPY .mvn/ .mvn/
RUN ./mvnw -B clean -DskipDockerBuild -DskipTests dependency:resolve-plugins dependency:resolve
ADD . .
RUN ./mvnw -B clean package -DskipDockerBuild -DskipTests

FROM ${JRE_IMAGE}:${JRE_IMAGE_TAG} as main

WORKDIR /app

COPY --from=build /src/target/transform-demo-ui-0.0.1-SNAPSHOT.jar /app/transform-demo-ui-0.0.1-SNAPSHOT.jar
USER nobody
EXPOSE ${APP_PORT}
ENTRYPOINT ["java", "-jar", "/app/transform-demo-ui-0.0.1-SNAPSHOT.jar"]
