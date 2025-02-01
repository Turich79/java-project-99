.DEFAULT_GOAL := build
build:
	app/gradlew clean build
run-dist:
	app/build/install/app/bin/app
report:
	app/gradlew jacocoTestReport
test:
	app/gradlew test
lint:
	app/gradlew checkstyleMain checkstyleTest
.PHONY: build