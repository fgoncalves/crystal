FROM adoptopenjdk/openjdk11

ARG script="crystal"
ARG binaryName="crystal-0.1-all.jar"
ARG buildDir="build"
ENV SCRIPT="${script}"

COPY "${buildDir}/libs/${binaryName}" "libs/${binaryName}"
COPY "${buildDir}/scriptsShadow/${script}" .

ENTRYPOINT ["./crystal"]