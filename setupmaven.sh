#!/bin/bash

# Default Maven download URL

# Default Maven home parent directory
MAVEN_PARENT_DIR="$HOME/maven"

# Help message
show_help() {
    echo "Usage: $0 [--help] [maven_home=custom_maven_home_directory]"
    echo "  --help                  Display this help message and exit."
    echo "  maven_home=directory    Specify a custom directory for MAVEN_HOME (default is $HOME/maven)."
}

# Parse command line arguments
for arg in "$@"
do
    case $arg in
        --help)
        show_help
        exit 0
        ;;
        maven_home=*)
        MAVEN_PARENT_DIR="${arg#*=}"
        shift # Remove argument from processing
        ;;
    esac
done


# Navigate to the target directory
pushd .
cd $MAVEN_PARENT_DIR

# Find the Maven directory name (it should start with 'apache-maven-')
mvn_dir=$(find . -maxdepth 1 -type d -name "apache-maven-*" -printf "%f\n")

# Full path to the Maven directory
MAVEN_HOME="$MAVEN_PARENT_DIR/$mvn_dir"

# Setup M2_HOME and PATH
echo "Setting up M2_HOME to $MAVEN_HOME"
export M2_HOME=$MAVEN_HOME
export PATH=$M2_HOME/bin:$PATH

# Print M2_HOME and PATH
echo "M2_HOME is set to $M2_HOME"
echo "PATH is set to $PATH"

# Optionally, you can add the M2_HOME and PATH setup to your shell profile (e.g., ~/.bashrc or ~/.profile) to make these changes permanent.
# echo 'export M2_HOME='"$M2_HOME" >> ~/.bashrc
# echo 'export PATH=$M2_HOME/bin:$PATH' >> ~/.bashrc
# source ~/.bashrc

popd

# Print current working directory
pwd

