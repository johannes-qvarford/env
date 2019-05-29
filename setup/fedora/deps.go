package fedora

import (
	"fmt"
	"os"
	"os/exec"
	"setup/core"
	"strings"
)

// InstallDeps Install dependencies for fedora.
func InstallDeps() error {
	overwriteFileAsAdministrator("/etc/yum.repos.d/vscode.repo", vscodeRepo)
	return core.InstallPackages(installFedoraPackage, "code", "fish", "tmux", "vim-enhanced", "util-linux-user", "maven")
}

func overwriteFileAsAdministrator(path string, content string) error {
	cmd := exec.Command("sudo", "dd", "of="+path)

	cmd.Stdin = strings.NewReader(content)
	cmd.Stdout = nil
	cmd.Stderr = nil

	fmt.Println("Writing yum repo for Fedora.")
	cmd.Start()
	err := cmd.Wait()
	if err != nil {
		return err
	}
	return nil
}

func installFedoraPackage(pkg string) error {
	cmd := exec.Command("sudo", "dnf", "install", pkg)
	cmd.Stdin = os.Stdin
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	cmd.Start()
	err := cmd.Wait()
	return err
}

var vscodeRepo = `[vscode]
name=Visual Studio Code
baseurl=https://packages.microsoft.com/yumrepos/vscode
enabled=1
gpgcheck=1
gpgkey=https://packages.microsoft.com/keys/microsoft.asc`
