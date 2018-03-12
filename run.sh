#!/bin/bash
mvn exec:java -f personal/setup -Dexec.mainClass=com.johannesqvarford.setup.App -Dexec.args="-r $PWD $*"
