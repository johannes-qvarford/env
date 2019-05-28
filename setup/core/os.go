package core

type OS interface {
	InstallDeps() error
	InitVim() error
	LinkFiles(repoDir string) error
	ChangeShell() error
}
