package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.ReceberDe;
import br.com.jhisolution.user.hunters.repository.ReceberDeRepository;
import br.com.jhisolution.user.hunters.service.ReceberDeService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.ReceberDe}.
 */
@RestController
@RequestMapping("/api")
public class ReceberDeResource {

    private final Logger log = LoggerFactory.getLogger(ReceberDeResource.class);

    private static final String ENTITY_NAME = "controleReceberDe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceberDeService receberDeService;

    private final ReceberDeRepository receberDeRepository;

    public ReceberDeResource(ReceberDeService receberDeService, ReceberDeRepository receberDeRepository) {
        this.receberDeService = receberDeService;
        this.receberDeRepository = receberDeRepository;
    }

    /**
     * {@code POST  /receber-des} : Create a new receberDe.
     *
     * @param receberDe the receberDe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receberDe, or with status {@code 400 (Bad Request)} if the receberDe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receber-des")
    public ResponseEntity<ReceberDe> createReceberDe(@Valid @RequestBody ReceberDe receberDe) throws URISyntaxException {
        log.debug("REST request to save ReceberDe : {}", receberDe);
        if (receberDe.getId() != null) {
            throw new BadRequestAlertException("A new receberDe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceberDe result = receberDeService.save(receberDe);
        return ResponseEntity
            .created(new URI("/api/receber-des/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receber-des/:id} : Updates an existing receberDe.
     *
     * @param id the id of the receberDe to save.
     * @param receberDe the receberDe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receberDe,
     * or with status {@code 400 (Bad Request)} if the receberDe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receberDe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receber-des/{id}")
    public ResponseEntity<ReceberDe> updateReceberDe(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReceberDe receberDe
    ) throws URISyntaxException {
        log.debug("REST request to update ReceberDe : {}, {}", id, receberDe);
        if (receberDe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receberDe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receberDeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReceberDe result = receberDeService.update(receberDe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receberDe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /receber-des/:id} : Partial updates given fields of an existing receberDe, field will ignore if it is null
     *
     * @param id the id of the receberDe to save.
     * @param receberDe the receberDe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receberDe,
     * or with status {@code 400 (Bad Request)} if the receberDe is not valid,
     * or with status {@code 404 (Not Found)} if the receberDe is not found,
     * or with status {@code 500 (Internal Server Error)} if the receberDe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/receber-des/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReceberDe> partialUpdateReceberDe(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReceberDe receberDe
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReceberDe partially : {}, {}", id, receberDe);
        if (receberDe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receberDe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receberDeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReceberDe> result = receberDeService.partialUpdate(receberDe);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receberDe.getId().toString())
        );
    }

    /**
     * {@code GET  /receber-des} : get all the receberDes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receberDes in body.
     */
    @GetMapping("/receber-des")
    public ResponseEntity<List<ReceberDe>> getAllReceberDes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ReceberDes");
        Page<ReceberDe> page = receberDeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /receber-des/:id} : get the "id" receberDe.
     *
     * @param id the id of the receberDe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receberDe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receber-des/{id}")
    public ResponseEntity<ReceberDe> getReceberDe(@PathVariable Long id) {
        log.debug("REST request to get ReceberDe : {}", id);
        Optional<ReceberDe> receberDe = receberDeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receberDe);
    }

    /**
     * {@code DELETE  /receber-des/:id} : delete the "id" receberDe.
     *
     * @param id the id of the receberDe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receber-des/{id}")
    public ResponseEntity<Void> deleteReceberDe(@PathVariable Long id) {
        log.debug("REST request to delete ReceberDe : {}", id);
        receberDeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
