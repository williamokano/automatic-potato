path=$(shell pwd)

run:
	./mvnw spring-boot:run

start:
	docker-compose up -d

stop:
	docker-compose down

install: | start

restart: | stop start
