package com.dd.campsites.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.dd.campsites.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.dd.campsites.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.dd.campsites.domain.User.class.getName());
            createCache(cm, com.dd.campsites.domain.Authority.class.getName());
            createCache(cm, com.dd.campsites.domain.User.class.getName() + ".authorities");
            createCache(cm, com.dd.campsites.domain.AuthenticatedUser.class.getName());
            createCache(cm, com.dd.campsites.domain.Post.class.getName());
            createCache(cm, com.dd.campsites.domain.Post.class.getName() + ".images");
            createCache(cm, com.dd.campsites.domain.Post.class.getName() + ".comments");
            createCache(cm, com.dd.campsites.domain.Images.class.getName());
            createCache(cm, com.dd.campsites.domain.Comments.class.getName());
            createCache(cm, com.dd.campsites.domain.Location.class.getName());
            createCache(cm, com.dd.campsites.domain.Rating.class.getName());
            createCache(cm, com.dd.campsites.domain.ListingType.class.getName());
            createCache(cm, com.dd.campsites.domain.Listing.class.getName());
            createCache(cm, com.dd.campsites.domain.Photos.class.getName());
            createCache(cm, com.dd.campsites.domain.Videos.class.getName());
            createCache(cm, com.dd.campsites.domain.Features.class.getName());
            createCache(cm, com.dd.campsites.domain.Bookings.class.getName());
            createCache(cm, com.dd.campsites.domain.Bookings.class.getName() + ".invoices");
            createCache(cm, com.dd.campsites.domain.Invoice.class.getName());
            createCache(cm, com.dd.campsites.domain.Room.class.getName());
            createCache(cm, com.dd.campsites.domain.RoomType.class.getName());
            createCache(cm, com.dd.campsites.domain.Review.class.getName());
            createCache(cm, com.dd.campsites.domain.Like.class.getName());
            createCache(cm, com.dd.campsites.domain.Followers.class.getName());
            createCache(cm, com.dd.campsites.domain.FeaturesListing.class.getName());
            createCache(cm, com.dd.campsites.domain.FeaturesInRoom.class.getName());
            createCache(cm, com.dd.campsites.domain.RoomsForListing.class.getName());
            createCache(cm, com.dd.campsites.domain.RoomsInBooking.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
