function fzf_default_command
	set -l dir (fzf_find_prune_string)
	eval find . $dir -prune -o -not $dir -print
end
