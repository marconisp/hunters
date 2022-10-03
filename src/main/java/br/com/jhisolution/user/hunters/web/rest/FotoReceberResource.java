package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoReceber;
import br.com.jhisolution.user.hunters.repository.FotoReceberRepository;
import br.com.jhisolution.user.hunters.service.FotoReceberService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoReceber}.
 */
@RestController
@RequestMapping("/api")
public class FotoReceberResource {

    private final Logger log = LoggerFactory.getLogger(FotoReceberResource.class);

    private static final String ENTITY_NAME = "fotoFotoReceber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoReceberService fotoReceberService;

    private final FotoReceberRepository fotoReceberRepository;

    public FotoReceberResource(FotoReceberService fotoReceberService, FotoReceberRepository fotoReceberRepository) {
        this.fotoReceberService = fotoReceberService;
        this.fotoReceberRepository = fotoReceberRepository;
    }

    /**
     * {@code POST  /foto-recebers} : Create a new fotoReceber.
     *
     * @param fotoReceber the fotoReceber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoReceber, or with status {@code 400 (Bad Request)} if the fotoReceber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-recebers")
    public ResponseEntity<FotoReceber> createFotoReceber(@Valid @RequestBody FotoReceber fotoReceber) throws URISyntaxException {
        log.debug("REST request to save FotoReceber : {}", fotoReceber);
        if (fotoReceber.getId() != null) {
            throw new BadRequestAlertException("A new fotoReceber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoReceber result = fotoReceberService.save(fotoReceber);
        return ResponseEntity
            .created(new URI("/api/foto-recebers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-recebers/:id} : Updates an existing fotoReceber.
     *
     * @param id the id of the fotoReceber to save.
     * @param fotoReceber the fotoReceber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoReceber,
     * or with status {@code 400 (Bad Request)} if the fotoReceber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoReceber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-recebers/{id}")
    public ResponseEntity<FotoReceber> updateFotoReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoReceber fotoReceber
    ) throws URISyntaxException {
        log.debug("REST request to update FotoReceber : {}, {}", id, fotoReceber);
        if (fotoReceber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoReceber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoReceberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoReceber result = fotoReceberService.update(fotoReceber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoReceber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-recebers/:id} : Partial updates given fields of an existing fotoReceber, field will ignore if it is null
     *
     * @param id the id of the fotoReceber to save.
     * @param fotoReceber the fotoReceber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoReceber,
     * or with status {@code 400 (Bad Request)} if the fotoReceber is not valid,
     * or with status {@code 404 (Not Found)} if the fotoReceber is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoReceber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-recebers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoReceber> partialUpdateFotoReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoReceber fotoReceber
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoReceber partially : {}, {}", id, fotoReceber);
        if (fotoReceber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoReceber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoReceberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoReceber> result = fotoReceberService.partialUpdate(fotoReceber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoReceber.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-recebers} : get all the fotoRecebers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoRecebers in body.
     */
    @GetMapping("/foto-recebers/receber/{id}")
    public ResponseEntity<List<FotoReceber>> getAllFotoRecebersByReceberId(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FotoRecebers");
        Page<FotoReceber> page = fotoReceberService.findAllByReceberId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/foto-recebers")
    public ResponseEntity<List<FotoReceber>> getAllFotoRecebers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoRecebers");
        Page<FotoReceber> page = fotoReceberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-recebers/:id} : get the "id" fotoReceber.
     *
     * @param id the id of the fotoReceber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoReceber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-recebers/{id}")
    public ResponseEntity<FotoReceber> getFotoReceber(@PathVariable Long id) {
        log.debug("REST request to get FotoReceber : {}", id);
        Optional<FotoReceber> fotoReceber = fotoReceberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoReceber);
    }

    /**
     * {@code DELETE  /foto-recebers/:id} : delete the "id" fotoReceber.
     *
     * @param id the id of the fotoReceber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-recebers/{id}")
    public ResponseEntity<Void> deleteFotoReceber(@PathVariable Long id) {
        log.debug("REST request to delete FotoReceber : {}", id);
        fotoReceberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
