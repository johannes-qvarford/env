package ubuntu

type Ubuntu struct{}

func (_ Ubuntu) InstallDeps() error {
	return InstallDeps()
}

func (_ Ubuntu) InitVim() error {
	return InitVim()
}

func (_ Ubuntu) LinkFiles(repoDir string) error {
	return LinkFiles(repoDir)
}

func (_ Ubuntu) ChangeShell() error {
	return ChangeShell()
}
