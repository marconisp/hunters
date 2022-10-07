package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.DiaSemana;
import br.com.jhisolution.user.hunters.repository.DiaSemanaRepository;
import br.com.jhisolution.user.hunters.service.DiaSemanaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.DiaSemana}.
 */
@RestController
@RequestMapping("/api")
public class DiaSemanaResource {

    private final Logger log = LoggerFactory.getLogger(DiaSemanaResource.class);

    private static final String ENTITY_NAME = "configDiaSemana";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiaSemanaService diaSemanaService;

    private final DiaSemanaRepository diaSemanaRepository;

    public DiaSemanaResource(DiaSemanaService diaSemanaService, DiaSemanaRepository diaSemanaRepository) {
        this.diaSemanaService = diaSemanaService;
        this.diaSemanaRepository = diaSemanaRepository;
    }

    /**
     * {@code POST  /dia-semanas} : Create a new diaSemana.
     *
     * @param diaSemana the diaSemana to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diaSemana, or with status {@code 400 (Bad Request)} if the diaSemana has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dia-semanas")
    public ResponseEntity<DiaSemana> createDiaSemana(@Valid @RequestBody DiaSemana diaSemana) throws URISyntaxException {
        log.debug("REST request to save DiaSemana : {}", diaSemana);
        if (diaSemana.getId() != null) {
            throw new BadRequestAlertException("A new diaSemana cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaSemana result = diaSemanaService.save(diaSemana);
        return ResponseEntity
            .created(new URI("/api/dia-semanas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dia-semanas/:id} : Updates an existing diaSemana.
     *
     * @param id the id of the diaSemana to save.
     * @param diaSemana the diaSemana to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaSemana,
     * or with status {@code 400 (Bad Request)} if the diaSemana is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diaSemana couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dia-semanas/{id}")
    public ResponseEntity<DiaSemana> updateDiaSemana(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiaSemana diaSemana
    ) throws URISyntaxException {
        log.debug("REST request to update DiaSemana : {}, {}", id, diaSemana);
        if (diaSemana.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaSemana.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaSemanaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiaSemana result = diaSemanaService.update(diaSemana);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diaSemana.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dia-semanas/:id} : Partial updates given fields of an existing diaSemana, field will ignore if it is null
     *
     * @param id the id of the diaSemana to save.
     * @param diaSemana the diaSemana to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaSemana,
     * or with status {@code 400 (Bad Request)} if the diaSemana is not valid,
     * or with status {@code 404 (Not Found)} if the diaSemana is not found,
     * or with status {@code 500 (Internal Server Error)} if the diaSemana couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dia-semanas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiaSemana> partialUpdateDiaSemana(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiaSemana diaSemana
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiaSemana partially : {}, {}", id, diaSemana);
        if (diaSemana.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaSemana.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaSemanaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiaSemana> result = diaSemanaService.partialUpdate(diaSemana);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diaSemana.getId().toString())
        );
    }

    /**
     * {@code GET  /dia-semanas} : get all the diaSemanas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diaSemanas in body.
     */
    @GetMapping("/dia-semanas")
    public ResponseEntity<List<DiaSemana>> getAllDiaSemanas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DiaSemanas");
        Page<DiaSemana> page = diaSemanaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dia-semanas/:id} : get the "id" diaSemana.
     *
     * @param id the id of the diaSemana to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diaSemana, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dia-semanas/{id}")
    public ResponseEntity<DiaSemana> getDiaSemana(@PathVariable Long id) {
        log.debug("REST request to get DiaSemana : {}", id);
        Optional<DiaSemana> diaSemana = diaSemanaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diaSemana);
    }

    /**
     * {@code DELETE  /dia-semanas/:id} : delete the "id" diaSemana.
     *
     * @param id the id of the diaSemana to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dia-semanas/{id}")
    public ResponseEntity<Void> deleteDiaSemana(@PathVariable Long id) {
        log.debug("REST request to delete DiaSemana : {}", id);
        diaSemanaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
