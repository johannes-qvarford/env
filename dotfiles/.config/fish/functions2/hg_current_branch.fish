function hg_current_branch
	cat (hg_dir)/.hg/bookmarks.current ^/dev/null
end