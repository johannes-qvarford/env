export DOTDIR=~/private/dotfiles
export ZSHDIR=~/private/zsh
source $ZSHDIR/functions.zsh

typeset -U path
path=(~/private/bin ~/bin /usr/local/bin /usr/bin $path)
is_mac && path=(/Applications/MacVim.app/Contents/MacOS $path)
export PATH

pick PAGER less more
pick EDITOR vim vi emacs nano
pick VISUAL vim vi

export LESS='-R --ignore-case --hilite-search --LONG-PROMPT --quiet --squeeze-blank-lines --HILITE-UNREAD'
export CLICOLOR=""
export INCLUDE_DIRS="/usr/include:/usr/local/include"
export BIN_DIRS="/usr/bin:/bin:/sbin:/usr/sbin:/usr/local/bin:$HOME/bin"
export LIB_DIRS="/lib:/usr/lib:/usr/local/lib"
export LD_LIBRARY_PATH="/usr/local/lib"
export ANDROID_HOME="/usr/local/opt/android-sdk"
export CDPATH=".:$HOME"
is_mac && CDPATH="$CDPATH:$HOME/Projects/TrashMania:$HOME/Projects/TrashMania/Assets/Resources"
export SSH_KEY_PATH="~/.ssh/dsa_id"
