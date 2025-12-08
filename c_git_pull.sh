#!/bin/bash

# Get current date and time
CURRENT_TIME=$(date "+%Y-%m-%d %H:%M:%S")

git fetch

# Push to master branch
git pull origin master

echo "✅ Code pull form 'master' branch at $CURRENT_TIME"
