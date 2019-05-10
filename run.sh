#!/bin/bash

OS=$(cat /etc/os-release | grep '^NAME' | sed -E 's/(.*)=(.*)/\2/')

if [[ "$OS" == "Fedora" ]]; then
    sudo dnf install golang
fi

go run ./setup -install-dependencies -initialize-vim -link-files -change-shell
