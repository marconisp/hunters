package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import br.com.jhisolution.user.hunters.repository.TipoPessoaRepository;
import br.com.jhisolution.user.hunters.service.TipoPessoaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoPessoa}.
 */
@RestController
@RequestMapping("/api")
public class TipoPessoaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPessoaResource.class);

    private static final String ENTITY_NAME = "configTipoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPessoaService tipoPessoaService;

    private final TipoPessoaRepository tipoPessoaRepository;

    public TipoPessoaResource(TipoPessoaService tipoPessoaService, TipoPessoaRepository tipoPessoaRepository) {
        this.tipoPessoaService = tipoPessoaService;
        this.tipoPessoaRepository = tipoPessoaRepository;
    }

    /**
     * {@code POST  /tipo-pessoas} : Create a new tipoPessoa.
     *
     * @param tipoPessoa the tipoPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPessoa, or with status {@code 400 (Bad Request)} if the tipoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-pessoas")
    public ResponseEntity<TipoPessoa> createTipoPessoa(@Valid @RequestBody TipoPessoa tipoPessoa) throws URISyntaxException {
        log.debug("REST request to save TipoPessoa : {}", tipoPessoa);
        if (tipoPessoa.getId() != null) {
            throw new BadRequestAlertException("A new tipoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPessoa result = tipoPessoaService.save(tipoPessoa);
        return ResponseEntity
            .created(new URI("/api/tipo-pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-pessoas/:id} : Updates an existing tipoPessoa.
     *
     * @param id the id of the tipoPessoa to save.
     * @param tipoPessoa the tipoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPessoa,
     * or with status {@code 400 (Bad Request)} if the tipoPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-pessoas/{id}")
    public ResponseEntity<TipoPessoa> updateTipoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoPessoa tipoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update TipoPessoa : {}, {}", id, tipoPessoa);
        if (tipoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoPessoa result = tipoPessoaService.update(tipoPessoa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPessoa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-pessoas/:id} : Partial updates given fields of an existing tipoPessoa, field will ignore if it is null
     *
     * @param id the id of the tipoPessoa to save.
     * @param tipoPessoa the tipoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPessoa,
     * or with status {@code 400 (Bad Request)} if the tipoPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the tipoPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-pessoas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoPessoa> partialUpdateTipoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoPessoa tipoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoPessoa partially : {}, {}", id, tipoPessoa);
        if (tipoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoPessoa> result = tipoPessoaService.partialUpdate(tipoPessoa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-pessoas} : get all the tipoPessoas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPessoas in body.
     */
    @GetMapping("/tipo-pessoas")
    public ResponseEntity<List<TipoPessoa>> getAllTipoPessoas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoPessoas");
        Page<TipoPessoa> page = tipoPessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-pessoas/:id} : get the "id" tipoPessoa.
     *
     * @param id the id of the tipoPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-pessoas/{id}")
    public ResponseEntity<TipoPessoa> getTipoPessoa(@PathVariable Long id) {
        log.debug("REST request to get TipoPessoa : {}", id);
        Optional<TipoPessoa> tipoPessoa = tipoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPessoa);
    }

    /**
     * {@code DELETE  /tipo-pessoas/:id} : delete the "id" tipoPessoa.
     *
     * @param id the id of the tipoPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-pessoas/{id}")
    public ResponseEntity<Void> deleteTipoPessoa(@PathVariable Long id) {
        log.debug("REST request to delete TipoPessoa : {}", id);
        tipoPessoaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
