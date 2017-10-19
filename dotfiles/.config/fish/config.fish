set color_repo_type blue
set color_repo_paren purple
set color_repo_branch cyan
set color_repo_dirty yellow

set -x PYTHONIOENCODING utf-8
set -x FZF_DEFAULT_COMMAND 'fish -c "fzf_default_command"'
set -x fzf_prune_dirs \
	Appdata \
	.hg \
	.git \
	.cache \
	Library
set -x EDITOR vim
set -x VISUAL vim
set -x PAGER less

if test -d /Applications/MacVim.app/Contents/MacOS
	set -gx	PATH /Applications/MacVim.app/Contents/MacOS $PATH
end

if test -d /cygdrive/c/Windows/system32
	set -gx PATH /cygdrive/c/Windows/system32 $PATH
    set -l java_home (cygpath -u $JAVA_HOME)
    set -gx PATH $java_home/bin $PATH
    if set -q ANDROID_HOME
        set -l android_home (cygpath -u $ANDROID_HOME)
        #set -l build_subdir (ls $android_home/build-tools | sort -r | head -n 1)
        set -l build_subdir 23.0.1
        set -gx PATH $android_home/tools $android_home/platform-tools $android_home/build-tools/$build_subdir $PATH
    end
end

if test (uname -o ^/dev/null) = "Cygwin"
	set -gx CYGWIN "winsymlinks:nativestrict"
end

set -gx PATH /usr/local/idea/bin $HOME/bin /usr/local/bin /usr/bin /bin ~/Appdata/Local/Android/tools /cygdrive/c/apache-maven/bin $HOME/.local/bin "/mnt/c/Program Files/Docker/Docker/resources/bin" ~/google-cloud-sdk/bin $PATH 
set -gx fish_function_path $HOME/.config/fish/functions2 $fish_function_path

remove_duplicates_in_array PATH
