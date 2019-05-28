package ubuntu

import (
	"fmt"
	"os"

	"gopkg.in/src-d/go-git.v4"
)

// InitVim Initialize Vim configuration.
func InitVim() error {
	installDir := os.Getenv("HOME") + "/.vim/dein/repos/github.com/Shougo/dein.vim"

	fmt.Printf("Creating %s directory.\n", installDir)
	err := os.MkdirAll(installDir, 0755)
	if err != nil {
		return err
	}

	fmt.Printf("Cloning Dein.\n")

	_, err = os.Stat(installDir + "/README.md")
	if err == nil {
		fmt.Printf("Dein has already been downloaded.\n")
		return nil
	} else if err != nil && !os.IsNotExist(err) {
		return err
	}

	// The README.md file did not exist if we reach this statement.
	_, err = git.PlainClone(installDir, false, &git.CloneOptions{
		URL:      "https://github.com/Shougo/dein.vim",
		Progress: os.Stdout,
	})
	if err != nil {
		return err
	}
	return nil
}
