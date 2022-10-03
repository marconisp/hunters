package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoPagar;
import br.com.jhisolution.user.hunters.repository.FotoPagarRepository;
import br.com.jhisolution.user.hunters.service.FotoPagarService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoPagar}.
 */
@RestController
@RequestMapping("/api")
public class FotoPagarResource {

    private final Logger log = LoggerFactory.getLogger(FotoPagarResource.class);

    private static final String ENTITY_NAME = "fotoFotoPagar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoPagarService fotoPagarService;

    private final FotoPagarRepository fotoPagarRepository;

    public FotoPagarResource(FotoPagarService fotoPagarService, FotoPagarRepository fotoPagarRepository) {
        this.fotoPagarService = fotoPagarService;
        this.fotoPagarRepository = fotoPagarRepository;
    }

    /**
     * {@code POST  /foto-pagars} : Create a new fotoPagar.
     *
     * @param fotoPagar the fotoPagar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoPagar, or with status {@code 400 (Bad Request)} if the fotoPagar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-pagars")
    public ResponseEntity<FotoPagar> createFotoPagar(@Valid @RequestBody FotoPagar fotoPagar) throws URISyntaxException {
        log.debug("REST request to save FotoPagar : {}", fotoPagar);
        if (fotoPagar.getId() != null) {
            throw new BadRequestAlertException("A new fotoPagar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoPagar result = fotoPagarService.save(fotoPagar);
        return ResponseEntity
            .created(new URI("/api/foto-pagars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-pagars/:id} : Updates an existing fotoPagar.
     *
     * @param id the id of the fotoPagar to save.
     * @param fotoPagar the fotoPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoPagar,
     * or with status {@code 400 (Bad Request)} if the fotoPagar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-pagars/{id}")
    public ResponseEntity<FotoPagar> updateFotoPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoPagar fotoPagar
    ) throws URISyntaxException {
        log.debug("REST request to update FotoPagar : {}, {}", id, fotoPagar);
        if (fotoPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoPagar result = fotoPagarService.update(fotoPagar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoPagar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-pagars/:id} : Partial updates given fields of an existing fotoPagar, field will ignore if it is null
     *
     * @param id the id of the fotoPagar to save.
     * @param fotoPagar the fotoPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoPagar,
     * or with status {@code 400 (Bad Request)} if the fotoPagar is not valid,
     * or with status {@code 404 (Not Found)} if the fotoPagar is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-pagars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoPagar> partialUpdateFotoPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoPagar fotoPagar
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoPagar partially : {}, {}", id, fotoPagar);
        if (fotoPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoPagar> result = fotoPagarService.partialUpdate(fotoPagar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoPagar.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-pagars} : get all the fotoPagars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoPagars in body.
     */
    @GetMapping("/foto-pagars")
    public ResponseEntity<List<FotoPagar>> getAllFotoPagars(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoPagars");
        Page<FotoPagar> page = fotoPagarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/foto-pagars/pagar/{id}")
    public ResponseEntity<List<FotoPagar>> getAllFotoPagarsByPagarId(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FotoPagars by pagar id");
        Page<FotoPagar> page = fotoPagarService.findAllByPagarId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-pagars/:id} : get the "id" fotoPagar.
     *
     * @param id the id of the fotoPagar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoPagar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-pagars/{id}")
    public ResponseEntity<FotoPagar> getFotoPagar(@PathVariable Long id) {
        log.debug("REST request to get FotoPagar : {}", id);
        Optional<FotoPagar> fotoPagar = fotoPagarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoPagar);
    }

    /**
     * {@code DELETE  /foto-pagars/:id} : delete the "id" fotoPagar.
     *
     * @param id the id of the fotoPagar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-pagars/{id}")
    public ResponseEntity<Void> deleteFotoPagar(@PathVariable Long id) {
        log.debug("REST request to delete FotoPagar : {}", id);
        fotoPagarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
