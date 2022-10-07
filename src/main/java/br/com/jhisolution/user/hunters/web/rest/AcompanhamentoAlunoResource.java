package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno;
import br.com.jhisolution.user.hunters.repository.AcompanhamentoAlunoRepository;
import br.com.jhisolution.user.hunters.service.AcompanhamentoAlunoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno}.
 */
@RestController
@RequestMapping("/api")
public class AcompanhamentoAlunoResource {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoAlunoResource.class);

    private static final String ENTITY_NAME = "matriculaAcompanhamentoAluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcompanhamentoAlunoService acompanhamentoAlunoService;

    private final AcompanhamentoAlunoRepository acompanhamentoAlunoRepository;

    public AcompanhamentoAlunoResource(
        AcompanhamentoAlunoService acompanhamentoAlunoService,
        AcompanhamentoAlunoRepository acompanhamentoAlunoRepository
    ) {
        this.acompanhamentoAlunoService = acompanhamentoAlunoService;
        this.acompanhamentoAlunoRepository = acompanhamentoAlunoRepository;
    }

    /**
     * {@code POST  /acompanhamento-alunos} : Create a new acompanhamentoAluno.
     *
     * @param acompanhamentoAluno the acompanhamentoAluno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acompanhamentoAluno, or with status {@code 400 (Bad Request)} if the acompanhamentoAluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acompanhamento-alunos")
    public ResponseEntity<AcompanhamentoAluno> createAcompanhamentoAluno(@Valid @RequestBody AcompanhamentoAluno acompanhamentoAluno)
        throws URISyntaxException {
        log.debug("REST request to save AcompanhamentoAluno : {}", acompanhamentoAluno);
        if (acompanhamentoAluno.getId() != null) {
            throw new BadRequestAlertException("A new acompanhamentoAluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcompanhamentoAluno result = acompanhamentoAlunoService.save(acompanhamentoAluno);
        return ResponseEntity
            .created(new URI("/api/acompanhamento-alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acompanhamento-alunos/:id} : Updates an existing acompanhamentoAluno.
     *
     * @param id the id of the acompanhamentoAluno to save.
     * @param acompanhamentoAluno the acompanhamentoAluno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acompanhamentoAluno,
     * or with status {@code 400 (Bad Request)} if the acompanhamentoAluno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acompanhamentoAluno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acompanhamento-alunos/{id}")
    public ResponseEntity<AcompanhamentoAluno> updateAcompanhamentoAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcompanhamentoAluno acompanhamentoAluno
    ) throws URISyntaxException {
        log.debug("REST request to update AcompanhamentoAluno : {}, {}", id, acompanhamentoAluno);
        if (acompanhamentoAluno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acompanhamentoAluno.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acompanhamentoAlunoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcompanhamentoAluno result = acompanhamentoAlunoService.update(acompanhamentoAluno);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acompanhamentoAluno.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acompanhamento-alunos/:id} : Partial updates given fields of an existing acompanhamentoAluno, field will ignore if it is null
     *
     * @param id the id of the acompanhamentoAluno to save.
     * @param acompanhamentoAluno the acompanhamentoAluno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acompanhamentoAluno,
     * or with status {@code 400 (Bad Request)} if the acompanhamentoAluno is not valid,
     * or with status {@code 404 (Not Found)} if the acompanhamentoAluno is not found,
     * or with status {@code 500 (Internal Server Error)} if the acompanhamentoAluno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acompanhamento-alunos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcompanhamentoAluno> partialUpdateAcompanhamentoAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcompanhamentoAluno acompanhamentoAluno
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcompanhamentoAluno partially : {}, {}", id, acompanhamentoAluno);
        if (acompanhamentoAluno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acompanhamentoAluno.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acompanhamentoAlunoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcompanhamentoAluno> result = acompanhamentoAlunoService.partialUpdate(acompanhamentoAluno);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acompanhamentoAluno.getId().toString())
        );
    }

    /**
     * {@code GET  /acompanhamento-alunos} : get all the acompanhamentoAlunos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acompanhamentoAlunos in body.
     */
    @GetMapping("/acompanhamento-alunos")
    public ResponseEntity<List<AcompanhamentoAluno>> getAllAcompanhamentoAlunos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AcompanhamentoAlunos");
        Page<AcompanhamentoAluno> page = acompanhamentoAlunoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acompanhamento-alunos/:id} : get the "id" acompanhamentoAluno.
     *
     * @param id the id of the acompanhamentoAluno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acompanhamentoAluno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acompanhamento-alunos/{id}")
    public ResponseEntity<AcompanhamentoAluno> getAcompanhamentoAluno(@PathVariable Long id) {
        log.debug("REST request to get AcompanhamentoAluno : {}", id);
        Optional<AcompanhamentoAluno> acompanhamentoAluno = acompanhamentoAlunoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acompanhamentoAluno);
    }

    /**
     * {@code DELETE  /acompanhamento-alunos/:id} : delete the "id" acompanhamentoAluno.
     *
     * @param id the id of the acompanhamentoAluno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acompanhamento-alunos/{id}")
    public ResponseEntity<Void> deleteAcompanhamentoAluno(@PathVariable Long id) {
        log.debug("REST request to delete AcompanhamentoAluno : {}", id);
        acompanhamentoAlunoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
