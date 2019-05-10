set color_repo_type blue
set color_repo_paren purple
set color_repo_branch cyan
set color_repo_dirty yellow

set -x PYTHONIOENCODING utf-8
set -x FZF_DEFAULT_COMMAND 'fish -c "fzf_default_command"'
set -U FZF_LEGACY_KEYBINDINGS 0
set -x fzf_prune_dirs \
	Appdata \
	.hg \
	.git \
	.cache \
	Library
set -x EDITOR vim
set -x VISUAL vim
set -x PAGER less

set -gx PATH $HOME/bin /usr/local/bin /usr/bin /bin / $PATH 
set -gx fish_function_path $HOME/.config/fish/functions2 $fish_function_path
set -gx MAVEN_OPTS '-Dmaven.wagon.http.ssl.insecure=true'

remove_duplicates_in_array PATH