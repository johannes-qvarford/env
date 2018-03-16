set nocompatible
set runtimepath+=~/.vim/dein/repos/github.com/Shougo/dein.vim " path to dein.vim
set runtimepath+=/usr/local/opt/fzf

call dein#begin(expand('~/.vim/dein'))
call dein#add('Shougo/dein.vim')
call dein#add('Shougo/vimproc.vim', {
    \ 'build': {
    \     'windows': 'tools\\update-dll-mingw',
    \     'cygwin': 'make -f make_cygwin.mak',
    \     'mac': 'make -f make_mac.mak',
    \     'linux': 'make',
    \     'unix': 'gmake',
    \    },
    \ })
call dein#add('Shougo/denite.nvim')
call dein#add('flazz/vim-colorschemes')
call dein#add('vim-scripts/ScrollColors')
call dein#add('vim-scripts/Syntastic',
    \{'on_ft': ['c', 'python', 'c++']})
call dein#add('christoomey/vim-tmux-navigator')
call dein#add('SirVer/ultisnips')
call dein#add('honza/vim-snippets')
call dein#add('tpope/vim-fugitive')
call dein#add('bling/vim-airline')
call dein#add('lervag/vimtex')
call dein#add('JalaiAmitahl/maven-compiler.vim',
    \{'on_ft': ['java']})
call dein#end()

source ~/.basicvimrc

function! AirlineInit()
    let g:airline_section_x = '%{getcwd()}'
    let g:airline#extensions#tabline#enabled = 1
    let g:airline#extensions#tabline#left_sep = '>'
    set laststatus=2
endfunction
"autocmd VimEnter * call AirlineInit()

let g:syntastic_quiet_messages = { "type": "style" }
let g:ycm_global_ycm_extra_conf = $HOME . ".vim/you_complete_me.py"
let g:ycm_confirm_extra_conf = 0
let g:UltiSnipsExpandTrigger = "<tab>"
let g:UltiSnipsJumpForwardTrigger="<c-k>"
let g:UltiSnipsJumpBackwardTrigger="<c-j>"

let g:ulti_expand_or_jump_res = 0

noremap <leader>ff :FZF<cr>
noremap <leader>fh :FZF ~<cr>
inoremap <expr> <CR> pumvisible() ? "<C-R>=ExpandSnippetOrCarriageReturn()<CR>" : "\<CR>"

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

set wildignore=*.so,*.swp,*.dll,*.wav,*.png,*.meta,AppData,.android
set shell=/bin/bash
