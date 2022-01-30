FROM maven:3.8.2-jdk-11 as build
WORKDIR /usr/src/app
COPY . .
RUN mvn clean package

FROM tomcat:9-jdk11
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/ROOT
COPY --from=build "/usr/src/app/target/*.war" "/usr/local/tomcat/webapps/ROOT.war"
