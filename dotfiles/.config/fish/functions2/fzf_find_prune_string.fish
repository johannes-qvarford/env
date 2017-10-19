function fzf_find_prune_string
	printf "%s %s " '\(' -false
	for d in $fzf_prune_dirs
		printf " %s %s " -o -name $d
	end
	printf "%s" '\)'
end
