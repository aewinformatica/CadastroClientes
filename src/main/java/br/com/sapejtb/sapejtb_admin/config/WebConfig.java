package br.com.sapejtb.sapejtb_admin.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.sapejtb.sapejtb_admin.controller.ClienteController;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;


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
