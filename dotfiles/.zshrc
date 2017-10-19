ZSH=~/private/oh-my-zsh
source $ZSH/oh-my-zsh.sh
source $ZSHDIR/fzf.zsh
source $ZSHDIR/prompt.zsh

setopt correct
setopt share_history
setopt hist_find_no_dups
setopt extended_glob

alias tmux='tmux -2' #tmux 256 colors
alias la='ls -Al'
alias ll='ls -l'
alias cd..='cd ..'
alias -g ...='cd ../..'
alias -g ....='cd ../../..'
alias -g DN='/dev/null'
alias -g NE='2> /dev/null'
alias -g NO='2> /dev/null 1>&2'
alias -g G='| grep -E'
alias -g L='| less'

alias -s cs=vim
alias -s vim=vim
alias -s c=vim
alias -s cpp=vim
alias -s cc=vim
alias -s txt=vim

bindkey -v
set -o vi
bindkey -M viins jk vi-cmd-mode

#remove stupid dump files created when zsh creates a zsh process
ls -A "$HOME" |
	grep 'zcompdump' |
	while read dumpfile; do
		rm "$HOME/$dumpfile"
	done

export PATH="$PATH:$HOME/.rvm/bin" # Add RVM to PATH for scripting
