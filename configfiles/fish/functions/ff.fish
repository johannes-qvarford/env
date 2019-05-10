function ff --description 'find files i current directory that matches pattern'
	find . -type f | grep $argv
end
