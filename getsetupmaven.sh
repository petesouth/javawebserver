#!/bin/bash

# Default Maven download URL
MVN_TAR_URL="https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz"

# Default Maven home directory
MAVEN_HOME="$HOME/maven"

# Help message
show_help() {
    echo "Usage: $0 [--help] [mvn_tar_url=custom_maven_tar_url] [maven_home=custom_maven_home_directory]"
    echo "  --help                  Display this help message and exit."
    echo "  mvn_tar_url=url         Specify a custom URL to download a different Maven tar.gz file."
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
        mvn_tar_url=*)
        MVN_TAR_URL="${arg#*=}"
        shift # Remove argument from processing
        ;;
        maven_home=*)
        MAVEN_HOME="${arg#*=}"
        shift # Remove argument from processing
        ;;
    esac
done

# Create the target directory
mkdir -p $MAVEN_HOME

# Download Maven from Apache
wget -c "$MVN_TAR_URL" -O $MAVEN_HOME/maven.tar.gz

# Navigate to the target directory
cd $MAVEN_HOME

# Extract the downloaded tar.gz file
tar -zxvf maven.tar.gz

# Remove the tar.gz file after extraction
rm maven.tar.gz

# Find the Maven directory name (it should start with 'apache-maven-')
mvn_dir=$(find . -maxdepth 1 -type d -name "apache-maven-*" -printf "%f\n")

# Setup M2_HOME and PATH
echo "Setting up M2_HOME to $MAVEN_HOME/$mvn_dir"
export M2_HOME=$MAVEN_HOME/$mvn_dir
export PATH=$M2_HOME/bin:$PATH

# Print M2_HOME and PATH
echo "M2_HOME is set to $M2_HOME"
echo "PATH is set to $PATH"

# Optionally, you can add the M2_HOME and PATH setup to your shell profile (e.g., ~/.bashrc or ~/.profile) to make these changes permanent.
# echo 'export M2_HOME='"$M2_HOME" >> ~/.bashrc
# echo 'export PATH=$M2_HOME/bin:$PATH' >> ~/.bashrc
# source ~/.bashrc

# Print current working directory
pwd

