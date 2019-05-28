package ubuntu

import (
	"os"
	"os/exec"
)

// ChangeShell Change the default shell
func ChangeShell() error {
	cmd := exec.Command("chsh", "-s", "/usr/bin/fish")

	cmd.Stdin = os.Stdin
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr

	err := cmd.Start()
	if err != nil {
		return err
	}

	err = cmd.Wait()
	if err != nil {
		return err
	}

	return err
}
