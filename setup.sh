#!/bin/bash

# List of child folder names
mapfile -t folders < <(find . -mindepth 2 -name "build.sh" -exec dirname {} \; | sort | uniq)

# Iterate over each folder and run the build.sh script if it exists
for folder in "${folders[@]}"; do
  if [ -f "$folder/build.sh" ]; then
    # Change to the directory
    cd "$folder" || continue
    
    # Run the build.sh script
    echo "Running build.sh in $folder"
    bash build.sh
    
    # Return to the initial directory
    cd - > /dev/null
  else
    echo "No build.sh found in $folder"
  fi
done
