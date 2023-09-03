.DEFAULT_GOAL := install-run-dist

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

dist:
	./gradlew clean installDist

run-dist:
	./build/install/app/bin/app

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest


install-run-dist: install run-dist

.PHONY: build