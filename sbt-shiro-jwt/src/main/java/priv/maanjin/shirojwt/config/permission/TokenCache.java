package priv.maanjin.shirojwt.config.permission;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCache {
	
	private static Map<String, String> caches = new ConcurrentHashMap<String, String>();
	
	public static void put(String username, String token) {
		caches.put(username, token);
	}
	
	public static void del(String token) {
		String key = null;
		for (Iterator<String> ite = caches.keySet().iterator(); ite.hasNext();) {
			String k = ite.next();
			if (caches.get(k).equals(token)) {
				key = k;
			}
		}
		caches.remove(key);
	}
	
	public static String get(String username) {
		return caches.get(username);
	}
	
}
