package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoIcon;
import br.com.jhisolution.user.hunters.repository.FotoIconRepository;
import br.com.jhisolution.user.hunters.service.FotoIconService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoIcon}.
 */
@RestController
@RequestMapping("/api")
public class FotoIconResource {

    private final Logger log = LoggerFactory.getLogger(FotoIconResource.class);

    private static final String ENTITY_NAME = "fotoFotoIcon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoIconService fotoIconService;

    private final FotoIconRepository fotoIconRepository;

    public FotoIconResource(FotoIconService fotoIconService, FotoIconRepository fotoIconRepository) {
        this.fotoIconService = fotoIconService;
        this.fotoIconRepository = fotoIconRepository;
    }

    /**
     * {@code POST  /foto-icons} : Create a new fotoIcon.
     *
     * @param fotoIcon the fotoIcon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoIcon, or with status {@code 400 (Bad Request)} if the fotoIcon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-icons")
    public ResponseEntity<FotoIcon> createFotoIcon(@Valid @RequestBody FotoIcon fotoIcon) throws URISyntaxException {
        log.debug("REST request to save FotoIcon : {}", fotoIcon);
        if (fotoIcon.getId() != null) {
            throw new BadRequestAlertException("A new fotoIcon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoIcon result = fotoIconService.save(fotoIcon);
        return ResponseEntity
            .created(new URI("/api/foto-icons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-icons/:id} : Updates an existing fotoIcon.
     *
     * @param id the id of the fotoIcon to save.
     * @param fotoIcon the fotoIcon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoIcon,
     * or with status {@code 400 (Bad Request)} if the fotoIcon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoIcon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-icons/{id}")
    public ResponseEntity<FotoIcon> updateFotoIcon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoIcon fotoIcon
    ) throws URISyntaxException {
        log.debug("REST request to update FotoIcon : {}, {}", id, fotoIcon);
        if (fotoIcon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoIcon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoIconRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoIcon result = fotoIconService.update(fotoIcon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoIcon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-icons/:id} : Partial updates given fields of an existing fotoIcon, field will ignore if it is null
     *
     * @param id the id of the fotoIcon to save.
     * @param fotoIcon the fotoIcon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoIcon,
     * or with status {@code 400 (Bad Request)} if the fotoIcon is not valid,
     * or with status {@code 404 (Not Found)} if the fotoIcon is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoIcon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-icons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoIcon> partialUpdateFotoIcon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoIcon fotoIcon
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoIcon partially : {}, {}", id, fotoIcon);
        if (fotoIcon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoIcon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoIconRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoIcon> result = fotoIconService.partialUpdate(fotoIcon);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoIcon.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-icons} : get all the fotoIcons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoIcons in body.
     */
    @GetMapping("/foto-icons")
    public ResponseEntity<List<FotoIcon>> getAllFotoIcons(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoIcons");
        Page<FotoIcon> page = fotoIconService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-icons/:id} : get the "id" fotoIcon.
     *
     * @param id the id of the fotoIcon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoIcon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-icons/{id}")
    public ResponseEntity<FotoIcon> getFotoIcon(@PathVariable Long id) {
        log.debug("REST request to get FotoIcon : {}", id);
        Optional<FotoIcon> fotoIcon = fotoIconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoIcon);
    }

    /**
     * {@code DELETE  /foto-icons/:id} : delete the "id" fotoIcon.
     *
     * @param id the id of the fotoIcon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-icons/{id}")
    public ResponseEntity<Void> deleteFotoIcon(@PathVariable Long id) {
        log.debug("REST request to delete FotoIcon : {}", id);
        fotoIconService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
