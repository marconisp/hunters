package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoSaidaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.FotoSaidaEstoqueService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque}.
 */
@RestController
@RequestMapping("/api")
public class FotoSaidaEstoqueResource {

    private final Logger log = LoggerFactory.getLogger(FotoSaidaEstoqueResource.class);

    private static final String ENTITY_NAME = "fotoFotoSaidaEstoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoSaidaEstoqueService fotoSaidaEstoqueService;

    private final FotoSaidaEstoqueRepository fotoSaidaEstoqueRepository;

    public FotoSaidaEstoqueResource(
        FotoSaidaEstoqueService fotoSaidaEstoqueService,
        FotoSaidaEstoqueRepository fotoSaidaEstoqueRepository
    ) {
        this.fotoSaidaEstoqueService = fotoSaidaEstoqueService;
        this.fotoSaidaEstoqueRepository = fotoSaidaEstoqueRepository;
    }

    /**
     * {@code POST  /foto-saida-estoques} : Create a new fotoSaidaEstoque.
     *
     * @param fotoSaidaEstoque the fotoSaidaEstoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoSaidaEstoque, or with status {@code 400 (Bad Request)} if the fotoSaidaEstoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-saida-estoques")
    public ResponseEntity<FotoSaidaEstoque> createFotoSaidaEstoque(@Valid @RequestBody FotoSaidaEstoque fotoSaidaEstoque)
        throws URISyntaxException {
        log.debug("REST request to save FotoSaidaEstoque : {}", fotoSaidaEstoque);
        if (fotoSaidaEstoque.getId() != null) {
            throw new BadRequestAlertException("A new fotoSaidaEstoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoSaidaEstoque result = fotoSaidaEstoqueService.save(fotoSaidaEstoque);
        return ResponseEntity
            .created(new URI("/api/foto-saida-estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-saida-estoques/:id} : Updates an existing fotoSaidaEstoque.
     *
     * @param id the id of the fotoSaidaEstoque to save.
     * @param fotoSaidaEstoque the fotoSaidaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoSaidaEstoque,
     * or with status {@code 400 (Bad Request)} if the fotoSaidaEstoque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoSaidaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-saida-estoques/{id}")
    public ResponseEntity<FotoSaidaEstoque> updateFotoSaidaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoSaidaEstoque fotoSaidaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to update FotoSaidaEstoque : {}, {}", id, fotoSaidaEstoque);
        if (fotoSaidaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoSaidaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoSaidaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoSaidaEstoque result = fotoSaidaEstoqueService.update(fotoSaidaEstoque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoSaidaEstoque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-saida-estoques/:id} : Partial updates given fields of an existing fotoSaidaEstoque, field will ignore if it is null
     *
     * @param id the id of the fotoSaidaEstoque to save.
     * @param fotoSaidaEstoque the fotoSaidaEstoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoSaidaEstoque,
     * or with status {@code 400 (Bad Request)} if the fotoSaidaEstoque is not valid,
     * or with status {@code 404 (Not Found)} if the fotoSaidaEstoque is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoSaidaEstoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-saida-estoques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoSaidaEstoque> partialUpdateFotoSaidaEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoSaidaEstoque fotoSaidaEstoque
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoSaidaEstoque partially : {}, {}", id, fotoSaidaEstoque);
        if (fotoSaidaEstoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoSaidaEstoque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoSaidaEstoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoSaidaEstoque> result = fotoSaidaEstoqueService.partialUpdate(fotoSaidaEstoque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoSaidaEstoque.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-saida-estoques} : get all the fotoSaidaEstoques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoSaidaEstoques in body.
     */
    @GetMapping("/foto-saida-estoques")
    public ResponseEntity<List<FotoSaidaEstoque>> getAllFotoSaidaEstoques(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FotoSaidaEstoques");
        Page<FotoSaidaEstoque> page = fotoSaidaEstoqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-saida-estoques/:id} : get the "id" fotoSaidaEstoque.
     *
     * @param id the id of the fotoSaidaEstoque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoSaidaEstoque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-saida-estoques/{id}")
    public ResponseEntity<FotoSaidaEstoque> getFotoSaidaEstoque(@PathVariable Long id) {
        log.debug("REST request to get FotoSaidaEstoque : {}", id);
        Optional<FotoSaidaEstoque> fotoSaidaEstoque = fotoSaidaEstoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoSaidaEstoque);
    }

    /**
     * {@code DELETE  /foto-saida-estoques/:id} : delete the "id" fotoSaidaEstoque.
     *
     * @param id the id of the fotoSaidaEstoque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-saida-estoques/{id}")
    public ResponseEntity<Void> deleteFotoSaidaEstoque(@PathVariable Long id) {
        log.debug("REST request to delete FotoSaidaEstoque : {}", id);
        fotoSaidaEstoqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
