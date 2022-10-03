package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoProduto;
import br.com.jhisolution.user.hunters.repository.FotoProdutoRepository;
import br.com.jhisolution.user.hunters.service.FotoProdutoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoProduto}.
 */
@RestController
@RequestMapping("/api")
public class FotoProdutoResource {

    private final Logger log = LoggerFactory.getLogger(FotoProdutoResource.class);

    private static final String ENTITY_NAME = "fotoFotoProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoProdutoService fotoProdutoService;

    private final FotoProdutoRepository fotoProdutoRepository;

    public FotoProdutoResource(FotoProdutoService fotoProdutoService, FotoProdutoRepository fotoProdutoRepository) {
        this.fotoProdutoService = fotoProdutoService;
        this.fotoProdutoRepository = fotoProdutoRepository;
    }

    /**
     * {@code POST  /foto-produtos} : Create a new fotoProduto.
     *
     * @param fotoProduto the fotoProduto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoProduto, or with status {@code 400 (Bad Request)} if the fotoProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-produtos")
    public ResponseEntity<FotoProduto> createFotoProduto(@Valid @RequestBody FotoProduto fotoProduto) throws URISyntaxException {
        log.debug("REST request to save FotoProduto : {}", fotoProduto);
        if (fotoProduto.getId() != null) {
            throw new BadRequestAlertException("A new fotoProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoProduto result = fotoProdutoService.save(fotoProduto);
        return ResponseEntity
            .created(new URI("/api/foto-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-produtos/:id} : Updates an existing fotoProduto.
     *
     * @param id the id of the fotoProduto to save.
     * @param fotoProduto the fotoProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoProduto,
     * or with status {@code 400 (Bad Request)} if the fotoProduto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-produtos/{id}")
    public ResponseEntity<FotoProduto> updateFotoProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoProduto fotoProduto
    ) throws URISyntaxException {
        log.debug("REST request to update FotoProduto : {}, {}", id, fotoProduto);
        if (fotoProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoProduto result = fotoProdutoService.update(fotoProduto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoProduto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-produtos/:id} : Partial updates given fields of an existing fotoProduto, field will ignore if it is null
     *
     * @param id the id of the fotoProduto to save.
     * @param fotoProduto the fotoProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoProduto,
     * or with status {@code 400 (Bad Request)} if the fotoProduto is not valid,
     * or with status {@code 404 (Not Found)} if the fotoProduto is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-produtos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoProduto> partialUpdateFotoProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoProduto fotoProduto
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoProduto partially : {}, {}", id, fotoProduto);
        if (fotoProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoProduto> result = fotoProdutoService.partialUpdate(fotoProduto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoProduto.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-produtos} : get all the fotoProdutos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoProdutos in body.
     */
    @GetMapping("/foto-produtos")
    public ResponseEntity<List<FotoProduto>> getAllFotoProdutos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoProdutos");
        Page<FotoProduto> page = fotoProdutoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-produtos/:id} : get the "id" fotoProduto.
     *
     * @param id the id of the fotoProduto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoProduto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-produtos/{id}")
    public ResponseEntity<FotoProduto> getFotoProduto(@PathVariable Long id) {
        log.debug("REST request to get FotoProduto : {}", id);
        Optional<FotoProduto> fotoProduto = fotoProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoProduto);
    }

    /**
     * {@code DELETE  /foto-produtos/:id} : delete the "id" fotoProduto.
     *
     * @param id the id of the fotoProduto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-produtos/{id}")
    public ResponseEntity<Void> deleteFotoProduto(@PathVariable Long id) {
        log.debug("REST request to delete FotoProduto : {}", id);
        fotoProdutoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
