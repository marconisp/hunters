package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.User;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.repository.UserRepository;
import br.com.jhisolution.user.hunters.security.AuthoritiesConstants;
import br.com.jhisolution.user.hunters.security.SecurityUtils;
import br.com.jhisolution.user.hunters.service.DadosPessoaisService;
import br.com.jhisolution.user.hunters.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.DadosPessoais}.
 */
@RestController
@RequestMapping("/api")
public class DadosPessoaisResource {

    private final Logger log = LoggerFactory.getLogger(DadosPessoaisResource.class);

    private static final String ENTITY_NAME = "userDadosPessoais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadosPessoaisService dadosPessoaisService;
    private final DadosPessoaisRepository dadosPessoaisRepository;
    private final UserRepository userRepository;

    public DadosPessoaisResource(
        UserRepository userRepository,
        DadosPessoaisService dadosPessoaisService,
        DadosPessoaisRepository dadosPessoaisRepository
    ) {
        this.userRepository = userRepository;
        this.dadosPessoaisService = dadosPessoaisService;
        this.dadosPessoaisRepository = dadosPessoaisRepository;
    }

    /**
     * {@code POST  /dados-pessoais} : Create a new dadosPessoais.
     *
     * @param dadosPessoais the dadosPessoais to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadosPessoais, or with status {@code 400 (Bad Request)} if the dadosPessoais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dados-pessoais")
    public ResponseEntity<DadosPessoais> createDadosPessoais(@Valid @RequestBody DadosPessoais dadosPessoais) throws URISyntaxException {
        log.debug("REST request to save DadosPessoais : {}", dadosPessoais);
        if (dadosPessoais.getId() != null) {
            throw new BadRequestAlertException("A new dadosPessoais cannot already have an ID", ENTITY_NAME, "idexists");
        }

        User user = this.getUserLogin().orElse(new User());

        dadosPessoais.setUser(user);

        DadosPessoais result = dadosPessoaisService.save(dadosPessoais);
        return ResponseEntity
            .created(new URI("/api/dados-pessoais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dados-pessoais/:id} : Updates an existing dadosPessoais.
     *
     * @param id the id of the dadosPessoais to save.
     * @param dadosPessoais the dadosPessoais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosPessoais,
     * or with status {@code 400 (Bad Request)} if the dadosPessoais is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadosPessoais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dados-pessoais/{id}")
    public ResponseEntity<DadosPessoais> updateDadosPessoais(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DadosPessoais dadosPessoais
    ) throws URISyntaxException {
        log.debug("REST request to update DadosPessoais : {}, {}", id, dadosPessoais);
        if (dadosPessoais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosPessoais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosPessoaisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        User user = this.getUserLogin().orElse(new User());

        dadosPessoais.setUser(user);

        DadosPessoais result = dadosPessoaisService.update(dadosPessoais);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosPessoais.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dados-pessoais/:id} : Partial updates given fields of an existing dadosPessoais, field will ignore if it is null
     *
     * @param id the id of the dadosPessoais to save.
     * @param dadosPessoais the dadosPessoais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosPessoais,
     * or with status {@code 400 (Bad Request)} if the dadosPessoais is not valid,
     * or with status {@code 404 (Not Found)} if the dadosPessoais is not found,
     * or with status {@code 500 (Internal Server Error)} if the dadosPessoais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dados-pessoais/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DadosPessoais> partialUpdateDadosPessoais(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DadosPessoais dadosPessoais
    ) throws URISyntaxException {
        log.debug("REST request to partial update DadosPessoais partially : {}, {}", id, dadosPessoais);
        if (dadosPessoais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosPessoais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosPessoaisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DadosPessoais> result = dadosPessoaisService.partialUpdate(dadosPessoais);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosPessoais.getId().toString())
        );
    }

    /**
     * {@code GET  /dados-pessoais} : get all the dadosPessoais.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadosPessoais in body.
     */
    @GetMapping("/dados-pessoais")
    public ResponseEntity<List<DadosPessoais>> getAllDadosPessoais(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DadosPessoais");
        Page<DadosPessoais> page;
        User user = this.getUserLogin().orElse(new User());

        Boolean admin =
            user
                .getAuthorities()
                .stream()
                .filter(role ->
                    role.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN) ||
                    role.getName().equalsIgnoreCase(AuthoritiesConstants.DIRETOR) ||
                    role.getName().equalsIgnoreCase(AuthoritiesConstants.SECRETARIA)
                )
                .count() >=
            1;

        if (admin) {
            page = dadosPessoaisService.findAll(pageable);
        } else {
            if (eagerload) {
                page = dadosPessoaisService.findAllByUser(pageable, user);
            } else {
                page = dadosPessoaisService.findAllByUser(pageable, user);
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dados-pessoais/:id} : get the "id" dadosPessoais.
     *
     * @param id the id of the dadosPessoais to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadosPessoais, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dados-pessoais/{id}")
    public ResponseEntity<DadosPessoais> getDadosPessoais(@PathVariable Long id) {
        log.debug("REST request to get DadosPessoais : {}", id);
        Optional<DadosPessoais> dadosPessoais = dadosPessoaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dadosPessoais);
    }

    /**
     * {@code DELETE  /dados-pessoais/:id} : delete the "id" dadosPessoais.
     *
     * @param id the id of the dadosPessoais to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dados-pessoais/{id}")
    public ResponseEntity<Void> deleteDadosPessoais(@PathVariable Long id) {
        log.debug("REST request to delete DadosPessoais : {}", id);
        dadosPessoaisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Optional<User> getUserLogin() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }
}
