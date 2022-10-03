package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Colaborador;
import br.com.jhisolution.user.hunters.repository.ColaboradorRepository;
import br.com.jhisolution.user.hunters.service.ColaboradorService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Colaborador}.
 */
@RestController
@RequestMapping("/api")
public class ColaboradorResource {

    private final Logger log = LoggerFactory.getLogger(ColaboradorResource.class);

    private static final String ENTITY_NAME = "userColaborador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColaboradorService colaboradorService;

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorResource(ColaboradorService colaboradorService, ColaboradorRepository colaboradorRepository) {
        this.colaboradorService = colaboradorService;
        this.colaboradorRepository = colaboradorRepository;
    }

    /**
     * {@code POST  /colaboradors} : Create a new colaborador.
     *
     * @param colaborador the colaborador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colaborador, or with status {@code 400 (Bad Request)} if the colaborador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colaboradors")
    public ResponseEntity<Colaborador> createColaborador(@Valid @RequestBody Colaborador colaborador) throws URISyntaxException {
        log.debug("REST request to save Colaborador : {}", colaborador);
        if (colaborador.getId() != null) {
            throw new BadRequestAlertException("A new colaborador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colaborador result = colaboradorService.save(colaborador);
        return ResponseEntity
            .created(new URI("/api/colaboradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colaboradors/:id} : Updates an existing colaborador.
     *
     * @param id the id of the colaborador to save.
     * @param colaborador the colaborador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaborador,
     * or with status {@code 400 (Bad Request)} if the colaborador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colaborador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colaboradors/{id}")
    public ResponseEntity<Colaborador> updateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Colaborador colaborador
    ) throws URISyntaxException {
        log.debug("REST request to update Colaborador : {}, {}", id, colaborador);
        if (colaborador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaborador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Colaborador result = colaboradorService.update(colaborador);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colaborador.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colaboradors/:id} : Partial updates given fields of an existing colaborador, field will ignore if it is null
     *
     * @param id the id of the colaborador to save.
     * @param colaborador the colaborador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaborador,
     * or with status {@code 400 (Bad Request)} if the colaborador is not valid,
     * or with status {@code 404 (Not Found)} if the colaborador is not found,
     * or with status {@code 500 (Internal Server Error)} if the colaborador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colaboradors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Colaborador> partialUpdateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Colaborador colaborador
    ) throws URISyntaxException {
        log.debug("REST request to partial update Colaborador partially : {}, {}", id, colaborador);
        if (colaborador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaborador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Colaborador> result = colaboradorService.partialUpdate(colaborador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colaborador.getId().toString())
        );
    }

    /**
     * {@code GET  /colaboradors} : get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colaboradors in body.
     */
    @GetMapping("/colaboradors")
    public ResponseEntity<List<Colaborador>> getAllColaboradors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Colaboradors");
        Page<Colaborador> page = colaboradorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colaboradors/:id} : get the "id" colaborador.
     *
     * @param id the id of the colaborador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colaborador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colaboradors/{id}")
    public ResponseEntity<Colaborador> getColaborador(@PathVariable Long id) {
        log.debug("REST request to get Colaborador : {}", id);
        Optional<Colaborador> colaborador = colaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colaborador);
    }

    /**
     * {@code DELETE  /colaboradors/:id} : delete the "id" colaborador.
     *
     * @param id the id of the colaborador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colaboradors/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        log.debug("REST request to delete Colaborador : {}", id);
        colaboradorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
