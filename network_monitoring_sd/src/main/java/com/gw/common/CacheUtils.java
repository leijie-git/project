package com.gw.common;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 * Cache工具类
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {

	private static CacheManager cacheManager = ((EhCacheCacheManager) SpringBeanTool.getBean("cacheManager")).getCacheManager();

	private static final String SYS_CACHE = "sysCache";

	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	private static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	private static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	private static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	/**
	 * 获得一个Cache，没有则创建一个。
	 * 
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName) {
		synchronized (cacheManager) {
			Cache cache = cacheManager.getCache(cacheName);
			if (cache == null) {
				cacheManager.addCache(cacheName);
				cache = cacheManager.getCache(cacheName);
				cache.getCacheConfiguration().setEternal(true);
			}
			return cache;
		}
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

}
