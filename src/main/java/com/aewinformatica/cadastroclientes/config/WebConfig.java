package com.aewinformatica.cadastroclientes.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aewinformatica.cadastroclientes.controller.ClienteController;
import com.aewinformatica.cadastroclientes.storage.FotoStorage;


@ComponentScan(basePackageClasses = { ClienteController.class, FotoStorage.class })
@Configuration
@EnableCaching
@EnableAsync
public class WebConfig implements WebMvcConfigurer {
	
//	@Bean
//	public FotoStorage fotoStorage(){
//		
//		return new FotoStorageLocal();
//	}
	
//	@Bean
//	public CacheManager cacheManager() throws Exception {		
//		return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
//				getClass().getResource("/env/ehcache.xml").toURI(),
//				getClass().getClassLoader()));
//	}
}
