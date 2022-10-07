package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoDocumento;
import br.com.jhisolution.user.hunters.repository.TipoDocumentoRepository;
import br.com.jhisolution.user.hunters.service.TipoDocumentoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoDocumento}.
 */
@RestController
@RequestMapping("/api")
public class TipoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoResource.class);

    private static final String ENTITY_NAME = "configTipoDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDocumentoService tipoDocumentoService;

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoResource(TipoDocumentoService tipoDocumentoService, TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoService = tipoDocumentoService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    /**
     * {@code POST  /tipo-documentos} : Create a new tipoDocumento.
     *
     * @param tipoDocumento the tipoDocumento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDocumento, or with status {@code 400 (Bad Request)} if the tipoDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-documentos")
    public ResponseEntity<TipoDocumento> createTipoDocumento(@Valid @RequestBody TipoDocumento tipoDocumento) throws URISyntaxException {
        log.debug("REST request to save TipoDocumento : {}", tipoDocumento);
        if (tipoDocumento.getId() != null) {
            throw new BadRequestAlertException("A new tipoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDocumento result = tipoDocumentoService.save(tipoDocumento);
        return ResponseEntity
            .created(new URI("/api/tipo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-documentos/:id} : Updates an existing tipoDocumento.
     *
     * @param id the id of the tipoDocumento to save.
     * @param tipoDocumento the tipoDocumento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumento,
     * or with status {@code 400 (Bad Request)} if the tipoDocumento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-documentos/{id}")
    public ResponseEntity<TipoDocumento> updateTipoDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoDocumento tipoDocumento
    ) throws URISyntaxException {
        log.debug("REST request to update TipoDocumento : {}, {}", id, tipoDocumento);
        if (tipoDocumento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDocumento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoDocumento result = tipoDocumentoService.update(tipoDocumento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDocumento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-documentos/:id} : Partial updates given fields of an existing tipoDocumento, field will ignore if it is null
     *
     * @param id the id of the tipoDocumento to save.
     * @param tipoDocumento the tipoDocumento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumento,
     * or with status {@code 400 (Bad Request)} if the tipoDocumento is not valid,
     * or with status {@code 404 (Not Found)} if the tipoDocumento is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-documentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoDocumento> partialUpdateTipoDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoDocumento tipoDocumento
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoDocumento partially : {}, {}", id, tipoDocumento);
        if (tipoDocumento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDocumento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoDocumento> result = tipoDocumentoService.partialUpdate(tipoDocumento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDocumento.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-documentos} : get all the tipoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDocumentos in body.
     */
    @GetMapping("/tipo-documentos")
    public ResponseEntity<List<TipoDocumento>> getAllTipoDocumentos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoDocumentos");
        Page<TipoDocumento> page = tipoDocumentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-documentos/:id} : get the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDocumento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-documentos/{id}")
    public ResponseEntity<TipoDocumento> getTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to get TipoDocumento : {}", id);
        Optional<TipoDocumento> tipoDocumento = tipoDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDocumento);
    }

    /**
     * {@code DELETE  /tipo-documentos/:id} : delete the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-documentos/{id}")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDocumento : {}", id);
        tipoDocumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
