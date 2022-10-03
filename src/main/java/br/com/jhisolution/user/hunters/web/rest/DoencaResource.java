package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Doenca;
import br.com.jhisolution.user.hunters.repository.DoencaRepository;
import br.com.jhisolution.user.hunters.service.DoencaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Doenca}.
 */
@RestController
@RequestMapping("/api")
public class DoencaResource {

    private final Logger log = LoggerFactory.getLogger(DoencaResource.class);

    private static final String ENTITY_NAME = "matriculaDoenca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoencaService doencaService;

    private final DoencaRepository doencaRepository;

    public DoencaResource(DoencaService doencaService, DoencaRepository doencaRepository) {
        this.doencaService = doencaService;
        this.doencaRepository = doencaRepository;
    }

    /**
     * {@code POST  /doencas} : Create a new doenca.
     *
     * @param doenca the doenca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenca, or with status {@code 400 (Bad Request)} if the doenca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doencas")
    public ResponseEntity<Doenca> createDoenca(@Valid @RequestBody Doenca doenca) throws URISyntaxException {
        log.debug("REST request to save Doenca : {}", doenca);
        if (doenca.getId() != null) {
            throw new BadRequestAlertException("A new doenca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Doenca result = doencaService.save(doenca);
        return ResponseEntity
            .created(new URI("/api/doencas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doencas/:id} : Updates an existing doenca.
     *
     * @param id the id of the doenca to save.
     * @param doenca the doenca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenca,
     * or with status {@code 400 (Bad Request)} if the doenca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doencas/{id}")
    public ResponseEntity<Doenca> updateDoenca(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Doenca doenca
    ) throws URISyntaxException {
        log.debug("REST request to update Doenca : {}, {}", id, doenca);
        if (doenca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doenca.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doencaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Doenca result = doencaService.update(doenca);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenca.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doencas/:id} : Partial updates given fields of an existing doenca, field will ignore if it is null
     *
     * @param id the id of the doenca to save.
     * @param doenca the doenca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenca,
     * or with status {@code 400 (Bad Request)} if the doenca is not valid,
     * or with status {@code 404 (Not Found)} if the doenca is not found,
     * or with status {@code 500 (Internal Server Error)} if the doenca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doencas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doenca> partialUpdateDoenca(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Doenca doenca
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doenca partially : {}, {}", id, doenca);
        if (doenca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doenca.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doencaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doenca> result = doencaService.partialUpdate(doenca);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenca.getId().toString())
        );
    }

    /**
     * {@code GET  /doencas} : get all the doencas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doencas in body.
     */
    @GetMapping("/doencas")
    public ResponseEntity<List<Doenca>> getAllDoencas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Doencas");
        Page<Doenca> page = doencaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doencas/:id} : get the "id" doenca.
     *
     * @param id the id of the doenca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doencas/{id}")
    public ResponseEntity<Doenca> getDoenca(@PathVariable Long id) {
        log.debug("REST request to get Doenca : {}", id);
        Optional<Doenca> doenca = doencaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doenca);
    }

    /**
     * {@code DELETE  /doencas/:id} : delete the "id" doenca.
     *
     * @param id the id of the doenca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doencas/{id}")
    public ResponseEntity<Void> deleteDoenca(@PathVariable Long id) {
        log.debug("REST request to delete Doenca : {}", id);
        doencaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
