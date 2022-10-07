package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica;
import br.com.jhisolution.user.hunters.repository.AvaliacaoEconomicaRepository;
import br.com.jhisolution.user.hunters.service.AvaliacaoEconomicaService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica}.
 */
@RestController
@RequestMapping("/api")
public class AvaliacaoEconomicaResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoEconomicaResource.class);

    private static final String ENTITY_NAME = "matriculaAvaliacaoEconomica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvaliacaoEconomicaService avaliacaoEconomicaService;

    private final AvaliacaoEconomicaRepository avaliacaoEconomicaRepository;

    public AvaliacaoEconomicaResource(
        AvaliacaoEconomicaService avaliacaoEconomicaService,
        AvaliacaoEconomicaRepository avaliacaoEconomicaRepository
    ) {
        this.avaliacaoEconomicaService = avaliacaoEconomicaService;
        this.avaliacaoEconomicaRepository = avaliacaoEconomicaRepository;
    }

    /**
     * {@code POST  /avaliacao-economicas} : Create a new avaliacaoEconomica.
     *
     * @param avaliacaoEconomica the avaliacaoEconomica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avaliacaoEconomica, or with status {@code 400 (Bad Request)} if the avaliacaoEconomica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avaliacao-economicas")
    public ResponseEntity<AvaliacaoEconomica> createAvaliacaoEconomica(@Valid @RequestBody AvaliacaoEconomica avaliacaoEconomica)
        throws URISyntaxException {
        log.debug("REST request to save AvaliacaoEconomica : {}", avaliacaoEconomica);
        if (avaliacaoEconomica.getId() != null) {
            throw new BadRequestAlertException("A new avaliacaoEconomica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvaliacaoEconomica result = avaliacaoEconomicaService.save(avaliacaoEconomica);
        return ResponseEntity
            .created(new URI("/api/avaliacao-economicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avaliacao-economicas/:id} : Updates an existing avaliacaoEconomica.
     *
     * @param id the id of the avaliacaoEconomica to save.
     * @param avaliacaoEconomica the avaliacaoEconomica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoEconomica,
     * or with status {@code 400 (Bad Request)} if the avaliacaoEconomica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoEconomica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avaliacao-economicas/{id}")
    public ResponseEntity<AvaliacaoEconomica> updateAvaliacaoEconomica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvaliacaoEconomica avaliacaoEconomica
    ) throws URISyntaxException {
        log.debug("REST request to update AvaliacaoEconomica : {}, {}", id, avaliacaoEconomica);
        if (avaliacaoEconomica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoEconomica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoEconomicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvaliacaoEconomica result = avaliacaoEconomicaService.update(avaliacaoEconomica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoEconomica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avaliacao-economicas/:id} : Partial updates given fields of an existing avaliacaoEconomica, field will ignore if it is null
     *
     * @param id the id of the avaliacaoEconomica to save.
     * @param avaliacaoEconomica the avaliacaoEconomica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoEconomica,
     * or with status {@code 400 (Bad Request)} if the avaliacaoEconomica is not valid,
     * or with status {@code 404 (Not Found)} if the avaliacaoEconomica is not found,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoEconomica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/avaliacao-economicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvaliacaoEconomica> partialUpdateAvaliacaoEconomica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvaliacaoEconomica avaliacaoEconomica
    ) throws URISyntaxException {
        log.debug("REST request to partial update AvaliacaoEconomica partially : {}, {}", id, avaliacaoEconomica);
        if (avaliacaoEconomica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoEconomica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoEconomicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvaliacaoEconomica> result = avaliacaoEconomicaService.partialUpdate(avaliacaoEconomica);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoEconomica.getId().toString())
        );
    }

    /**
     * {@code GET  /avaliacao-economicas} : get all the avaliacaoEconomicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avaliacaoEconomicas in body.
     */
    @GetMapping("/avaliacao-economicas")
    public ResponseEntity<List<AvaliacaoEconomica>> getAllAvaliacaoEconomicas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AvaliacaoEconomicas");
        Page<AvaliacaoEconomica> page = avaliacaoEconomicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avaliacao-economicas/:id} : get the "id" avaliacaoEconomica.
     *
     * @param id the id of the avaliacaoEconomica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avaliacaoEconomica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avaliacao-economicas/{id}")
    public ResponseEntity<AvaliacaoEconomica> getAvaliacaoEconomica(@PathVariable Long id) {
        log.debug("REST request to get AvaliacaoEconomica : {}", id);
        Optional<AvaliacaoEconomica> avaliacaoEconomica = avaliacaoEconomicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avaliacaoEconomica);
    }

    /**
     * {@code DELETE  /avaliacao-economicas/:id} : delete the "id" avaliacaoEconomica.
     *
     * @param id the id of the avaliacaoEconomica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avaliacao-economicas/{id}")
    public ResponseEntity<Void> deleteAvaliacaoEconomica(@PathVariable Long id) {
        log.debug("REST request to delete AvaliacaoEconomica : {}", id);
        avaliacaoEconomicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
