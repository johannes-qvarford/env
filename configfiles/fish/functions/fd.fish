function fd --description 'goto directory found with fzf'
	find -type d | fzf | read dir
cd $dir
end
