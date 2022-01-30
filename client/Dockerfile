FROM maven:3.8.2-jdk-11
WORKDIR /usr/src/app
COPY . .
RUN mvn package site -Dmaven.test.skip
CMD sh -c "sleep 5s && \
           mvn test site"