#!/usr/bin/env python3

"""
Script to setup the environment for me: Johannes Qvarford.
"""

from __future__ import print_function

import sys
import subprocess
import random
import os
import re
import platform

def sh_call(an_argument, silent=False):
    """ Calls the shell command. """
    out, err = sys.stdout, sys.stderr
    with open(os.devnull, 'w') as devnull:
        if silent:
            out, err = devnull, devnull
        return subprocess.call(an_argument, \
          shell=True, stdout=out, stderr=err) == 0

def git_clone(remote, local):
    """ Clone a remote github repo to a local directory. """
    isdir = os.path.isdir(local)
    if not isdir:
        sh_call("git clone https://github.com/{0} {1}".format(remote, local))
    return not isdir

def symlink(src, dest):
    """ Create a symbolic link file dest that points at src"""
    nothing_at_dest = not os.path.lexists(dest)
    is_dest_normal_file = not nothing_at_dest and not os.path.islink(dest)
    is_dest_link_file = not nothing_at_dest and os.path.islink(dest)
    is_correct_link_target = is_dest_link_file and os.readlink(dest) == src

    if nothing_at_dest:
        print("new {0} -> {1}".format(dest, src))
        os.symlink(src, dest)
    elif is_dest_normal_file or not is_correct_link_target:
        print("replace {0} -> {1}".format(dest, src))
        os.remove(dest)
        os.symlink(src, dest)

def decode_homefile(filename):
    """ Decode a home file by replacing the dunders with slashes """
    return re.sub(r"__", "/", filename)

def hg_clone(remote, local):
    """ Clone a remote bitbucket hg repo to a local directory."""
    isdir = os.path.isdir(local)
    if not isdir:
        sh_call("hg clone https://bitbucket.org/{0} {1}".format(remote, local))
    return not isdir

def assert_in_env_directory():
    """ Assert that setup is run in the env root directory,
    and exits if not """
    if not "setup" in os.listdir(os.getcwd()):
        print("Run this in env directory")
        sys.exit(1)

def symlink_to_dotdir_files():
    """ Create links to the files in the dotfiles directory,

    and place them in the home directory """
    home = os.environ["HOME"]
    dotdir = os.path.join(os.getcwd(), "dotfiles")
    dotfiles = [f for f in os.listdir(dotdir)
                if not f in [".hg", ".config", ".DS_Store"]]

    for filename in dotfiles:
        dotfile = os.path.join(dotdir, filename)
        homefile = os.path.join(home, filename)
        homefile = decode_homefile(homefile)
        symlink(dotfile, homefile)

    configdir = os.path.join(os.getcwd(), "dotfiles", ".config")
    configfiles = [f for f in os.listdir(configdir)]
    homeconfigdir = os.path.join(home, ".config")

    if not os.path.exists(homeconfigdir):
        os.mkdir(homeconfigdir)

    for filename in configfiles:
        dotfile = os.path.join(configdir, filename)
        homefile = os.path.join(homeconfigdir, filename)
        homefile = decode_homefile(homefile)
        symlink(dotfile, homefile)

def symlink_bin_files():
    """ Symlink the files in the bin subdirectory to $HOME/bin """
    symbindir = os.path.join(os.environ["HOME"], "bin")
    create_dir(symbindir)
    bindir = os.path.join(os.getcwd(), "bin")
    binfiles = [f for f in os.listdir(bindir) if not f in [".hg", ".DS_Store"]]
    for binary in binfiles:
        symfile = os.path.join(symbindir, binary)
        bfile = os.path.join(bindir, binary)
        symlink(bfile, symfile)
        
def symlink_between_env_and_home_directories(envpathsegs, homepathsegs, ignore):
    envwholesegs = [os.getcwd()] + envpathsegs
    physdir = os.path.join(*envwholesegs)
    homewholesegs = [os.environ["HOME"]] + homepathsegs
    symdir = os.path.join(*homewholesegs)
    create_dir(symdir)
    files = [f for f in os.listdir(physdir) if not f in [".hg", ".DS_Store"] + ignore]
    for file in files:
        symfile = os.path.join(symdir, file)
        physfile = os.path.join(physdir, file)
        symlink(src=physfile, dest=symfile)

def super_symlink(syms):
    for s in syms:
        symlink_between_env_and_home_directories(envpathsegs=s["env"], homepathsegs=s["home"], ignore=s["subdirs"])
        for sub in s["subdirs"]:
            env = s["env"] + [sub]
            home = s["home"] + [sub]
            symlink_between_env_and_home_directories(envpathsegs=env, homepathsegs=home, ignore=[])
            
def symlink_vscode_files():
    """ Symlink the files in the vscode subdirectory to $HOME/Library/Application Support/Code/User/ """
    print("symlinking vscode files")
    syms = [
        {"env": ["vscode"], "home": ["Library", "Application Support", "Code", "User"], "subdirs": ["snippets"]}
    ]
    super_symlink(syms)

def install_fish():
    """ Install the fish shell """
    sh_call("""brew install fish""")
    sh_call("""echo /usr/local/bin/fish | sudo tee -a /etc/shells""")
    sh_call("""chsh -s /usr/local/bin/fish""")

def install_vim():
    """ Install vim plugins """
    # Create .vim if it doesn't exist already.
    home = os.environ["HOME"]
    vimdir = os.path.join(home, ".vim")
    if not os.path.isdir(vimdir):
        os.mkdir(vimdir)

    # Install vundle and all plugins in the vimrc
    git_clone("VundleVim/Vundle.vim", \
      "{0}/.vim/bundle/Vundle.vim".format(os.environ["HOME"]))
    # All plugins are not available on the first run, so we have to run it again.
    sh_call("""vim +':execute ":PluginInstall" | q | q'""")
    sh_call("""vim +':execute ":PluginInstall" | q | q'""")
    sh_call("""brew install dos2unix""")
    sh_call("""find ~/.vim -type f -print0 | xargs -0 dos2unix""")

def create_dir(dirname):
    """ Create a directory and all its parent directories
        if they don't exist. """
    subprocess.call("mkdir -p " + dirname, shell=True)

def install_fzf():
    """ Install fzf in gen subdirectory, and symlink binary from home/bin """
    create_dir("gen")
    git_clone("junegunn/fzf", "gen/fzf")
    gen_fzf = os.path.join(os.getcwd(), "gen/fzf/fzf")

    home = os.environ["HOME"]
    home_bin = os.path.join(home, "bin")
    home_bin_fzf = os.path.join(home_bin, "fzf")

    create_dir(home_bin)
    symlink(gen_fzf, home_bin_fzf)



if __name__ == "__main__":
    assert_in_env_directory()
    symlink_vscode_files()
    install_fish()
    install_vim()
    symlink_to_dotdir_files()
    symlink_bin_files()
    install_fzf()
    sh_call("""defaults write com.apple.finder AppleShowAllFiles YES""")
#symlink_you_complete_me()
