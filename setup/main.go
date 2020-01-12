package main

import (
	"flag"
	"fmt"
	"os"
	"setup/core"
	"setup/fedora"
	"setup/ubuntu"
)

type flags struct {
	repoDir     *string
	installDeps *bool
	initVim     *bool
	linkFiles   *bool
	changeShell *bool
	os          *string
}

func main() {
	flags := flags{
		repoDir:     flag.String("repository-directory", os.Getenv("HOME")+"/env", "directory root of the git repository."),
		installDeps: flag.Bool("install-dependencies", false, "whether or not dependencies should be installed or not"),
		initVim:     flag.Bool("initialize-vim", false, "whether or not vim should be setup or not."),
		linkFiles:   flag.Bool("link-files", false, "whether or not to link home and repository files."),
		changeShell: flag.Bool("change-shell", false, "whether or not to change the shell."),
		os:          flag.String("os", "Fedora", "Operating system to install on."),
	}

	flag.Parse()

	var activeOS core.OS
	activeOS = fedora.Fedora{}
	if *flags.os == "Ubuntu" {
		activeOS = ubuntu.Ubuntu{}
	}

	if *flags.installDeps {
		fmt.Printf("Installing dependencies...\n")
		err := activeOS.InstallDeps()
		if err != nil {
			fmt.Fprintln(os.Stderr, err)
			os.Exit(1)
		}
	}

	if *flags.initVim {
		fmt.Printf("Initializing Vim...\n")
		err := activeOS.InitVim()
		if err != nil {
			fmt.Fprintln(os.Stderr, err)
			os.Exit(1)
		}
	}

	if *flags.linkFiles {
		fmt.Printf("Linking dotfiles...\n")
		err := activeOS.LinkFiles(*flags.repoDir)
		if err != nil {
			fmt.Fprintln(os.Stderr, err)
			os.Exit(1)
		}
	}

	if *flags.changeShell {
		fmt.Printf("Changing shell...\n")
		err := activeOS.ChangeShell()
		if err != nil {
			fmt.Fprintln(os.Stderr, err)
		}
	}
}
