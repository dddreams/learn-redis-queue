if redis.call("exists", KEYS[1]) == 1 then
    if tonumber(redis.call("get", KEYS[1])) < tonumber(ARGV[1]) then
        redis.call("set", KEYS[1], ARGV[1])
        return 1
    else
        return 0
    end
else
    redis.call("set", KEYS[1], ARGV[1])
    return 2
end

