FROM jboss/wildfly:20.0.1.Final
WORKDIR /opt/jboss/wildfly
COPY target/petshop.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD ["./bin/standalone.sh", "-b", "0.0.0.0"]