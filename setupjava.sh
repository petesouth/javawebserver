#!/bin/bash

# Default JDK version
jdk_version="17"

# Default installation directory
install_dir="$HOME/java/jdk${jdk_version}"

# Help message
show_help() {
    echo "Usage: $0 [--help] [jdk_version=version_number]"
    echo "  --help                  Display this help message and exit."
    echo "  jdk_version=number      Specify the JDK version to setup the environment for (default is 17)."
}

# Parse command line arguments
for arg in "$@"
do
    case $arg in
        --help)
        show_help
        exit 0
        ;;
        jdk_version=*)
        jdk_version="${arg#*=}"
        install_dir="$HOME/java/jdk${jdk_version}"
        shift # Remove argument from processing
        ;;
    esac
done

# Verify if JDK installation directory exists
if [[ ! -d "$install_dir" ]]; then
    echo "JDK installation directory does not exist: $install_dir"
    echo "Please check your JDK installation."
    exit 1
fi

# Find the JDK directory inside the installation directory (it should start with 'graalvm-' or 'jdk-')
jdk_home=$(find $install_dir -maxdepth 1 -type d -name "graalvm-*" -o -name "jdk-*" | head -n 1)

if [[ ! -d "$jdk_home" ]]; then
    echo "JDK home directory does not exist inside: $install_dir"
    echo "Please check your JDK installation."
    exit 1
fi

# Setup JAVA_HOME
echo "Setting up JAVA_HOME to $jdk_home"
export JAVA_HOME=$jdk_home

# Update PATH
echo "Updating PATH to include JAVA_HOME/bin"
export PATH=$JAVA_HOME/bin:$PATH

# Print JAVA_HOME and PATH
echo "JAVA_HOME is set to $JAVA_HOME"
echo "PATH is set to $PATH"

# Optionally, you can add the JAVA_HOME and PATH setup to your shell profile (e.g., ~/.bashrc or ~/.profile) to make these changes permanent.
# echo 'export JAVA_HOME='"$JAVA_HOME" >> ~/.bashrc
# echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
# source ~/.bashrc

# Print current working directory
pwd

