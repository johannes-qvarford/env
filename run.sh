#!/bin/bash

if [[ "$(uname)" == "Darwin" ]]; then
    brew install maven
    #brew cask install java
fi

mvn exec:java -f personal/setup -Dexec.mainClass=com.johannesqvarford.setup.App -Dexec.args="-r $PWD $*"
