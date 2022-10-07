package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.ExameMedico;
import br.com.jhisolution.user.hunters.repository.ExameMedicoRepository;
import br.com.jhisolution.user.hunters.service.ExameMedicoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.ExameMedico}.
 */
@RestController
@RequestMapping("/api")
public class ExameMedicoResource {

    private final Logger log = LoggerFactory.getLogger(ExameMedicoResource.class);

    private static final String ENTITY_NAME = "eventoExameMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExameMedicoService exameMedicoService;

    private final ExameMedicoRepository exameMedicoRepository;

    public ExameMedicoResource(ExameMedicoService exameMedicoService, ExameMedicoRepository exameMedicoRepository) {
        this.exameMedicoService = exameMedicoService;
        this.exameMedicoRepository = exameMedicoRepository;
    }

    /**
     * {@code POST  /exame-medicos} : Create a new exameMedico.
     *
     * @param exameMedico the exameMedico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exameMedico, or with status {@code 400 (Bad Request)} if the exameMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exame-medicos")
    public ResponseEntity<ExameMedico> createExameMedico(@Valid @RequestBody ExameMedico exameMedico) throws URISyntaxException {
        log.debug("REST request to save ExameMedico : {}", exameMedico);
        if (exameMedico.getId() != null) {
            throw new BadRequestAlertException("A new exameMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExameMedico result = exameMedicoService.save(exameMedico);
        return ResponseEntity
            .created(new URI("/api/exame-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exame-medicos/:id} : Updates an existing exameMedico.
     *
     * @param id the id of the exameMedico to save.
     * @param exameMedico the exameMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exameMedico,
     * or with status {@code 400 (Bad Request)} if the exameMedico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exameMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exame-medicos/{id}")
    public ResponseEntity<ExameMedico> updateExameMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExameMedico exameMedico
    ) throws URISyntaxException {
        log.debug("REST request to update ExameMedico : {}, {}", id, exameMedico);
        if (exameMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exameMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exameMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExameMedico result = exameMedicoService.update(exameMedico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exameMedico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exame-medicos/:id} : Partial updates given fields of an existing exameMedico, field will ignore if it is null
     *
     * @param id the id of the exameMedico to save.
     * @param exameMedico the exameMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exameMedico,
     * or with status {@code 400 (Bad Request)} if the exameMedico is not valid,
     * or with status {@code 404 (Not Found)} if the exameMedico is not found,
     * or with status {@code 500 (Internal Server Error)} if the exameMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exame-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExameMedico> partialUpdateExameMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExameMedico exameMedico
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExameMedico partially : {}, {}", id, exameMedico);
        if (exameMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exameMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exameMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExameMedico> result = exameMedicoService.partialUpdate(exameMedico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exameMedico.getId().toString())
        );
    }

    /**
     * {@code GET  /exame-medicos} : get all the exameMedicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exameMedicos in body.
     */
    @GetMapping("/exame-medicos")
    public ResponseEntity<List<ExameMedico>> getAllExameMedicos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ExameMedicos");
        Page<ExameMedico> page = exameMedicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exame-medicos/:id} : get the "id" exameMedico.
     *
     * @param id the id of the exameMedico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exameMedico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exame-medicos/{id}")
    public ResponseEntity<ExameMedico> getExameMedico(@PathVariable Long id) {
        log.debug("REST request to get ExameMedico : {}", id);
        Optional<ExameMedico> exameMedico = exameMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exameMedico);
    }

    /**
     * {@code DELETE  /exame-medicos/:id} : delete the "id" exameMedico.
     *
     * @param id the id of the exameMedico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exame-medicos/{id}")
    public ResponseEntity<Void> deleteExameMedico(@PathVariable Long id) {
        log.debug("REST request to delete ExameMedico : {}", id);
        exameMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
