filetype plugin indent on

set rtp+=$HOME/.vim/bundle/Vundle.vim
set rtp+=/usr/local/opt/fzf

call vundle#begin()
Plugin 'VundleVim/Vundle.vim'     
Plugin 'flazz/vim-colorschemes'
Plugin 'ScrollColors'
Plugin 'Syntastic'
Plugin 'christoomey/vim-tmux-navigator'
Plugin 'SirVer/ultisnips'
Plugin 'honza/vim-snippets'
Plugin 'tpope/vim-fugitive'
"Plugin 'bling/vim-airline'
Plugin 'lervag/vimtex'
Plugin 'JalaiAmitahl/maven-compiler.vim'
call vundle#end()
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
let g:UltiSnipsExpandTrigger = "<nop>"
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
