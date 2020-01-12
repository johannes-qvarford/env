#!/bin/bash

OS=$(cat /etc/os-release | grep '^NAME' | sed -E 's/(.*)=(.*)/\2/' | tr -d '"')

if [[ "$OS" == "Fedora" ]]; then
    sudo dnf install golang
fi

if [[ "$OS" == "Ubuntu" ]]; then
    dir=$(mktemp -d)
    wget -O "$dir/go.tar.gz" https://dl.google.com/go/go1.12.5.linux-amd64.tar.gz
    sudo tar -C /usr/local -xzf "$dir/go.tar.gz"
    rm -rf "$dir"
    PATH="$PATH:/usr/local/go/bin"
fi

go run ./setup -install-dependencies -initialize-vim -link-files -change-shell -os $OS
