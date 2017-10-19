function fzf_dirs
	fzf_default_command | filter-dirs | fzf
end
