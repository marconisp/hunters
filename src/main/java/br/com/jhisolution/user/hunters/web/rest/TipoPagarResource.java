package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoPagar;
import br.com.jhisolution.user.hunters.repository.TipoPagarRepository;
import br.com.jhisolution.user.hunters.service.TipoPagarService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoPagar}.
 */
@RestController
@RequestMapping("/api")
public class TipoPagarResource {

    private final Logger log = LoggerFactory.getLogger(TipoPagarResource.class);

    private static final String ENTITY_NAME = "controleTipoPagar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPagarService tipoPagarService;

    private final TipoPagarRepository tipoPagarRepository;

    public TipoPagarResource(TipoPagarService tipoPagarService, TipoPagarRepository tipoPagarRepository) {
        this.tipoPagarService = tipoPagarService;
        this.tipoPagarRepository = tipoPagarRepository;
    }

    /**
     * {@code POST  /tipo-pagars} : Create a new tipoPagar.
     *
     * @param tipoPagar the tipoPagar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPagar, or with status {@code 400 (Bad Request)} if the tipoPagar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-pagars")
    public ResponseEntity<TipoPagar> createTipoPagar(@Valid @RequestBody TipoPagar tipoPagar) throws URISyntaxException {
        log.debug("REST request to save TipoPagar : {}", tipoPagar);
        if (tipoPagar.getId() != null) {
            throw new BadRequestAlertException("A new tipoPagar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPagar result = tipoPagarService.save(tipoPagar);
        return ResponseEntity
            .created(new URI("/api/tipo-pagars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-pagars/:id} : Updates an existing tipoPagar.
     *
     * @param id the id of the tipoPagar to save.
     * @param tipoPagar the tipoPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPagar,
     * or with status {@code 400 (Bad Request)} if the tipoPagar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-pagars/{id}")
    public ResponseEntity<TipoPagar> updateTipoPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoPagar tipoPagar
    ) throws URISyntaxException {
        log.debug("REST request to update TipoPagar : {}, {}", id, tipoPagar);
        if (tipoPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoPagar result = tipoPagarService.update(tipoPagar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPagar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-pagars/:id} : Partial updates given fields of an existing tipoPagar, field will ignore if it is null
     *
     * @param id the id of the tipoPagar to save.
     * @param tipoPagar the tipoPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPagar,
     * or with status {@code 400 (Bad Request)} if the tipoPagar is not valid,
     * or with status {@code 404 (Not Found)} if the tipoPagar is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-pagars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoPagar> partialUpdateTipoPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoPagar tipoPagar
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoPagar partially : {}, {}", id, tipoPagar);
        if (tipoPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoPagar> result = tipoPagarService.partialUpdate(tipoPagar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPagar.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-pagars} : get all the tipoPagars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPagars in body.
     */
    @GetMapping("/tipo-pagars")
    public ResponseEntity<List<TipoPagar>> getAllTipoPagars(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoPagars");
        Page<TipoPagar> page = tipoPagarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-pagars/:id} : get the "id" tipoPagar.
     *
     * @param id the id of the tipoPagar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPagar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-pagars/{id}")
    public ResponseEntity<TipoPagar> getTipoPagar(@PathVariable Long id) {
        log.debug("REST request to get TipoPagar : {}", id);
        Optional<TipoPagar> tipoPagar = tipoPagarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPagar);
    }

    /**
     * {@code DELETE  /tipo-pagars/:id} : delete the "id" tipoPagar.
     *
     * @param id the id of the tipoPagar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-pagars/{id}")
    public ResponseEntity<Void> deleteTipoPagar(@PathVariable Long id) {
        log.debug("REST request to delete TipoPagar : {}", id);
        tipoPagarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
