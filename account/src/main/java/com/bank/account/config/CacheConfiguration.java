package com.bank.account.config;


import com.bank.account.dto.BankDetailsDto;
import com.bank.account.dto.ProfileDto;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager ehCacheManager() {
        final CachingProvider provider = Caching.getCachingProvider();
        final CacheManager cacheManager = provider.getCacheManager();

        final CacheConfigurationBuilder<Long, ProfileDto> profileConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class,
                                ProfileDto.class,
                                ResourcePoolsBuilder
                                        .newResourcePoolsBuilder().offheap(1, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(5)));

        final javax.cache.configuration.Configuration<Long, ProfileDto> profileCacheConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(profileConfiguration);

        cacheManager.createCache("profileCache", profileCacheConfiguration);

        final CacheConfigurationBuilder<Long, BankDetailsDto> configuration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class,
                                BankDetailsDto.class,
                                ResourcePoolsBuilder
                                        .newResourcePoolsBuilder().offheap(1, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(365)));

        final javax.cache.configuration.Configuration<Long, BankDetailsDto> bankDetailsCacheConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache("bankDetailsCache", bankDetailsCacheConfiguration);

        return cacheManager;
    }

}
