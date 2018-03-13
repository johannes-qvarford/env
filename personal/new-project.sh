#!/bin/bash
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DgroupId=com.johannesqvarford -DartifactId=$1 -Dversion=1.0-SNAPSHOT -Dpackage=com.johannesqvarford.${1//-/}
