package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.PagarPara;
import br.com.jhisolution.user.hunters.repository.PagarParaRepository;
import br.com.jhisolution.user.hunters.service.PagarParaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.PagarPara}.
 */
@RestController
@RequestMapping("/api")
public class PagarParaResource {

    private final Logger log = LoggerFactory.getLogger(PagarParaResource.class);

    private static final String ENTITY_NAME = "controlePagarPara";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagarParaService pagarParaService;

    private final PagarParaRepository pagarParaRepository;

    public PagarParaResource(PagarParaService pagarParaService, PagarParaRepository pagarParaRepository) {
        this.pagarParaService = pagarParaService;
        this.pagarParaRepository = pagarParaRepository;
    }

    /**
     * {@code POST  /pagar-paras} : Create a new pagarPara.
     *
     * @param pagarPara the pagarPara to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagarPara, or with status {@code 400 (Bad Request)} if the pagarPara has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pagar-paras")
    public ResponseEntity<PagarPara> createPagarPara(@Valid @RequestBody PagarPara pagarPara) throws URISyntaxException {
        log.debug("REST request to save PagarPara : {}", pagarPara);
        if (pagarPara.getId() != null) {
            throw new BadRequestAlertException("A new pagarPara cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagarPara result = pagarParaService.save(pagarPara);
        return ResponseEntity
            .created(new URI("/api/pagar-paras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pagar-paras/:id} : Updates an existing pagarPara.
     *
     * @param id the id of the pagarPara to save.
     * @param pagarPara the pagarPara to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagarPara,
     * or with status {@code 400 (Bad Request)} if the pagarPara is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagarPara couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pagar-paras/{id}")
    public ResponseEntity<PagarPara> updatePagarPara(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PagarPara pagarPara
    ) throws URISyntaxException {
        log.debug("REST request to update PagarPara : {}, {}", id, pagarPara);
        if (pagarPara.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagarPara.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagarParaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PagarPara result = pagarParaService.update(pagarPara);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagarPara.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pagar-paras/:id} : Partial updates given fields of an existing pagarPara, field will ignore if it is null
     *
     * @param id the id of the pagarPara to save.
     * @param pagarPara the pagarPara to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagarPara,
     * or with status {@code 400 (Bad Request)} if the pagarPara is not valid,
     * or with status {@code 404 (Not Found)} if the pagarPara is not found,
     * or with status {@code 500 (Internal Server Error)} if the pagarPara couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pagar-paras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PagarPara> partialUpdatePagarPara(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PagarPara pagarPara
    ) throws URISyntaxException {
        log.debug("REST request to partial update PagarPara partially : {}, {}", id, pagarPara);
        if (pagarPara.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagarPara.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagarParaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PagarPara> result = pagarParaService.partialUpdate(pagarPara);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagarPara.getId().toString())
        );
    }

    /**
     * {@code GET  /pagar-paras} : get all the pagarParas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagarParas in body.
     */
    @GetMapping("/pagar-paras")
    public ResponseEntity<List<PagarPara>> getAllPagarParas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PagarParas");
        Page<PagarPara> page = pagarParaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pagar-paras/:id} : get the "id" pagarPara.
     *
     * @param id the id of the pagarPara to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagarPara, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pagar-paras/{id}")
    public ResponseEntity<PagarPara> getPagarPara(@PathVariable Long id) {
        log.debug("REST request to get PagarPara : {}", id);
        Optional<PagarPara> pagarPara = pagarParaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagarPara);
    }

    /**
     * {@code DELETE  /pagar-paras/:id} : delete the "id" pagarPara.
     *
     * @param id the id of the pagarPara to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pagar-paras/{id}")
    public ResponseEntity<Void> deletePagarPara(@PathVariable Long id) {
        log.debug("REST request to delete PagarPara : {}", id);
        pagarParaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
