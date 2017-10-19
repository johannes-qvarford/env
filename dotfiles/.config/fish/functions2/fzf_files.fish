function fzf_files
#	fzf_default_command | while read path
#		if test -f $path
#			echo $path
#		end
#	end | fzf
#	fzf_default_command | ruby -n -e 'if File.file? $_.strip; puts $_; end' | fzf
	fzf_default_command | filter_files | fzf
end
