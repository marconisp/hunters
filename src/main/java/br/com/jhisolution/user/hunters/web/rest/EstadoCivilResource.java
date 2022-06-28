package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import br.com.jhisolution.user.hunters.repository.EstadoCivilRepository;
import br.com.jhisolution.user.hunters.service.EstadoCivilService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.EstadoCivil}.
 */
@RestController
@RequestMapping("/api")
public class EstadoCivilResource {

    private final Logger log = LoggerFactory.getLogger(EstadoCivilResource.class);

    private static final String ENTITY_NAME = "configEstadoCivil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoCivilService estadoCivilService;

    private final EstadoCivilRepository estadoCivilRepository;

    public EstadoCivilResource(EstadoCivilService estadoCivilService, EstadoCivilRepository estadoCivilRepository) {
        this.estadoCivilService = estadoCivilService;
        this.estadoCivilRepository = estadoCivilRepository;
    }

    /**
     * {@code POST  /estado-civils} : Create a new estadoCivil.
     *
     * @param estadoCivil the estadoCivil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoCivil, or with status {@code 400 (Bad Request)} if the estadoCivil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-civils")
    public ResponseEntity<EstadoCivil> createEstadoCivil(@Valid @RequestBody EstadoCivil estadoCivil) throws URISyntaxException {
        log.debug("REST request to save EstadoCivil : {}", estadoCivil);
        if (estadoCivil.getId() != null) {
            throw new BadRequestAlertException("A new estadoCivil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoCivil result = estadoCivilService.save(estadoCivil);
        return ResponseEntity
            .created(new URI("/api/estado-civils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-civils/:id} : Updates an existing estadoCivil.
     *
     * @param id the id of the estadoCivil to save.
     * @param estadoCivil the estadoCivil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoCivil,
     * or with status {@code 400 (Bad Request)} if the estadoCivil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoCivil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-civils/{id}")
    public ResponseEntity<EstadoCivil> updateEstadoCivil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstadoCivil estadoCivil
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoCivil : {}, {}", id, estadoCivil);
        if (estadoCivil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoCivil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoCivilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoCivil result = estadoCivilService.update(estadoCivil);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoCivil.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-civils/:id} : Partial updates given fields of an existing estadoCivil, field will ignore if it is null
     *
     * @param id the id of the estadoCivil to save.
     * @param estadoCivil the estadoCivil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoCivil,
     * or with status {@code 400 (Bad Request)} if the estadoCivil is not valid,
     * or with status {@code 404 (Not Found)} if the estadoCivil is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoCivil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-civils/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoCivil> partialUpdateEstadoCivil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstadoCivil estadoCivil
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoCivil partially : {}, {}", id, estadoCivil);
        if (estadoCivil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoCivil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoCivilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoCivil> result = estadoCivilService.partialUpdate(estadoCivil);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoCivil.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-civils} : get all the estadoCivils.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoCivils in body.
     */
    @GetMapping("/estado-civils")
    public ResponseEntity<List<EstadoCivil>> getAllEstadoCivils(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EstadoCivils");
        Page<EstadoCivil> page = estadoCivilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-civils/:id} : get the "id" estadoCivil.
     *
     * @param id the id of the estadoCivil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoCivil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-civils/{id}")
    public ResponseEntity<EstadoCivil> getEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to get EstadoCivil : {}", id);
        Optional<EstadoCivil> estadoCivil = estadoCivilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoCivil);
    }

    /**
     * {@code DELETE  /estado-civils/:id} : delete the "id" estadoCivil.
     *
     * @param id the id of the estadoCivil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-civils/{id}")
    public ResponseEntity<Void> deleteEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to delete EstadoCivil : {}", id);
        estadoCivilService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
