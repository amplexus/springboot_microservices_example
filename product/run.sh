#!/bin/bash

# H2 console will be accessible via localhost:8080/console

java -Dspring.config.location=src/main/resources/application.properties -jar build/libs/product.jar
