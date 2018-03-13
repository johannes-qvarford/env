#!/bin/bash
mvn exec:java -Dexec.mainClass=com.johannesqvarford.gitisdirty.App -Dlog4j.configuration=log4j.debug.properties
