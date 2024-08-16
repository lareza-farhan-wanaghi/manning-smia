#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# Clean and package the Maven project
./mvnw clean package -DskipTests

# Build the Docker image using the Docker Maven plugin
./mvnw docker:build
