#BUILD container
FROM maven:3.8.4-openjdk-17 AS builder

LABEL template_creator="Miguel Doctor <migueldoctor@gmail.com>"
LABEL maintainer="Andres Uzeda <andres.uzeda@niceincontact.com>"

# Compile the main file and test files
WORKDIR /opt
COPY ./pom.xml .
COPY ./gatling-highcharts-maven-archetype.iml .
COPY ./src ./src
RUN mvn compile
RUN mvn test-compile

# Separate image keeps other build files out and drops the image size by about 150 MB
FROM maven:3.8.4-openjdk-17 AS runner
WORKDIR /opt
COPY --from=builder /opt/pom.xml pom.xml
COPY --from=builder /opt/gatling-highcharts-maven-archetype.iml gatling-highcharts-maven-archetype.iml
COPY --from=builder /opt/target/classes target/classes
COPY --from=builder /opt/target/test-classes target/test-classes