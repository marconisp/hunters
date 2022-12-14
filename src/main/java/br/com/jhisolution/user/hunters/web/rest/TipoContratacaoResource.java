package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoContratacao;
import br.com.jhisolution.user.hunters.repository.TipoContratacaoRepository;
import br.com.jhisolution.user.hunters.service.TipoContratacaoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoContratacao}.
 */
@RestController
@RequestMapping("/api")
public class TipoContratacaoResource {

    private final Logger log = LoggerFactory.getLogger(TipoContratacaoResource.class);

    private static final String ENTITY_NAME = "controleTipoContratacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoContratacaoService tipoContratacaoService;

    private final TipoContratacaoRepository tipoContratacaoRepository;

    public TipoContratacaoResource(TipoContratacaoService tipoContratacaoService, TipoContratacaoRepository tipoContratacaoRepository) {
        this.tipoContratacaoService = tipoContratacaoService;
        this.tipoContratacaoRepository = tipoContratacaoRepository;
    }

    /**
     * {@code POST  /tipo-contratacaos} : Create a new tipoContratacao.
     *
     * @param tipoContratacao the tipoContratacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoContratacao, or with status {@code 400 (Bad Request)} if the tipoContratacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-contratacaos")
    public ResponseEntity<TipoContratacao> createTipoContratacao(@Valid @RequestBody TipoContratacao tipoContratacao)
        throws URISyntaxException {
        log.debug("REST request to save TipoContratacao : {}", tipoContratacao);
        if (tipoContratacao.getId() != null) {
            throw new BadRequestAlertException("A new tipoContratacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoContratacao result = tipoContratacaoService.save(tipoContratacao);
        return ResponseEntity
            .created(new URI("/api/tipo-contratacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-contratacaos/:id} : Updates an existing tipoContratacao.
     *
     * @param id the id of the tipoContratacao to save.
     * @param tipoContratacao the tipoContratacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoContratacao,
     * or with status {@code 400 (Bad Request)} if the tipoContratacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoContratacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-contratacaos/{id}")
    public ResponseEntity<TipoContratacao> updateTipoContratacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoContratacao tipoContratacao
    ) throws URISyntaxException {
        log.debug("REST request to update TipoContratacao : {}, {}", id, tipoContratacao);
        if (tipoContratacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoContratacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoContratacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoContratacao result = tipoContratacaoService.update(tipoContratacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoContratacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-contratacaos/:id} : Partial updates given fields of an existing tipoContratacao, field will ignore if it is null
     *
     * @param id the id of the tipoContratacao to save.
     * @param tipoContratacao the tipoContratacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoContratacao,
     * or with status {@code 400 (Bad Request)} if the tipoContratacao is not valid,
     * or with status {@code 404 (Not Found)} if the tipoContratacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoContratacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-contratacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoContratacao> partialUpdateTipoContratacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoContratacao tipoContratacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoContratacao partially : {}, {}", id, tipoContratacao);
        if (tipoContratacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoContratacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoContratacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoContratacao> result = tipoContratacaoService.partialUpdate(tipoContratacao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoContratacao.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-contratacaos} : get all the tipoContratacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoContratacaos in body.
     */
    @GetMapping("/tipo-contratacaos")
    public ResponseEntity<List<TipoContratacao>> getAllTipoContratacaos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoContratacaos");
        Page<TipoContratacao> page = tipoContratacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-contratacaos/:id} : get the "id" tipoContratacao.
     *
     * @param id the id of the tipoContratacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoContratacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-contratacaos/{id}")
    public ResponseEntity<TipoContratacao> getTipoContratacao(@PathVariable Long id) {
        log.debug("REST request to get TipoContratacao : {}", id);
        Optional<TipoContratacao> tipoContratacao = tipoContratacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoContratacao);
    }

    /**
     * {@code DELETE  /tipo-contratacaos/:id} : delete the "id" tipoContratacao.
     *
     * @param id the id of the tipoContratacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-contratacaos/{id}")
    public ResponseEntity<Void> deleteTipoContratacao(@PathVariable Long id) {
        log.debug("REST request to delete TipoContratacao : {}", id);
        tipoContratacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
