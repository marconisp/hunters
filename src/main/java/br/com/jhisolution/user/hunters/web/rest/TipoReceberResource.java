package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoReceber;
import br.com.jhisolution.user.hunters.repository.TipoReceberRepository;
import br.com.jhisolution.user.hunters.service.TipoReceberService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoReceber}.
 */
@RestController
@RequestMapping("/api")
public class TipoReceberResource {

    private final Logger log = LoggerFactory.getLogger(TipoReceberResource.class);

    private static final String ENTITY_NAME = "controleTipoReceber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoReceberService tipoReceberService;

    private final TipoReceberRepository tipoReceberRepository;

    public TipoReceberResource(TipoReceberService tipoReceberService, TipoReceberRepository tipoReceberRepository) {
        this.tipoReceberService = tipoReceberService;
        this.tipoReceberRepository = tipoReceberRepository;
    }

    /**
     * {@code POST  /tipo-recebers} : Create a new tipoReceber.
     *
     * @param tipoReceber the tipoReceber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoReceber, or with status {@code 400 (Bad Request)} if the tipoReceber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-recebers")
    public ResponseEntity<TipoReceber> createTipoReceber(@Valid @RequestBody TipoReceber tipoReceber) throws URISyntaxException {
        log.debug("REST request to save TipoReceber : {}", tipoReceber);
        if (tipoReceber.getId() != null) {
            throw new BadRequestAlertException("A new tipoReceber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoReceber result = tipoReceberService.save(tipoReceber);
        return ResponseEntity
            .created(new URI("/api/tipo-recebers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-recebers/:id} : Updates an existing tipoReceber.
     *
     * @param id the id of the tipoReceber to save.
     * @param tipoReceber the tipoReceber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoReceber,
     * or with status {@code 400 (Bad Request)} if the tipoReceber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoReceber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-recebers/{id}")
    public ResponseEntity<TipoReceber> updateTipoReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoReceber tipoReceber
    ) throws URISyntaxException {
        log.debug("REST request to update TipoReceber : {}, {}", id, tipoReceber);
        if (tipoReceber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoReceber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoReceberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoReceber result = tipoReceberService.update(tipoReceber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoReceber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-recebers/:id} : Partial updates given fields of an existing tipoReceber, field will ignore if it is null
     *
     * @param id the id of the tipoReceber to save.
     * @param tipoReceber the tipoReceber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoReceber,
     * or with status {@code 400 (Bad Request)} if the tipoReceber is not valid,
     * or with status {@code 404 (Not Found)} if the tipoReceber is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoReceber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-recebers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoReceber> partialUpdateTipoReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoReceber tipoReceber
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoReceber partially : {}, {}", id, tipoReceber);
        if (tipoReceber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoReceber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoReceberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoReceber> result = tipoReceberService.partialUpdate(tipoReceber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoReceber.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-recebers} : get all the tipoRecebers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRecebers in body.
     */
    @GetMapping("/tipo-recebers")
    public ResponseEntity<List<TipoReceber>> getAllTipoRecebers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoRecebers");
        Page<TipoReceber> page = tipoReceberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-recebers/:id} : get the "id" tipoReceber.
     *
     * @param id the id of the tipoReceber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoReceber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-recebers/{id}")
    public ResponseEntity<TipoReceber> getTipoReceber(@PathVariable Long id) {
        log.debug("REST request to get TipoReceber : {}", id);
        Optional<TipoReceber> tipoReceber = tipoReceberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoReceber);
    }

    /**
     * {@code DELETE  /tipo-recebers/:id} : delete the "id" tipoReceber.
     *
     * @param id the id of the tipoReceber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-recebers/{id}")
    public ResponseEntity<Void> deleteTipoReceber(@PathVariable Long id) {
        log.debug("REST request to delete TipoReceber : {}", id);
        tipoReceberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
