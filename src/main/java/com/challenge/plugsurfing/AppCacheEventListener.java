package com.challenge.plugsurfing;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppCacheEventListener implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		log.info("Cache event {} for key {}, old value {}, new value {}", event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
	}

}
