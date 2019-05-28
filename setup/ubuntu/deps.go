package ubuntu

import (
	"os"
	"os/exec"
	"setup/core"
)

// InstallDeps Install dependencies for ubuntu.
func InstallDeps() error {
	return core.InstallPackages(installUbuntuPackage, "fish", "tmux", "vim")
}

func installUbuntuPackage(pkg string) error {
	cmd := exec.Command("sudo", "apt", "install", pkg)
	cmd.Stdin = os.Stdin
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	cmd.Start()
	err := cmd.Wait()
	return err
}
