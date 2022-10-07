package br.com.jhisolution.user.hunters.config;

import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoPessoa.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoDocumento.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".matriculas");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".dadosMedicos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".avaliacaoEconomicas");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".acompanhamentoAlunos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".colaboradors");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".caracteristicasPsiquicas");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".exameMedicos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".eventos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".pagars");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".recebers");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".entradaEstoques");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosPessoais.class.getName() + ".saidaEstoques");
            createCache(cm, br.com.jhisolution.user.hunters.domain.Matricula.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Turma.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.PeriodoDuracao.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.PeriodoDuracao.class.getName() + ".diaSemanas");
            createCache(cm, br.com.jhisolution.user.hunters.domain.DiaSemana.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.DadosMedico.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Vacina.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Doenca.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Alergia.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno.class.getName() + ".itemMaterias");
            createCache(cm, br.com.jhisolution.user.hunters.domain.ItemMateria.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Materia.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Colaborador.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Colaborador.class.getName() + ".agendaColaboradors");
            createCache(cm, br.com.jhisolution.user.hunters.domain.Colaborador.class.getName() + ".tipoContratacaos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoContratacao.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.AgendaColaborador.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.ExameMedico.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.ExameMedico.class.getName() + ".fotoExameMedicos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoExameMedico.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Evento.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Evento.class.getName() + ".enderecos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.EnderecoEvento.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Pagar.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Pagar.class.getName() + ".fotoPagars");
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoPagar.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.PagarPara.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoPagar.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Receber.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Receber.class.getName() + ".fotoRecebers");
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoReceber.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.ReceberDe.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoReceber.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.TipoTransacao.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Estoque.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Produto.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.Produto.class.getName() + ".fotoProdutos");
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoProduto.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.EntradaEstoque.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.EntradaEstoque.class.getName() + ".fotoEntradaEstoques");
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.SaidaEstoque.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.SaidaEstoque.class.getName() + ".fotoSaidaEstoques");
            createCache(cm, br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.GrupoProduto.class.getName());
            createCache(cm, br.com.jhisolution.user.hunters.domain.GrupoProduto.class.getName() + ".nomes");
            createCache(cm, br.com.jhisolution.user.hunters.domain.SubGrupoProduto.class.getName());
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
