#!/bin/bash

if [[ "$(uname)" == "Darwin" ]]; then
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    brew install maven
    #brew cask install java
fi

mvn exec:java -f personal -pl setup -am -Dexec.mainClass=com.johannesqvarford.setup.App -Dexec.args="-r $PWD $*"
