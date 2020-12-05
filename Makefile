.PHONY: clean
clean:
	./gradlew clean

.PHONY: package-analyser
package-analyser:
	./gradlew -Panalyser.version=${ANALYSER_VERSION} :crystal-analyser:assembleShadowDist

.PHONY: build-analyser-image
build-analyser-image: clean package-analyser
	docker build --tag crystal-analyser:${ANALYSER_VERSION} --build-arg binaryName="crystal-analyser-${ANALYSER_VERSION}-all.jar" crystal-analyser/
