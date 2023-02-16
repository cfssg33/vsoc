FROM mcr.microsoft.com/java/jre:8u282-zulu-ubuntu-18.04
COPY /build/libs/*.jar /home/vsoc_can.jar
ENTRYPOINT ["java", "-jar", "/home/vsoc_can.jar"]
