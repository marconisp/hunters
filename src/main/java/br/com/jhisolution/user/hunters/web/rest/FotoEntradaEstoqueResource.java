package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoEntradaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.FotoEntradaEstoqueService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque}.
 */
@RestController
@RequestMapping("/api")
public class FotoEntradaEstoqueResource {

    private final Logger log = LoggerFactory.getLogger(FotoEntradaEstoqueResource.class);

    private static final String ENTITY_NAME = "fotoFotoEntradaEstoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoEntradaEstoqueService fotoEntradaEstoqueService;

    private final FotoEntradaEstoqueRepository fotoEntradaEstoqueRepository;

    public FotoEntradaEstoqueResource(
        FotoEntradaEstoqueService fotoEntradaEstoqueService,
        FotoEntradaEstoqueRepository fotoEntradaEstoqueRepository
    ) {
        this.fotoEntradaEstoqueService = fotoEntradaEstoqueService;
        this.fotoEntradaEstoqueRepository = fotoEntradaEstoqueRepository;
    }

    /**
     * {@code POST  /foto-entrada-estoques} : Create a new fotoEntradaEstoque.
     *
     * @param fotoEntradaEstoque the fotoEntradaEstoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoEntradaEstoque, or with status {@code 400 (Bad Request)} if the fotoEntradaEstoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-entrada-estoques")
    public ResponseEntity<FotoEntradaEstoque> createFotoEntradaEstoque(@Valid @RequestBody FotoEntradaEstoque fotoEntradaEstoque)
        throws URISyntaxException {
        log.debug("REST request to save FotoEntradaEstoque : {}", fotoEntradaEstoque);
        if (fotoEntradaEstoque.getId() != null) {
            throw new BadRequestAlertException("A new fotoEntradaEstoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoEntradaEstoque result = fotoEntradaEstoqueService.save(fotoEntradaEstoque);
        return ResponseEntity
            .created(new URI("/api/foto-entrada-estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-entrada-estoques/:id} : Updates an existing fotoEntradaEstoque.
     *
     * @param id the id of the fotoEntradaEstoque to save.
     * @param fotoEntradaEstoque the fotoEntradaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoEntradaEstoque,
     * or with status {@code 400 (Bad Request)} if the fotoEntradaEstoque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoEntradaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-entrada-estoques/{id}")
    public ResponseEntity<FotoEntradaEstoque> updateFotoEntradaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoEntradaEstoque fotoEntradaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to update FotoEntradaEstoque : {}, {}", id, fotoEntradaEstoque);
        if (fotoEntradaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoEntradaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoEntradaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoEntradaEstoque result = fotoEntradaEstoqueService.update(fotoEntradaEstoque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoEntradaEstoque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-entrada-estoques/:id} : Partial updates given fields of an existing fotoEntradaEstoque, field will ignore if it is null
     *
     * @param id the id of the fotoEntradaEstoque to save.
     * @param fotoEntradaEstoque the fotoEntradaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoEntradaEstoque,
     * or with status {@code 400 (Bad Request)} if the fotoEntradaEstoque is not valid,
     * or with status {@code 404 (Not Found)} if the fotoEntradaEstoque is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoEntradaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-entrada-estoques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoEntradaEstoque> partialUpdateFotoEntradaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoEntradaEstoque fotoEntradaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoEntradaEstoque partially : {}, {}", id, fotoEntradaEstoque);
        if (fotoEntradaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoEntradaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoEntradaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoEntradaEstoque> result = fotoEntradaEstoqueService.partialUpdate(fotoEntradaEstoque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoEntradaEstoque.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-entrada-estoques} : get all the fotoEntradaEstoques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoEntradaEstoques in body.
     */
    @GetMapping("/foto-entrada-estoques")
    public ResponseEntity<List<FotoEntradaEstoque>> getAllFotoEntradaEstoques(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FotoEntradaEstoques");
        Page<FotoEntradaEstoque> page = fotoEntradaEstoqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-entrada-estoques/:id} : get the "id" fotoEntradaEstoque.
     *
     * @param id the id of the fotoEntradaEstoque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoEntradaEstoque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-entrada-estoques/{id}")
    public ResponseEntity<FotoEntradaEstoque> getFotoEntradaEstoque(@PathVariable Long id) {
        log.debug("REST request to get FotoEntradaEstoque : {}", id);
        Optional<FotoEntradaEstoque> fotoEntradaEstoque = fotoEntradaEstoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoEntradaEstoque);
    }

    /**
     * {@code DELETE  /foto-entrada-estoques/:id} : delete the "id" fotoEntradaEstoque.
     *
     * @param id the id of the fotoEntradaEstoque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-entrada-estoques/{id}")
    public ResponseEntity<Void> deleteFotoEntradaEstoque(@PathVariable Long id) {
        log.debug("REST request to delete FotoEntradaEstoque : {}", id);
        fotoEntradaEstoqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
