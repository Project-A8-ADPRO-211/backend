web: java -javaagent:datadog/dd-java-agent.jar -Ddd.profiling.enabled=true -XX:FlightRecorderOptions=stackdepth=256 -Ddd.logs.injection=true -Ddd.service=backend-adpro -Ddd.env=prod -Dserver.port=$PORT -XX:+UseSerialGC -XX:MaxRAM=350m -Dspring.profiles.active=heroku $JAVA_OPTS -jar build/libs/*.jar
