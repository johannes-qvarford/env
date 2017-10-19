function fbower
    mv ~/.config ~/.config.bk
    mv ~/.bower-config ~/.config
    bower $args
    mv ~.config ~/.bower-config
    mv ~/.config.bk ~/.config
end
