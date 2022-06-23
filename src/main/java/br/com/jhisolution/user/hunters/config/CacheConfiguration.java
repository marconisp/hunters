package br.com.jhisolution.user.hunters.config;

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
            createCache(cm, br.com.jhisolution.user.hunters.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.jhisolution.user.hunters.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.jhisolution.user.hunters.domain.User.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Authority.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.jhisolution.user.hunters.domain.User1.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.User1.class.getName() + ".dadosPessoais");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".mensagems");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".avisos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".documentos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".enderecos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.Endereco.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Mensagem.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Aviso.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Documento.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Documento.class.getName() + ".fotos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.Foto.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoAvatar.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoIcon.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoDocumento.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoMensagem.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Raca.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Religiao.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.EstadoCivil.class.getName());
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
