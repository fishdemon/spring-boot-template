package priv.maanjin.shirojwt.config.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import priv.maanjin.shirojwt.constant.ApplicationConstant;
import priv.maanjin.shirojwt.util.JwtUtil;
import priv.maanjin.shirojwt.util.RedisClient;
	
public class ShiroCache<K, V> implements Cache<K, V> {
	
	@Value("${shiroCacheExpireTime}")
	private String shiroCacheExpireTime;

	@Autowired
	private RedisClient redis;

	/**
	 * 缓存的key名称获取为shiro:cache:account
	 * 
	 * @param key
	 * @return java.lang.String
	 * @date 2018/9/4 18:33
	 */
	private String getKey(Object key) {
		return ApplicationConstant.PREFIX_SHIRO_CACHE + JwtUtil.getUsername(key.toString()) + ApplicationConstant.JWT_ACCOUNT;
	}

	/**
	 * 获取缓存
	 */
	@Override
	public Object get(Object key) throws CacheException {
		if (!redis.hasKey(this.getKey(key))) {
			return null;
		}
		return redis.get(this.getKey(key));
	}

	/**
	 * 保存缓存
	 */
	@Override
	public Object put(Object key, Object value) throws CacheException {
		// 读取配置文件，获取Redis的Shiro缓存过期时间
		// PropertiesUtil.readProperties("config.properties");
		// String shiroCacheExpireTime =
		// PropertiesUtil.getProperty("shiroCacheExpireTime");
		// 设置Redis的Shiro缓存
		return redis.set(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
	}

	/**
	 * 移除缓存
	 */
	@Override
	public Object remove(Object key) throws CacheException {
		if (!redis.hasKey(this.getKey(key))) {
			return null;
		}
		redis.del(this.getKey(key));
		return null;
	}

	/**
	 * 清空所有缓存
	 */
	@Override
	public void clear() throws CacheException {

	}

	/**
	 * 缓存的个数
	 */
	@Override
	public Set<K> keys() {
		return null;
	}

	/**
	 * 获取所有的key
	 */
	@Override
	public int size() {
		return 0;
	}

	/**
	 * 获取所有的value
	 */
	@Override
	public Collection<V> values() {
		return null;
	}

}
