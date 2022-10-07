package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.EntradaEstoque;
import br.com.jhisolution.user.hunters.repository.EntradaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.EntradaEstoqueService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.EntradaEstoque}.
 */
@RestController
@RequestMapping("/api")
public class EntradaEstoqueResource {

    private final Logger log = LoggerFactory.getLogger(EntradaEstoqueResource.class);

    private static final String ENTITY_NAME = "controleEntradaEstoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntradaEstoqueService entradaEstoqueService;

    private final EntradaEstoqueRepository entradaEstoqueRepository;

    public EntradaEstoqueResource(EntradaEstoqueService entradaEstoqueService, EntradaEstoqueRepository entradaEstoqueRepository) {
        this.entradaEstoqueService = entradaEstoqueService;
        this.entradaEstoqueRepository = entradaEstoqueRepository;
    }

    /**
     * {@code POST  /entrada-estoques} : Create a new entradaEstoque.
     *
     * @param entradaEstoque the entradaEstoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entradaEstoque, or with status {@code 400 (Bad Request)} if the entradaEstoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrada-estoques")
    public ResponseEntity<EntradaEstoque> createEntradaEstoque(@Valid @RequestBody EntradaEstoque entradaEstoque)
        throws URISyntaxException {
        log.debug("REST request to save EntradaEstoque : {}", entradaEstoque);
        if (entradaEstoque.getId() != null) {
            throw new BadRequestAlertException("A new entradaEstoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntradaEstoque result = entradaEstoqueService.save(entradaEstoque);
        return ResponseEntity
            .created(new URI("/api/entrada-estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrada-estoques/:id} : Updates an existing entradaEstoque.
     *
     * @param id the id of the entradaEstoque to save.
     * @param entradaEstoque the entradaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaEstoque,
     * or with status {@code 400 (Bad Request)} if the entradaEstoque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entradaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrada-estoques/{id}")
    public ResponseEntity<EntradaEstoque> updateEntradaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntradaEstoque entradaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to update EntradaEstoque : {}, {}", id, entradaEstoque);
        if (entradaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entradaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entradaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntradaEstoque result = entradaEstoqueService.update(entradaEstoque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entradaEstoque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entrada-estoques/:id} : Partial updates given fields of an existing entradaEstoque, field will ignore if it is null
     *
     * @param id the id of the entradaEstoque to save.
     * @param entradaEstoque the entradaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaEstoque,
     * or with status {@code 400 (Bad Request)} if the entradaEstoque is not valid,
     * or with status {@code 404 (Not Found)} if the entradaEstoque is not found,
     * or with status {@code 500 (Internal Server Error)} if the entradaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entrada-estoques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntradaEstoque> partialUpdateEntradaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntradaEstoque entradaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntradaEstoque partially : {}, {}", id, entradaEstoque);
        if (entradaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entradaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entradaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntradaEstoque> result = entradaEstoqueService.partialUpdate(entradaEstoque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entradaEstoque.getId().toString())
        );
    }

    /**
     * {@code GET  /entrada-estoques} : get all the entradaEstoques.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entradaEstoques in body.
     */
    @GetMapping("/entrada-estoques")
    public ResponseEntity<List<EntradaEstoque>> getAllEntradaEstoques(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of EntradaEstoques");
        Page<EntradaEstoque> page;
        if (eagerload) {
            page = entradaEstoqueService.findAllWithEagerRelationships(pageable);
        } else {
            page = entradaEstoqueService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrada-estoques/:id} : get the "id" entradaEstoque.
     *
     * @param id the id of the entradaEstoque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entradaEstoque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrada-estoques/{id}")
    public ResponseEntity<EntradaEstoque> getEntradaEstoque(@PathVariable Long id) {
        log.debug("REST request to get EntradaEstoque : {}", id);
        Optional<EntradaEstoque> entradaEstoque = entradaEstoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entradaEstoque);
    }

    /**
     * {@code DELETE  /entrada-estoques/:id} : delete the "id" entradaEstoque.
     *
     * @param id the id of the entradaEstoque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrada-estoques/{id}")
    public ResponseEntity<Void> deleteEntradaEstoque(@PathVariable Long id) {
        log.debug("REST request to delete EntradaEstoque : {}", id);
        entradaEstoqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
