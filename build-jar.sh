#!/bin/bash

##
# This script is for building jar file, by doing the following:
# - Create proper MANIFEST.MF file
# - Clean previous compile/build
# - Compile the .java files
# - Create jar, show verbose output, specify jar name, and manifest file
##

MAIN_CLASS="Library"
PACKAGE_NAME="local.shendy"
JAR_NAME="library"

SOURCE_ROOT_PATH="src/main/java"
RESOURCES_ROOT_PATH="src/main/resources"
PACKAGE_PATH="$SOURCE_ROOT_PATH/$(echo $PACKAGE_NAME | tr . /)"

MANIFEST_FILENAME="MANIFEST.MF"
MANIFEST_FILE="$RESOURCES_ROOT_PATH/META-INF/$MANIFEST_FILENAME"
MANIFEST_VERSION="1.0"
MANIFEST_MAIN_CLASS=$PACKAGE_NAME.$MAIN_CLASS

# - Create proper MANIFEST.MF file
echo "Manifest-Version: $MANIFEST_VERSION" > $MANIFEST_FILE
echo "Class-Path: ." >> $MANIFEST_FILE
echo "Main-Class: $MANIFEST_MAIN_CLASS" >> $MANIFEST_FILE

# - Clean previous compile/build
rm -rf ./out/*


# - Compile the .java files
# - Create jar, show verbose output, specify jar name, and manifest file
javac -cp ./"$SOURCE_ROOT_PATH" ./"$PACKAGE_PATH"/*.java -d ./out/ \
  && jar cvfm "$JAR_NAME.jar" ./"$MANIFEST_FILE" -C ./out/ .
