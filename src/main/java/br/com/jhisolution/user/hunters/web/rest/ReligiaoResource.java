package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Religiao;
import br.com.jhisolution.user.hunters.repository.ReligiaoRepository;
import br.com.jhisolution.user.hunters.service.ReligiaoService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Religiao}.
 */
@RestController
@RequestMapping("/api")
public class ReligiaoResource {

    private final Logger log = LoggerFactory.getLogger(ReligiaoResource.class);

    private static final String ENTITY_NAME = "configReligiao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReligiaoService religiaoService;

    private final ReligiaoRepository religiaoRepository;

    public ReligiaoResource(ReligiaoService religiaoService, ReligiaoRepository religiaoRepository) {
        this.religiaoService = religiaoService;
        this.religiaoRepository = religiaoRepository;
    }

    /**
     * {@code POST  /religiaos} : Create a new religiao.
     *
     * @param religiao the religiao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new religiao, or with status {@code 400 (Bad Request)} if the religiao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/religiaos")
    public ResponseEntity<Religiao> createReligiao(@Valid @RequestBody Religiao religiao) throws URISyntaxException {
        log.debug("REST request to save Religiao : {}", religiao);
        if (religiao.getId() != null) {
            throw new BadRequestAlertException("A new religiao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Religiao result = religiaoService.save(religiao);
        return ResponseEntity
            .created(new URI("/api/religiaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /religiaos/:id} : Updates an existing religiao.
     *
     * @param id the id of the religiao to save.
     * @param religiao the religiao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated religiao,
     * or with status {@code 400 (Bad Request)} if the religiao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the religiao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/religiaos/{id}")
    public ResponseEntity<Religiao> updateReligiao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Religiao religiao
    ) throws URISyntaxException {
        log.debug("REST request to update Religiao : {}, {}", id, religiao);
        if (religiao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, religiao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!religiaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Religiao result = religiaoService.update(religiao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, religiao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /religiaos/:id} : Partial updates given fields of an existing religiao, field will ignore if it is null
     *
     * @param id the id of the religiao to save.
     * @param religiao the religiao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated religiao,
     * or with status {@code 400 (Bad Request)} if the religiao is not valid,
     * or with status {@code 404 (Not Found)} if the religiao is not found,
     * or with status {@code 500 (Internal Server Error)} if the religiao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/religiaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Religiao> partialUpdateReligiao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Religiao religiao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Religiao partially : {}, {}", id, religiao);
        if (religiao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, religiao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!religiaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Religiao> result = religiaoService.partialUpdate(religiao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, religiao.getId().toString())
        );
    }

    /**
     * {@code GET  /religiaos} : get all the religiaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of religiaos in body.
     */
    @GetMapping("/religiaos")
    public ResponseEntity<List<Religiao>> getAllReligiaos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Religiaos");
        Page<Religiao> page = religiaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /religiaos/:id} : get the "id" religiao.
     *
     * @param id the id of the religiao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the religiao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/religiaos/{id}")
    public ResponseEntity<Religiao> getReligiao(@PathVariable Long id) {
        log.debug("REST request to get Religiao : {}", id);
        Optional<Religiao> religiao = religiaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(religiao);
    }

    /**
     * {@code DELETE  /religiaos/:id} : delete the "id" religiao.
     *
     * @param id the id of the religiao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/religiaos/{id}")
    public ResponseEntity<Void> deleteReligiao(@PathVariable Long id) {
        log.debug("REST request to delete Religiao : {}", id);
        religiaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
