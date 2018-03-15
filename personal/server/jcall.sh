#!/bin/bash

args=("$@")

for i in ${#args[@]}; do
    ${args[$i]} = ${args[$i]/--//};
done

curl "localhost:9000/$1?workingDirectory=$PWD&"