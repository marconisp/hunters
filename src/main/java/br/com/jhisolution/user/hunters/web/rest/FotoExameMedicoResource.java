package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoExameMedico;
import br.com.jhisolution.user.hunters.repository.FotoExameMedicoRepository;
import br.com.jhisolution.user.hunters.service.FotoExameMedicoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoExameMedico}.
 */
@RestController
@RequestMapping("/api")
public class FotoExameMedicoResource {

    private final Logger log = LoggerFactory.getLogger(FotoExameMedicoResource.class);

    private static final String ENTITY_NAME = "fotoFotoExameMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoExameMedicoService fotoExameMedicoService;

    private final FotoExameMedicoRepository fotoExameMedicoRepository;

    public FotoExameMedicoResource(FotoExameMedicoService fotoExameMedicoService, FotoExameMedicoRepository fotoExameMedicoRepository) {
        this.fotoExameMedicoService = fotoExameMedicoService;
        this.fotoExameMedicoRepository = fotoExameMedicoRepository;
    }

    /**
     * {@code POST  /foto-exame-medicos} : Create a new fotoExameMedico.
     *
     * @param fotoExameMedico the fotoExameMedico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoExameMedico, or with status {@code 400 (Bad Request)} if the fotoExameMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-exame-medicos")
    public ResponseEntity<FotoExameMedico> createFotoExameMedico(@Valid @RequestBody FotoExameMedico fotoExameMedico)
        throws URISyntaxException {
        log.debug("REST request to save FotoExameMedico : {}", fotoExameMedico);
        if (fotoExameMedico.getId() != null) {
            throw new BadRequestAlertException("A new fotoExameMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoExameMedico result = fotoExameMedicoService.save(fotoExameMedico);
        return ResponseEntity
            .created(new URI("/api/foto-exame-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-exame-medicos/:id} : Updates an existing fotoExameMedico.
     *
     * @param id the id of the fotoExameMedico to save.
     * @param fotoExameMedico the fotoExameMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoExameMedico,
     * or with status {@code 400 (Bad Request)} if the fotoExameMedico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoExameMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-exame-medicos/{id}")
    public ResponseEntity<FotoExameMedico> updateFotoExameMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoExameMedico fotoExameMedico
    ) throws URISyntaxException {
        log.debug("REST request to update FotoExameMedico : {}, {}", id, fotoExameMedico);
        if (fotoExameMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoExameMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoExameMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoExameMedico result = fotoExameMedicoService.update(fotoExameMedico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoExameMedico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-exame-medicos/:id} : Partial updates given fields of an existing fotoExameMedico, field will ignore if it is null
     *
     * @param id the id of the fotoExameMedico to save.
     * @param fotoExameMedico the fotoExameMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoExameMedico,
     * or with status {@code 400 (Bad Request)} if the fotoExameMedico is not valid,
     * or with status {@code 404 (Not Found)} if the fotoExameMedico is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoExameMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-exame-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoExameMedico> partialUpdateFotoExameMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoExameMedico fotoExameMedico
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoExameMedico partially : {}, {}", id, fotoExameMedico);
        if (fotoExameMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoExameMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoExameMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoExameMedico> result = fotoExameMedicoService.partialUpdate(fotoExameMedico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoExameMedico.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-exame-medicos} : get all the fotoExameMedicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoExameMedicos in body.
     */
    @GetMapping("/foto-exame-medicos")
    public ResponseEntity<List<FotoExameMedico>> getAllFotoExameMedicos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoExameMedicos");
        Page<FotoExameMedico> page = fotoExameMedicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-exame-medicos/:id} : get the "id" fotoExameMedico.
     *
     * @param id the id of the fotoExameMedico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoExameMedico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-exame-medicos/{id}")
    public ResponseEntity<FotoExameMedico> getFotoExameMedico(@PathVariable Long id) {
        log.debug("REST request to get FotoExameMedico : {}", id);
        Optional<FotoExameMedico> fotoExameMedico = fotoExameMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoExameMedico);
    }

    /**
     * {@code DELETE  /foto-exame-medicos/:id} : delete the "id" fotoExameMedico.
     *
     * @param id the id of the fotoExameMedico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-exame-medicos/{id}")
    public ResponseEntity<Void> deleteFotoExameMedico(@PathVariable Long id) {
        log.debug("REST request to delete FotoExameMedico : {}", id);
        fotoExameMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
