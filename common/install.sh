#!/bin/bash

libs=(
    "./libs/command_bus"
    "./libs/string-enum"
    "./libs/misc"
    "./libs/domain_event"
    "./libs/page"
    "./libs/service-token"
    "./clients/passport-client"
    "./libs/jwt-auth"
    "./contracts/authorizator-contracts"
)
echo "Installing libs"
for dir in "${libs[@]}"; do
  if [ -d "$dir" ]; then
    echo "Processing directory: $dir"
    cd "$dir" || exit
    if [ -f "pom.xml" ]; then
      echo "Running mvn install in $dir"
      mvn install
    else
      echo "No pom.xml found in $dir, skipping..."
    fi
    cd - || exit
  else
    echo "Directory $dir does not exist, skipping..."
  fi
done
