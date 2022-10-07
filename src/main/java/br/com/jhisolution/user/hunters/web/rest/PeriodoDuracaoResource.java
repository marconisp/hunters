package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.PeriodoDuracao;
import br.com.jhisolution.user.hunters.repository.PeriodoDuracaoRepository;
import br.com.jhisolution.user.hunters.service.PeriodoDuracaoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.PeriodoDuracao}.
 */
@RestController
@RequestMapping("/api")
public class PeriodoDuracaoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoDuracaoResource.class);

    private static final String ENTITY_NAME = "configPeriodoDuracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoDuracaoService periodoDuracaoService;

    private final PeriodoDuracaoRepository periodoDuracaoRepository;

    public PeriodoDuracaoResource(PeriodoDuracaoService periodoDuracaoService, PeriodoDuracaoRepository periodoDuracaoRepository) {
        this.periodoDuracaoService = periodoDuracaoService;
        this.periodoDuracaoRepository = periodoDuracaoRepository;
    }

    /**
     * {@code POST  /periodo-duracaos} : Create a new periodoDuracao.
     *
     * @param periodoDuracao the periodoDuracao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoDuracao, or with status {@code 400 (Bad Request)} if the periodoDuracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodo-duracaos")
    public ResponseEntity<PeriodoDuracao> createPeriodoDuracao(@Valid @RequestBody PeriodoDuracao periodoDuracao)
        throws URISyntaxException {
        log.debug("REST request to save PeriodoDuracao : {}", periodoDuracao);
        if (periodoDuracao.getId() != null) {
            throw new BadRequestAlertException("A new periodoDuracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoDuracao result = periodoDuracaoService.save(periodoDuracao);
        return ResponseEntity
            .created(new URI("/api/periodo-duracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodo-duracaos/:id} : Updates an existing periodoDuracao.
     *
     * @param id the id of the periodoDuracao to save.
     * @param periodoDuracao the periodoDuracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoDuracao,
     * or with status {@code 400 (Bad Request)} if the periodoDuracao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoDuracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodo-duracaos/{id}")
    public ResponseEntity<PeriodoDuracao> updatePeriodoDuracao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeriodoDuracao periodoDuracao
    ) throws URISyntaxException {
        log.debug("REST request to update PeriodoDuracao : {}, {}", id, periodoDuracao);
        if (periodoDuracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoDuracao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoDuracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeriodoDuracao result = periodoDuracaoService.update(periodoDuracao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoDuracao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodo-duracaos/:id} : Partial updates given fields of an existing periodoDuracao, field will ignore if it is null
     *
     * @param id the id of the periodoDuracao to save.
     * @param periodoDuracao the periodoDuracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoDuracao,
     * or with status {@code 400 (Bad Request)} if the periodoDuracao is not valid,
     * or with status {@code 404 (Not Found)} if the periodoDuracao is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoDuracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodo-duracaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoDuracao> partialUpdatePeriodoDuracao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeriodoDuracao periodoDuracao
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeriodoDuracao partially : {}, {}", id, periodoDuracao);
        if (periodoDuracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoDuracao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoDuracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoDuracao> result = periodoDuracaoService.partialUpdate(periodoDuracao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoDuracao.getId().toString())
        );
    }

    /**
     * {@code GET  /periodo-duracaos} : get all the periodoDuracaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodoDuracaos in body.
     */
    @GetMapping("/periodo-duracaos")
    public ResponseEntity<List<PeriodoDuracao>> getAllPeriodoDuracaos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PeriodoDuracaos");
        Page<PeriodoDuracao> page = periodoDuracaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodo-duracaos/:id} : get the "id" periodoDuracao.
     *
     * @param id the id of the periodoDuracao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoDuracao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodo-duracaos/{id}")
    public ResponseEntity<PeriodoDuracao> getPeriodoDuracao(@PathVariable Long id) {
        log.debug("REST request to get PeriodoDuracao : {}", id);
        Optional<PeriodoDuracao> periodoDuracao = periodoDuracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoDuracao);
    }

    /**
     * {@code DELETE  /periodo-duracaos/:id} : delete the "id" periodoDuracao.
     *
     * @param id the id of the periodoDuracao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodo-duracaos/{id}")
    public ResponseEntity<Void> deletePeriodoDuracao(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoDuracao : {}", id);
        periodoDuracaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
