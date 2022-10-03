package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.SubGrupoProduto;
import br.com.jhisolution.user.hunters.repository.SubGrupoProdutoRepository;
import br.com.jhisolution.user.hunters.service.SubGrupoProdutoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.SubGrupoProduto}.
 */
@RestController
@RequestMapping("/api")
public class SubGrupoProdutoResource {

    private final Logger log = LoggerFactory.getLogger(SubGrupoProdutoResource.class);

    private static final String ENTITY_NAME = "controleSubGrupoProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubGrupoProdutoService subGrupoProdutoService;

    private final SubGrupoProdutoRepository subGrupoProdutoRepository;

    public SubGrupoProdutoResource(SubGrupoProdutoService subGrupoProdutoService, SubGrupoProdutoRepository subGrupoProdutoRepository) {
        this.subGrupoProdutoService = subGrupoProdutoService;
        this.subGrupoProdutoRepository = subGrupoProdutoRepository;
    }

    /**
     * {@code POST  /sub-grupo-produtos} : Create a new subGrupoProduto.
     *
     * @param subGrupoProduto the subGrupoProduto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subGrupoProduto, or with status {@code 400 (Bad Request)} if the subGrupoProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-grupo-produtos")
    public ResponseEntity<SubGrupoProduto> createSubGrupoProduto(@Valid @RequestBody SubGrupoProduto subGrupoProduto)
        throws URISyntaxException {
        log.debug("REST request to save SubGrupoProduto : {}", subGrupoProduto);
        if (subGrupoProduto.getId() != null) {
            throw new BadRequestAlertException("A new subGrupoProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubGrupoProduto result = subGrupoProdutoService.save(subGrupoProduto);
        return ResponseEntity
            .created(new URI("/api/sub-grupo-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-grupo-produtos/:id} : Updates an existing subGrupoProduto.
     *
     * @param id the id of the subGrupoProduto to save.
     * @param subGrupoProduto the subGrupoProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subGrupoProduto,
     * or with status {@code 400 (Bad Request)} if the subGrupoProduto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subGrupoProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-grupo-produtos/{id}")
    public ResponseEntity<SubGrupoProduto> updateSubGrupoProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubGrupoProduto subGrupoProduto
    ) throws URISyntaxException {
        log.debug("REST request to update SubGrupoProduto : {}, {}", id, subGrupoProduto);
        if (subGrupoProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subGrupoProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subGrupoProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubGrupoProduto result = subGrupoProdutoService.update(subGrupoProduto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subGrupoProduto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-grupo-produtos/:id} : Partial updates given fields of an existing subGrupoProduto, field will ignore if it is null
     *
     * @param id the id of the subGrupoProduto to save.
     * @param subGrupoProduto the subGrupoProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subGrupoProduto,
     * or with status {@code 400 (Bad Request)} if the subGrupoProduto is not valid,
     * or with status {@code 404 (Not Found)} if the subGrupoProduto is not found,
     * or with status {@code 500 (Internal Server Error)} if the subGrupoProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-grupo-produtos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubGrupoProduto> partialUpdateSubGrupoProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubGrupoProduto subGrupoProduto
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubGrupoProduto partially : {}, {}", id, subGrupoProduto);
        if (subGrupoProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subGrupoProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subGrupoProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubGrupoProduto> result = subGrupoProdutoService.partialUpdate(subGrupoProduto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subGrupoProduto.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-grupo-produtos} : get all the subGrupoProdutos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subGrupoProdutos in body.
     */
    @GetMapping("/sub-grupo-produtos")
    public ResponseEntity<List<SubGrupoProduto>> getAllSubGrupoProdutos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SubGrupoProdutos");
        Page<SubGrupoProduto> page = subGrupoProdutoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-grupo-produtos/:id} : get the "id" subGrupoProduto.
     *
     * @param id the id of the subGrupoProduto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subGrupoProduto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-grupo-produtos/{id}")
    public ResponseEntity<SubGrupoProduto> getSubGrupoProduto(@PathVariable Long id) {
        log.debug("REST request to get SubGrupoProduto : {}", id);
        Optional<SubGrupoProduto> subGrupoProduto = subGrupoProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subGrupoProduto);
    }

    /**
     * {@code DELETE  /sub-grupo-produtos/:id} : delete the "id" subGrupoProduto.
     *
     * @param id the id of the subGrupoProduto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-grupo-produtos/{id}")
    public ResponseEntity<Void> deleteSubGrupoProduto(@PathVariable Long id) {
        log.debug("REST request to delete SubGrupoProduto : {}", id);
        subGrupoProdutoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
