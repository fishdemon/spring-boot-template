package priv.maanjin.shirojwt.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 * 
 * @author Anjin.Ma
 *
 */
@Component
public class RedisLock {
	static final long ACQUIRE_TIME_OUT = 3000;
	static final long KEY_TIME_OUT = 2000;
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 加锁
	 * @param key
	 * @param value
	 * @return boolean
	 */
	public boolean lock(String key, String value) {
		// setIfAbsent相当于jedis中的setnx，如果能赋值就返回true，如果已经有值了，就返回false
		// 即：在判断这个key是不是第一次进入这个方法
		if (redisTemplate.opsForValue().setIfAbsent(key, value, KEY_TIME_OUT, TimeUnit.MILLISECONDS)) {
			// 第一次，即：这个key还没有被赋值的时候
			return true;
		}
		return false;
	}

	/**
	 * 解锁
	 * @param key
	 * @param value
	 * @return boolean
	 */
	public boolean unlock(String key, String value) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		DefaultRedisScript<List> redisScript = new DefaultRedisScript<List>();
        redisScript.setScriptText(script);
        redisScript.setResultType(List.class);
        
        Object res = redisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                value.getBytes()));
        Long result= (Long) res;

        if (result == 2) {
            return true;
        }
        return false;
	}

}
