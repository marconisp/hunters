package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Raca;
import br.com.jhisolution.user.hunters.repository.RacaRepository;
import br.com.jhisolution.user.hunters.service.RacaService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Raca}.
 */
@RestController
@RequestMapping("/api")
public class RacaResource {

    private final Logger log = LoggerFactory.getLogger(RacaResource.class);

    private static final String ENTITY_NAME = "configRaca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacaService racaService;

    private final RacaRepository racaRepository;

    public RacaResource(RacaService racaService, RacaRepository racaRepository) {
        this.racaService = racaService;
        this.racaRepository = racaRepository;
    }

    /**
     * {@code POST  /racas} : Create a new raca.
     *
     * @param raca the raca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raca, or with status {@code 400 (Bad Request)} if the raca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racas")
    public ResponseEntity<Raca> createRaca(@Valid @RequestBody Raca raca) throws URISyntaxException {
        log.debug("REST request to save Raca : {}", raca);
        if (raca.getId() != null) {
            throw new BadRequestAlertException("A new raca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Raca result = racaService.save(raca);
        return ResponseEntity
            .created(new URI("/api/racas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racas/:id} : Updates an existing raca.
     *
     * @param id the id of the raca to save.
     * @param raca the raca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raca,
     * or with status {@code 400 (Bad Request)} if the raca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racas/{id}")
    public ResponseEntity<Raca> updateRaca(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Raca raca)
        throws URISyntaxException {
        log.debug("REST request to update Raca : {}, {}", id, raca);
        if (raca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raca.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!racaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Raca result = racaService.update(raca);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raca.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /racas/:id} : Partial updates given fields of an existing raca, field will ignore if it is null
     *
     * @param id the id of the raca to save.
     * @param raca the raca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raca,
     * or with status {@code 400 (Bad Request)} if the raca is not valid,
     * or with status {@code 404 (Not Found)} if the raca is not found,
     * or with status {@code 500 (Internal Server Error)} if the raca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/racas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Raca> partialUpdateRaca(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Raca raca
    ) throws URISyntaxException {
        log.debug("REST request to partial update Raca partially : {}, {}", id, raca);
        if (raca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raca.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!racaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Raca> result = racaService.partialUpdate(raca);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raca.getId().toString())
        );
    }

    /**
     * {@code GET  /racas} : get all the racas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racas in body.
     */
    @GetMapping("/racas")
    public ResponseEntity<List<Raca>> getAllRacas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Racas");
        Page<Raca> page = racaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racas/:id} : get the "id" raca.
     *
     * @param id the id of the raca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racas/{id}")
    public ResponseEntity<Raca> getRaca(@PathVariable Long id) {
        log.debug("REST request to get Raca : {}", id);
        Optional<Raca> raca = racaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raca);
    }

    /**
     * {@code DELETE  /racas/:id} : delete the "id" raca.
     *
     * @param id the id of the raca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racas/{id}")
    public ResponseEntity<Void> deleteRaca(@PathVariable Long id) {
        log.debug("REST request to delete Raca : {}", id);
        racaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
