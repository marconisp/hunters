package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.FotoAvatar;
import br.com.jhisolution.user.hunters.repository.FotoAvatarRepository;
import br.com.jhisolution.user.hunters.service.FotoAvatarService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.FotoAvatar}.
 */
@RestController
@RequestMapping("/api")
public class FotoAvatarResource {

    private final Logger log = LoggerFactory.getLogger(FotoAvatarResource.class);

    private static final String ENTITY_NAME = "fotoFotoAvatar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoAvatarService fotoAvatarService;

    private final FotoAvatarRepository fotoAvatarRepository;

    public FotoAvatarResource(FotoAvatarService fotoAvatarService, FotoAvatarRepository fotoAvatarRepository) {
        this.fotoAvatarService = fotoAvatarService;
        this.fotoAvatarRepository = fotoAvatarRepository;
    }

    /**
     * {@code POST  /foto-avatars} : Create a new fotoAvatar.
     *
     * @param fotoAvatar the fotoAvatar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoAvatar, or with status {@code 400 (Bad Request)} if the fotoAvatar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-avatars")
    public ResponseEntity<FotoAvatar> createFotoAvatar(@Valid @RequestBody FotoAvatar fotoAvatar) throws URISyntaxException {
        log.debug("REST request to save FotoAvatar : {}", fotoAvatar);
        if (fotoAvatar.getId() != null) {
            throw new BadRequestAlertException("A new fotoAvatar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoAvatar result = fotoAvatarService.save(fotoAvatar);
        return ResponseEntity
            .created(new URI("/api/foto-avatars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-avatars/:id} : Updates an existing fotoAvatar.
     *
     * @param id the id of the fotoAvatar to save.
     * @param fotoAvatar the fotoAvatar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoAvatar,
     * or with status {@code 400 (Bad Request)} if the fotoAvatar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoAvatar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-avatars/{id}")
    public ResponseEntity<FotoAvatar> updateFotoAvatar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoAvatar fotoAvatar
    ) throws URISyntaxException {
        log.debug("REST request to update FotoAvatar : {}, {}", id, fotoAvatar);
        if (fotoAvatar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoAvatar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoAvatarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoAvatar result = fotoAvatarService.update(fotoAvatar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoAvatar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-avatars/:id} : Partial updates given fields of an existing fotoAvatar, field will ignore if it is null
     *
     * @param id the id of the fotoAvatar to save.
     * @param fotoAvatar the fotoAvatar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoAvatar,
     * or with status {@code 400 (Bad Request)} if the fotoAvatar is not valid,
     * or with status {@code 404 (Not Found)} if the fotoAvatar is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoAvatar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-avatars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoAvatar> partialUpdateFotoAvatar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoAvatar fotoAvatar
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoAvatar partially : {}, {}", id, fotoAvatar);
        if (fotoAvatar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoAvatar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoAvatarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoAvatar> result = fotoAvatarService.partialUpdate(fotoAvatar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoAvatar.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-avatars} : get all the fotoAvatars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoAvatars in body.
     */
    @GetMapping("/foto-avatars")
    public ResponseEntity<List<FotoAvatar>> getAllFotoAvatars(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FotoAvatars");
        Page<FotoAvatar> page = fotoAvatarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-avatars/:id} : get the "id" fotoAvatar.
     *
     * @param id the id of the fotoAvatar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoAvatar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-avatars/{id}")
    public ResponseEntity<FotoAvatar> getFotoAvatar(@PathVariable Long id) {
        log.debug("REST request to get FotoAvatar : {}", id);
        Optional<FotoAvatar> fotoAvatar = fotoAvatarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoAvatar);
    }

    /**
     * {@code DELETE  /foto-avatars/:id} : delete the "id" fotoAvatar.
     *
     * @param id the id of the fotoAvatar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-avatars/{id}")
    public ResponseEntity<Void> deleteFotoAvatar(@PathVariable Long id) {
        log.debug("REST request to delete FotoAvatar : {}", id);
        fotoAvatarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
