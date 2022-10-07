package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.AgendaColaborador;
import br.com.jhisolution.user.hunters.repository.AgendaColaboradorRepository;
import br.com.jhisolution.user.hunters.service.AgendaColaboradorService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.AgendaColaborador}.
 */
@RestController
@RequestMapping("/api")
public class AgendaColaboradorResource {

    private final Logger log = LoggerFactory.getLogger(AgendaColaboradorResource.class);

    private static final String ENTITY_NAME = "userAgendaColaborador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaColaboradorService agendaColaboradorService;

    private final AgendaColaboradorRepository agendaColaboradorRepository;

    public AgendaColaboradorResource(
        AgendaColaboradorService agendaColaboradorService,
        AgendaColaboradorRepository agendaColaboradorRepository
    ) {
        this.agendaColaboradorService = agendaColaboradorService;
        this.agendaColaboradorRepository = agendaColaboradorRepository;
    }

    /**
     * {@code POST  /agenda-colaboradors} : Create a new agendaColaborador.
     *
     * @param agendaColaborador the agendaColaborador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaColaborador, or with status {@code 400 (Bad Request)} if the agendaColaborador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agenda-colaboradors")
    public ResponseEntity<AgendaColaborador> createAgendaColaborador(@Valid @RequestBody AgendaColaborador agendaColaborador)
        throws URISyntaxException {
        log.debug("REST request to save AgendaColaborador : {}", agendaColaborador);
        if (agendaColaborador.getId() != null) {
            throw new BadRequestAlertException("A new agendaColaborador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendaColaborador result = agendaColaboradorService.save(agendaColaborador);
        return ResponseEntity
            .created(new URI("/api/agenda-colaboradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agenda-colaboradors/:id} : Updates an existing agendaColaborador.
     *
     * @param id the id of the agendaColaborador to save.
     * @param agendaColaborador the agendaColaborador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaColaborador,
     * or with status {@code 400 (Bad Request)} if the agendaColaborador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaColaborador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agenda-colaboradors/{id}")
    public ResponseEntity<AgendaColaborador> updateAgendaColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendaColaborador agendaColaborador
    ) throws URISyntaxException {
        log.debug("REST request to update AgendaColaborador : {}, {}", id, agendaColaborador);
        if (agendaColaborador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaColaborador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaColaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgendaColaborador result = agendaColaboradorService.update(agendaColaborador);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaColaborador.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /agenda-colaboradors/:id} : Partial updates given fields of an existing agendaColaborador, field will ignore if it is null
     *
     * @param id the id of the agendaColaborador to save.
     * @param agendaColaborador the agendaColaborador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaColaborador,
     * or with status {@code 400 (Bad Request)} if the agendaColaborador is not valid,
     * or with status {@code 404 (Not Found)} if the agendaColaborador is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendaColaborador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/agenda-colaboradors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendaColaborador> partialUpdateAgendaColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendaColaborador agendaColaborador
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgendaColaborador partially : {}, {}", id, agendaColaborador);
        if (agendaColaborador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaColaborador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaColaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendaColaborador> result = agendaColaboradorService.partialUpdate(agendaColaborador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaColaborador.getId().toString())
        );
    }

    /**
     * {@code GET  /agenda-colaboradors} : get all the agendaColaboradors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaColaboradors in body.
     */
    @GetMapping("/agenda-colaboradors")
    public ResponseEntity<List<AgendaColaborador>> getAllAgendaColaboradors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of AgendaColaboradors");
        Page<AgendaColaborador> page;
        if (eagerload) {
            page = agendaColaboradorService.findAllWithEagerRelationships(pageable);
        } else {
            page = agendaColaboradorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agenda-colaboradors/:id} : get the "id" agendaColaborador.
     *
     * @param id the id of the agendaColaborador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaColaborador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agenda-colaboradors/{id}")
    public ResponseEntity<AgendaColaborador> getAgendaColaborador(@PathVariable Long id) {
        log.debug("REST request to get AgendaColaborador : {}", id);
        Optional<AgendaColaborador> agendaColaborador = agendaColaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaColaborador);
    }

    /**
     * {@code DELETE  /agenda-colaboradors/:id} : delete the "id" agendaColaborador.
     *
     * @param id the id of the agendaColaborador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agenda-colaboradors/{id}")
    public ResponseEntity<Void> deleteAgendaColaborador(@PathVariable Long id) {
        log.debug("REST request to delete AgendaColaborador : {}", id);
        agendaColaboradorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
