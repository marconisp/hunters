package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.EnderecoEvento;
import br.com.jhisolution.user.hunters.repository.EnderecoEventoRepository;
import br.com.jhisolution.user.hunters.service.EnderecoEventoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.EnderecoEvento}.
 */
@RestController
@RequestMapping("/api")
public class EnderecoEventoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoEventoResource.class);

    private static final String ENTITY_NAME = "eventoEnderecoEvento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoEventoService enderecoEventoService;

    private final EnderecoEventoRepository enderecoEventoRepository;

    public EnderecoEventoResource(EnderecoEventoService enderecoEventoService, EnderecoEventoRepository enderecoEventoRepository) {
        this.enderecoEventoService = enderecoEventoService;
        this.enderecoEventoRepository = enderecoEventoRepository;
    }

    /**
     * {@code POST  /endereco-eventos} : Create a new enderecoEvento.
     *
     * @param enderecoEvento the enderecoEvento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoEvento, or with status {@code 400 (Bad Request)} if the enderecoEvento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/endereco-eventos")
    public ResponseEntity<EnderecoEvento> createEnderecoEvento(@Valid @RequestBody EnderecoEvento enderecoEvento)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoEvento : {}", enderecoEvento);
        if (enderecoEvento.getId() != null) {
            throw new BadRequestAlertException("A new enderecoEvento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoEvento result = enderecoEventoService.save(enderecoEvento);
        return ResponseEntity
            .created(new URI("/api/endereco-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /endereco-eventos/:id} : Updates an existing enderecoEvento.
     *
     * @param id the id of the enderecoEvento to save.
     * @param enderecoEvento the enderecoEvento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEvento,
     * or with status {@code 400 (Bad Request)} if the enderecoEvento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEvento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/endereco-eventos/{id}")
    public ResponseEntity<EnderecoEvento> updateEnderecoEvento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoEvento enderecoEvento
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoEvento : {}, {}", id, enderecoEvento);
        if (enderecoEvento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEvento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEventoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnderecoEvento result = enderecoEventoService.update(enderecoEvento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEvento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /endereco-eventos/:id} : Partial updates given fields of an existing enderecoEvento, field will ignore if it is null
     *
     * @param id the id of the enderecoEvento to save.
     * @param enderecoEvento the enderecoEvento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEvento,
     * or with status {@code 400 (Bad Request)} if the enderecoEvento is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoEvento is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEvento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/endereco-eventos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoEvento> partialUpdateEnderecoEvento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoEvento enderecoEvento
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoEvento partially : {}, {}", id, enderecoEvento);
        if (enderecoEvento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEvento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEventoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoEvento> result = enderecoEventoService.partialUpdate(enderecoEvento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEvento.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-eventos} : get all the enderecoEventos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoEventos in body.
     */
    @GetMapping("/endereco-eventos")
    public ResponseEntity<List<EnderecoEvento>> getAllEnderecoEventos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EnderecoEventos");
        Page<EnderecoEvento> page = enderecoEventoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endereco-eventos/:id} : get the "id" enderecoEvento.
     *
     * @param id the id of the enderecoEvento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoEvento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/endereco-eventos/{id}")
    public ResponseEntity<EnderecoEvento> getEnderecoEvento(@PathVariable Long id) {
        log.debug("REST request to get EnderecoEvento : {}", id);
        Optional<EnderecoEvento> enderecoEvento = enderecoEventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoEvento);
    }

    /**
     * {@code DELETE  /endereco-eventos/:id} : delete the "id" enderecoEvento.
     *
     * @param id the id of the enderecoEvento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/endereco-eventos/{id}")
    public ResponseEntity<Void> deleteEnderecoEvento(@PathVariable Long id) {
        log.debug("REST request to delete EnderecoEvento : {}", id);
        enderecoEventoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
