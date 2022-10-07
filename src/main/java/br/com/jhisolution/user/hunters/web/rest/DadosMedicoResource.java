package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.DadosMedico;
import br.com.jhisolution.user.hunters.repository.DadosMedicoRepository;
import br.com.jhisolution.user.hunters.service.DadosMedicoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.DadosMedico}.
 */
@RestController
@RequestMapping("/api")
public class DadosMedicoResource {

    private final Logger log = LoggerFactory.getLogger(DadosMedicoResource.class);

    private static final String ENTITY_NAME = "matriculaDadosMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadosMedicoService dadosMedicoService;

    private final DadosMedicoRepository dadosMedicoRepository;

    public DadosMedicoResource(DadosMedicoService dadosMedicoService, DadosMedicoRepository dadosMedicoRepository) {
        this.dadosMedicoService = dadosMedicoService;
        this.dadosMedicoRepository = dadosMedicoRepository;
    }

    /**
     * {@code POST  /dados-medicos} : Create a new dadosMedico.
     *
     * @param dadosMedico the dadosMedico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadosMedico, or with status {@code 400 (Bad Request)} if the dadosMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dados-medicos")
    public ResponseEntity<DadosMedico> createDadosMedico(@Valid @RequestBody DadosMedico dadosMedico) throws URISyntaxException {
        log.debug("REST request to save DadosMedico : {}", dadosMedico);
        if (dadosMedico.getId() != null) {
            throw new BadRequestAlertException("A new dadosMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DadosMedico result = dadosMedicoService.save(dadosMedico);
        return ResponseEntity
            .created(new URI("/api/dados-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dados-medicos/:id} : Updates an existing dadosMedico.
     *
     * @param id the id of the dadosMedico to save.
     * @param dadosMedico the dadosMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosMedico,
     * or with status {@code 400 (Bad Request)} if the dadosMedico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadosMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dados-medicos/{id}")
    public ResponseEntity<DadosMedico> updateDadosMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DadosMedico dadosMedico
    ) throws URISyntaxException {
        log.debug("REST request to update DadosMedico : {}, {}", id, dadosMedico);
        if (dadosMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DadosMedico result = dadosMedicoService.update(dadosMedico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosMedico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dados-medicos/:id} : Partial updates given fields of an existing dadosMedico, field will ignore if it is null
     *
     * @param id the id of the dadosMedico to save.
     * @param dadosMedico the dadosMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosMedico,
     * or with status {@code 400 (Bad Request)} if the dadosMedico is not valid,
     * or with status {@code 404 (Not Found)} if the dadosMedico is not found,
     * or with status {@code 500 (Internal Server Error)} if the dadosMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dados-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DadosMedico> partialUpdateDadosMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DadosMedico dadosMedico
    ) throws URISyntaxException {
        log.debug("REST request to partial update DadosMedico partially : {}, {}", id, dadosMedico);
        if (dadosMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DadosMedico> result = dadosMedicoService.partialUpdate(dadosMedico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosMedico.getId().toString())
        );
    }

    /**
     * {@code GET  /dados-medicos} : get all the dadosMedicos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadosMedicos in body.
     */
    @GetMapping("/dados-medicos")
    public ResponseEntity<List<DadosMedico>> getAllDadosMedicos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DadosMedicos");
        Page<DadosMedico> page;
        if (eagerload) {
            page = dadosMedicoService.findAllWithEagerRelationships(pageable);
        } else {
            page = dadosMedicoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dados-medicos/:id} : get the "id" dadosMedico.
     *
     * @param id the id of the dadosMedico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadosMedico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dados-medicos/{id}")
    public ResponseEntity<DadosMedico> getDadosMedico(@PathVariable Long id) {
        log.debug("REST request to get DadosMedico : {}", id);
        Optional<DadosMedico> dadosMedico = dadosMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dadosMedico);
    }

    /**
     * {@code DELETE  /dados-medicos/:id} : delete the "id" dadosMedico.
     *
     * @param id the id of the dadosMedico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dados-medicos/{id}")
    public ResponseEntity<Void> deleteDadosMedico(@PathVariable Long id) {
        log.debug("REST request to delete DadosMedico : {}", id);
        dadosMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
