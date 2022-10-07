package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.SaidaEstoque;
import br.com.jhisolution.user.hunters.repository.SaidaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.SaidaEstoqueService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.SaidaEstoque}.
 */
@RestController
@RequestMapping("/api")
public class SaidaEstoqueResource {

    private final Logger log = LoggerFactory.getLogger(SaidaEstoqueResource.class);

    private static final String ENTITY_NAME = "controleSaidaEstoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaidaEstoqueService saidaEstoqueService;

    private final SaidaEstoqueRepository saidaEstoqueRepository;

    public SaidaEstoqueResource(SaidaEstoqueService saidaEstoqueService, SaidaEstoqueRepository saidaEstoqueRepository) {
        this.saidaEstoqueService = saidaEstoqueService;
        this.saidaEstoqueRepository = saidaEstoqueRepository;
    }

    /**
     * {@code POST  /saida-estoques} : Create a new saidaEstoque.
     *
     * @param saidaEstoque the saidaEstoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saidaEstoque, or with status {@code 400 (Bad Request)} if the saidaEstoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saida-estoques")
    public ResponseEntity<SaidaEstoque> createSaidaEstoque(@Valid @RequestBody SaidaEstoque saidaEstoque) throws URISyntaxException {
        log.debug("REST request to save SaidaEstoque : {}", saidaEstoque);
        if (saidaEstoque.getId() != null) {
            throw new BadRequestAlertException("A new saidaEstoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaidaEstoque result = saidaEstoqueService.save(saidaEstoque);
        return ResponseEntity
            .created(new URI("/api/saida-estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saida-estoques/:id} : Updates an existing saidaEstoque.
     *
     * @param id the id of the saidaEstoque to save.
     * @param saidaEstoque the saidaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saidaEstoque,
     * or with status {@code 400 (Bad Request)} if the saidaEstoque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saidaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saida-estoques/{id}")
    public ResponseEntity<SaidaEstoque> updateSaidaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SaidaEstoque saidaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to update SaidaEstoque : {}, {}", id, saidaEstoque);
        if (saidaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saidaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saidaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaidaEstoque result = saidaEstoqueService.update(saidaEstoque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saidaEstoque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /saida-estoques/:id} : Partial updates given fields of an existing saidaEstoque, field will ignore if it is null
     *
     * @param id the id of the saidaEstoque to save.
     * @param saidaEstoque the saidaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saidaEstoque,
     * or with status {@code 400 (Bad Request)} if the saidaEstoque is not valid,
     * or with status {@code 404 (Not Found)} if the saidaEstoque is not found,
     * or with status {@code 500 (Internal Server Error)} if the saidaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/saida-estoques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaidaEstoque> partialUpdateSaidaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SaidaEstoque saidaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaidaEstoque partially : {}, {}", id, saidaEstoque);
        if (saidaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saidaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saidaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaidaEstoque> result = saidaEstoqueService.partialUpdate(saidaEstoque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saidaEstoque.getId().toString())
        );
    }

    /**
     * {@code GET  /saida-estoques} : get all the saidaEstoques.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saidaEstoques in body.
     */
    @GetMapping("/saida-estoques")
    public ResponseEntity<List<SaidaEstoque>> getAllSaidaEstoques(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of SaidaEstoques");
        Page<SaidaEstoque> page;
        if (eagerload) {
            page = saidaEstoqueService.findAllWithEagerRelationships(pageable);
        } else {
            page = saidaEstoqueService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /saida-estoques/:id} : get the "id" saidaEstoque.
     *
     * @param id the id of the saidaEstoque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saidaEstoque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saida-estoques/{id}")
    public ResponseEntity<SaidaEstoque> getSaidaEstoque(@PathVariable Long id) {
        log.debug("REST request to get SaidaEstoque : {}", id);
        Optional<SaidaEstoque> saidaEstoque = saidaEstoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saidaEstoque);
    }

    /**
     * {@code DELETE  /saida-estoques/:id} : delete the "id" saidaEstoque.
     *
     * @param id the id of the saidaEstoque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saida-estoques/{id}")
    public ResponseEntity<Void> deleteSaidaEstoque(@PathVariable Long id) {
        log.debug("REST request to delete SaidaEstoque : {}", id);
        saidaEstoqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
