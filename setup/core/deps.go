package core

import "fmt"

// PackageInstaller function for installing a package.
type PackageInstaller func(string) error

// InstallPackages use installer on all packages.
func InstallPackages(installer PackageInstaller, pkgs ...string) error {
	for _, e := range pkgs {
		fmt.Printf("Installing package %s.\n", e)
		err := installer(e)
		if err != nil {
			return err
		}
	}
	return nil
}
