.DEFAULT_GOAL := run

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

dist:
	./gradlew clean installDist

run-dist:
	./build/install/app/bin/app -h

run:
	./gradlew run --args="-h"

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest


.PHONY: build