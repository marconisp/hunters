package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Alergia;
import br.com.jhisolution.user.hunters.repository.AlergiaRepository;
import br.com.jhisolution.user.hunters.service.AlergiaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Alergia}.
 */
@RestController
@RequestMapping("/api")
public class AlergiaResource {

    private final Logger log = LoggerFactory.getLogger(AlergiaResource.class);

    private static final String ENTITY_NAME = "matriculaAlergia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlergiaService alergiaService;

    private final AlergiaRepository alergiaRepository;

    public AlergiaResource(AlergiaService alergiaService, AlergiaRepository alergiaRepository) {
        this.alergiaService = alergiaService;
        this.alergiaRepository = alergiaRepository;
    }

    /**
     * {@code POST  /alergias} : Create a new alergia.
     *
     * @param alergia the alergia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alergia, or with status {@code 400 (Bad Request)} if the alergia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alergias")
    public ResponseEntity<Alergia> createAlergia(@Valid @RequestBody Alergia alergia) throws URISyntaxException {
        log.debug("REST request to save Alergia : {}", alergia);
        if (alergia.getId() != null) {
            throw new BadRequestAlertException("A new alergia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alergia result = alergiaService.save(alergia);
        return ResponseEntity
            .created(new URI("/api/alergias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alergias/:id} : Updates an existing alergia.
     *
     * @param id the id of the alergia to save.
     * @param alergia the alergia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alergia,
     * or with status {@code 400 (Bad Request)} if the alergia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alergia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alergias/{id}")
    public ResponseEntity<Alergia> updateAlergia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Alergia alergia
    ) throws URISyntaxException {
        log.debug("REST request to update Alergia : {}, {}", id, alergia);
        if (alergia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alergia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alergiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Alergia result = alergiaService.update(alergia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alergia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alergias/:id} : Partial updates given fields of an existing alergia, field will ignore if it is null
     *
     * @param id the id of the alergia to save.
     * @param alergia the alergia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alergia,
     * or with status {@code 400 (Bad Request)} if the alergia is not valid,
     * or with status {@code 404 (Not Found)} if the alergia is not found,
     * or with status {@code 500 (Internal Server Error)} if the alergia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alergias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Alergia> partialUpdateAlergia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Alergia alergia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Alergia partially : {}, {}", id, alergia);
        if (alergia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alergia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alergiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Alergia> result = alergiaService.partialUpdate(alergia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alergia.getId().toString())
        );
    }

    /**
     * {@code GET  /alergias} : get all the alergias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alergias in body.
     */
    @GetMapping("/alergias")
    public ResponseEntity<List<Alergia>> getAllAlergias(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Alergias");
        Page<Alergia> page = alergiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alergias/:id} : get the "id" alergia.
     *
     * @param id the id of the alergia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alergia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alergias/{id}")
    public ResponseEntity<Alergia> getAlergia(@PathVariable Long id) {
        log.debug("REST request to get Alergia : {}", id);
        Optional<Alergia> alergia = alergiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alergia);
    }

    /**
     * {@code DELETE  /alergias/:id} : delete the "id" alergia.
     *
     * @param id the id of the alergia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alergias/{id}")
    public ResponseEntity<Void> deleteAlergia(@PathVariable Long id) {
        log.debug("REST request to delete Alergia : {}", id);
        alergiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
