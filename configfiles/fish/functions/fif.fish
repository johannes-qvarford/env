function fif --description 'finds all files in the current directory that matches grep pattern'
    find . -type f | xargs grep $argv
end
