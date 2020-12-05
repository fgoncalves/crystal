FROM adoptopenjdk/openjdk11

ARG script="crystal-analyser"
ARG binaryName="crystal-analyser-0.1-all.jar"
ARG buildDir="build"
ENV SCRIPT="${script}"

COPY "${buildDir}/libs/${binaryName}" "lib/${binaryName}"
COPY "${buildDir}/scriptsShadow/${script}" .

EXPOSE 8080 8080
ENTRYPOINT ["./crystal-analyser"]