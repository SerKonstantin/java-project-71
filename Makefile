.DEFAULT_GOAL := dist

clean:
	make -C app clean

build:
	make -C app build

install:
	make -C app install

dist:
	make -C app dist

run-dist:
	make -C app run-dist

run:
	make -C app run

test:
	make -C app test

report:
	make -C app report

lint:
	make -C app lint


.PHONY: build
