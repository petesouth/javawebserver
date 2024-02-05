#!/bin/bash

# Default JDK version
jdk_version="17"

# Default JDK download URL
JDK_URL="https://download.oracle.com/graalvm/${jdk_version}/latest/graalvm-jdk-${jdk_version}_linux-x64_bin.tar.gz"

# Help message
show_help() {
    echo "Usage: $0 [--help] [jdk_version=version_number] [url=custom_jdk_url]"
    echo "  --help                  Display this help message and exit."
    echo "  jdk_version=number      Specify a JDK version to download (default is 17)."
    echo "  url=custom_jdk_url      Specify a custom URL to download a different JDK tar.gz file."
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
        JDK_URL="https://download.oracle.com/graalvm/${jdk_version}/latest/graalvm-jdk-${jdk_version}_linux-x64_bin.tar.gz"
        shift # Remove argument from processing
        ;;
        url=*)
        JDK_URL="${arg#*=}"
        shift # Remove argument from processing
        ;;
    esac
done

# Create the target directory
mkdir -p ~/java/jdk${jdk_version}

# Download the JDK from Oracle
wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" \
     "$JDK_URL" \
     -O ~/java/jdk${jdk_version}/jdk.tar.gz

# Navigate to the target directory
cd ~/java/jdk${jdk_version}

# Extract the downloaded tar.gz file
tar -zxvf jdk.tar.gz

# Remove the tar.gz file after extraction
rm jdk.tar.gz

# Find the JDK directory name (it should start with 'graalvm-' or 'jdk-')
jdk_dir=$(find . -maxdepth 1 -type d -name "graalvm-*" -printf "%f\n" -o -name "jdk-*" -printf "%f\n")

# Echo the final directory
echo "JDK installed in: $PWD/$jdk_dir"

# Print the current working directory
pwd

