package fedora

type Fedora struct{}

func (_ Fedora) InstallDeps() error {
	return InstallDeps()
}

func (_ Fedora) InitVim() error {
	return InitVim()
}

func (_ Fedora) LinkFiles(repoDir string) error {
	return LinkFiles(repoDir)
}

func (_ Fedora) ChangeShell() error {
	return ChangeShell()
}
