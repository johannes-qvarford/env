(require 'package)
(add-to-list 'load-path "~/.emacs.d/elpa/evil-20150219.53")
(push '("marmalade" . "http://marmalade-repo.org/packages/")
  package-archives)
(push '("melpa" . "http://melpa.milkbox.net/packages/")
  package-archives)
(package-initialize)

(defvar my-packages '(clojure-mode cider multiple-cursors rainbow-delimiters hipster-theme projectile))

(dolist (p my-packages)
  (unless (package-installed-p p)
    (package-install p)))


(require 'multiple-cursors)

(defmacro interactive-insert-char (character)
  `(lambda ()
    (interactive) (insert-char ,character)))
(defmacro bind (bind-string symbol-or-function)
  `(global-set-key (kbd ,bind-string) ,symbol-or-function))

(bind "C-x C-j" 'mc/edit-lines)

(bind "C-j" 'next-line)
(bind "C-k" 'previous-line)
(bind "C-h" 'backward-char)
(bind "C-l" 'forward-char)
(bind "C-S-h" 'backward-word)
(bind "C-S-l" 'forward-word)
(bind "C-p" 'help)
(bind "C-x C-e" 'eval-buffer)
(bind "C-x C-n" 'other-window)
(bind "C-x C-u" 'undo)

(bind "C-x C-d" 'kill-whole-line)
(bind "C-x C-w" 'copy-region-as-kill)
(bind "C-8" (interactive-insert-char #x5b))
(bind "C-9" (interactive-insert-char #x5d))
(bind "C-(" (interactive-insert-char #x7b))
(bind "C-)" (interactive-insert-char #x7d))

(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(custom-enabled-themes (quote (hipster)))
 '(custom-safe-themes (quote ("1c50040ec3b3480b1fec3a0e912cac1eb011c27dd16d087d61e72054685de345" default))))
(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 )
