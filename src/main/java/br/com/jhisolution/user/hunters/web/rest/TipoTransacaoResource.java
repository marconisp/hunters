package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import br.com.jhisolution.user.hunters.repository.TipoTransacaoRepository;
import br.com.jhisolution.user.hunters.service.TipoTransacaoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoTransacao}.
 */
@RestController
@RequestMapping("/api")
public class TipoTransacaoResource {

    private final Logger log = LoggerFactory.getLogger(TipoTransacaoResource.class);

    private static final String ENTITY_NAME = "controleTipoTransacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoTransacaoService tipoTransacaoService;

    private final TipoTransacaoRepository tipoTransacaoRepository;

    public TipoTransacaoResource(TipoTransacaoService tipoTransacaoService, TipoTransacaoRepository tipoTransacaoRepository) {
        this.tipoTransacaoService = tipoTransacaoService;
        this.tipoTransacaoRepository = tipoTransacaoRepository;
    }

    /**
     * {@code POST  /tipo-transacaos} : Create a new tipoTransacao.
     *
     * @param tipoTransacao the tipoTransacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoTransacao, or with status {@code 400 (Bad Request)} if the tipoTransacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-transacaos")
    public ResponseEntity<TipoTransacao> createTipoTransacao(@Valid @RequestBody TipoTransacao tipoTransacao) throws URISyntaxException {
        log.debug("REST request to save TipoTransacao : {}", tipoTransacao);
        if (tipoTransacao.getId() != null) {
            throw new BadRequestAlertException("A new tipoTransacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoTransacao result = tipoTransacaoService.save(tipoTransacao);
        return ResponseEntity
            .created(new URI("/api/tipo-transacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-transacaos/:id} : Updates an existing tipoTransacao.
     *
     * @param id the id of the tipoTransacao to save.
     * @param tipoTransacao the tipoTransacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTransacao,
     * or with status {@code 400 (Bad Request)} if the tipoTransacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoTransacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-transacaos/{id}")
    public ResponseEntity<TipoTransacao> updateTipoTransacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoTransacao tipoTransacao
    ) throws URISyntaxException {
        log.debug("REST request to update TipoTransacao : {}, {}", id, tipoTransacao);
        if (tipoTransacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoTransacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoTransacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoTransacao result = tipoTransacaoService.update(tipoTransacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTransacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-transacaos/:id} : Partial updates given fields of an existing tipoTransacao, field will ignore if it is null
     *
     * @param id the id of the tipoTransacao to save.
     * @param tipoTransacao the tipoTransacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTransacao,
     * or with status {@code 400 (Bad Request)} if the tipoTransacao is not valid,
     * or with status {@code 404 (Not Found)} if the tipoTransacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoTransacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-transacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoTransacao> partialUpdateTipoTransacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoTransacao tipoTransacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoTransacao partially : {}, {}", id, tipoTransacao);
        if (tipoTransacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoTransacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoTransacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoTransacao> result = tipoTransacaoService.partialUpdate(tipoTransacao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTransacao.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-transacaos} : get all the tipoTransacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoTransacaos in body.
     */
    @GetMapping("/tipo-transacaos")
    public ResponseEntity<List<TipoTransacao>> getAllTipoTransacaos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoTransacaos");
        Page<TipoTransacao> page = tipoTransacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-transacaos/:id} : get the "id" tipoTransacao.
     *
     * @param id the id of the tipoTransacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoTransacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-transacaos/{id}")
    public ResponseEntity<TipoTransacao> getTipoTransacao(@PathVariable Long id) {
        log.debug("REST request to get TipoTransacao : {}", id);
        Optional<TipoTransacao> tipoTransacao = tipoTransacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoTransacao);
    }

    /**
     * {@code DELETE  /tipo-transacaos/:id} : delete the "id" tipoTransacao.
     *
     * @param id the id of the tipoTransacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-transacaos/{id}")
    public ResponseEntity<Void> deleteTipoTransacao(@PathVariable Long id) {
        log.debug("REST request to delete TipoTransacao : {}", id);
        tipoTransacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
