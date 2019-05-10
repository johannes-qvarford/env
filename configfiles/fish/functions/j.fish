function j
    set -l port 9900
    set -l name "$argv[1]";
    set -l json "["

    set -l bargs $argv
    set -e bargs[1]
    set -l bargscount (count $bargs)

    set -l i 1
    while test $i -le $bargscount
        set -l arg $bargs[$i]
        set json $json""(echo $arg | jq . --raw-input)
        if test $i -ne $bargscount
            set json "$json,"
        end
        set i (math $i + 1)
    end
    set json "$json]"

    if not netstat -an | grep -q "$port.*LISTEN"
        set -l jar_path (find ~/bin/com/johannesqvarford/server | grep 'jar$' | sort | tail -n 1)
        nohup >/dev/null 2>&1 java -jar $jar_path com.johannesqvarford.server.App &
    end

    set -l response (curl 2>/dev/null localhost:$port/$name\?workingDirectory="$PWD" -X POST -H "Content-Type: application/json" -d "$json")
    set -l response '{"stdout": "", "stderr": "", "exitStatus": 0}'
    set -l stdout (string trim (echo $response | jq .stdout --raw-output))
    set -l stderr (string trim (echo $response | jq .stderr --raw-output))
    set -l exit_status (string trim (echo $response | jq .exitStatus --raw-output))

    if test "$stdout" != ""
        echo "$stdout" >&1
    end
    if test "$stderr" != ""
        echo "$stderr" >&2
    end
    if test "$exit_status" = ''
        set exit_status 1
    end
    
    return $exit_status
end