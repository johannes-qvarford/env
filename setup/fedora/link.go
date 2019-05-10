package fedora

import (
	"fmt"
	"io/ioutil"
	"os"
)

// LinkFiles Link dotfiles in repo to home directory.
func LinkFiles(repoDir string) error {

	fmt.Println("Linking dotfiles.")
	err := linkFilesInDir(
		fmt.Sprintf("%s/%s", repoDir, "dotfiles"),
		fmt.Sprintf("%s", os.Getenv("HOME")))

	if err != nil {
		return err
	}

	fmt.Println("Linking configfiles.")
	err = linkFilesInDir(
		fmt.Sprintf("%s/%s", repoDir, "configfiles"),
		fmt.Sprintf("%s/.config", os.Getenv("HOME")))

	return err
}

func linkFilesInDir(dotFileDir, homeFileDir string) error {
	files, err := ioutil.ReadDir(dotFileDir)

	if err != nil {
		return err
	}

	for _, f := range files {
		homePath := fmt.Sprintf("%s/%s", homeFileDir, f.Name())
		dotFilePath := fmt.Sprintf("%s/%s", dotFileDir, f.Name())
		err = linkSwitchero(dotFilePath, homePath)
		if err != nil {
			return err
		}
	}

	return nil
}

func linkSwitchero(dotFilePath, homeFilePath string) error {

	fmt.Printf("Checking %s status.\n", homeFilePath)
	homeStat, err := os.Lstat(homeFilePath)

	// Only ok error is if the home file doesn't exist yet.
	if err != nil && !os.IsNotExist(err) {
		return err
	}

	fileExists := err == nil

	homeIsLink := fileExists && homeStat.Mode()&os.ModeSymlink != 0

	// Delete existing non-link.
	if fileExists && !homeIsLink {
		fmt.Printf("Removing %s.\n", homeFilePath)
		err = os.Remove(homeFilePath)
		if err != nil {
			return err
		}
	}

	// Create link if file didn't exist or wasn't a link before being deleted.
	if !fileExists || !homeIsLink {
		fmt.Printf("%s -> %s.\n", homeFilePath, dotFilePath)
		err = os.Symlink(dotFilePath, homeFilePath)
		if err != nil {
			return err
		}
	} else {
		fmt.Printf("%s was already a link.\n", homeFilePath)
	}

	return nil
}
