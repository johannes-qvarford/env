set nocompatible
set runtimepath+=~/.vim/dein/repos/github.com/Shougo/dein.vim " path to dein.vim

if dein#load_state(expand('~/.vim/dein'))
    call dein#begin(expand('~/.vim/dein'))
    call dein#add('Shougo/dein.vim')
    call dein#add('Shougo/denite.nvim')
    call dein#add('flazz/vim-colorschemes')
    call dein#add('vim-scripts/ScrollColors')
    call dein#add('vim-scripts/Syntastic',
        \{'on_ft': ['c', 'python']})
    call dein#add('christoomey/vim-tmux-navigator')
    call dein#add('honza/vim-snippets')
    call dein#add('SirVer/ultisnips')
    call dein#add('tpope/vim-fugitive')
    call dein#add('bling/vim-airline')
    call dein#add('fatih/vim-go')
    call dein#end()
    call dein#save_state()
endif

" Required:
filetype plugin indent on
syntax enable

if dein#check_install()
    call dein#install()
endif

source ~/.basicvimrc

function! AirlineInit()
    let g:airline_section_x = '%{getcwd()}'
    let g:airline#extensions#tabline#enabled = 1
    let g:airline#extensions#tabline#left_sep = '>'
    set laststatus=2
endfunction
autocmd VimEnter * call AirlineInit()

let g:syntastic_quiet_messages = { "type": "style" }
let g:UltiSnipsExpandTrigger = "<tab>"
let g:UltiSnipsJumpForwardTrigger="<c-k>"
let g:UltiSnipsJumpBackwardTrigger="<c-j>"

let g:ulti_expand_or_jump_res = 0

if !exists("*ExpandSnippetOrCarriageReturn")
    function ExpandSnippetOrCarriageReturn()
        let snippet = UltiSnips#ExpandSnippetOrJump()
        if g:ulti_expand_or_jump_res > 0
            return snippet
        else
            return "\<CR>"
        endif
    endfunction
endif

set wildignore=*.so,*.swp
set shell=/usr/bin/fish
