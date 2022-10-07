package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.ItemMateria;
import br.com.jhisolution.user.hunters.repository.ItemMateriaRepository;
import br.com.jhisolution.user.hunters.service.ItemMateriaService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.ItemMateria}.
 */
@RestController
@RequestMapping("/api")
public class ItemMateriaResource {

    private final Logger log = LoggerFactory.getLogger(ItemMateriaResource.class);

    private static final String ENTITY_NAME = "matriculaItemMateria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemMateriaService itemMateriaService;

    private final ItemMateriaRepository itemMateriaRepository;

    public ItemMateriaResource(ItemMateriaService itemMateriaService, ItemMateriaRepository itemMateriaRepository) {
        this.itemMateriaService = itemMateriaService;
        this.itemMateriaRepository = itemMateriaRepository;
    }

    /**
     * {@code POST  /item-materias} : Create a new itemMateria.
     *
     * @param itemMateria the itemMateria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemMateria, or with status {@code 400 (Bad Request)} if the itemMateria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-materias")
    public ResponseEntity<ItemMateria> createItemMateria(@Valid @RequestBody ItemMateria itemMateria) throws URISyntaxException {
        log.debug("REST request to save ItemMateria : {}", itemMateria);
        if (itemMateria.getId() != null) {
            throw new BadRequestAlertException("A new itemMateria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemMateria result = itemMateriaService.save(itemMateria);
        return ResponseEntity
            .created(new URI("/api/item-materias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-materias/:id} : Updates an existing itemMateria.
     *
     * @param id the id of the itemMateria to save.
     * @param itemMateria the itemMateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemMateria,
     * or with status {@code 400 (Bad Request)} if the itemMateria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemMateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-materias/{id}")
    public ResponseEntity<ItemMateria> updateItemMateria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemMateria itemMateria
    ) throws URISyntaxException {
        log.debug("REST request to update ItemMateria : {}, {}", id, itemMateria);
        if (itemMateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemMateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemMateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemMateria result = itemMateriaService.update(itemMateria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemMateria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-materias/:id} : Partial updates given fields of an existing itemMateria, field will ignore if it is null
     *
     * @param id the id of the itemMateria to save.
     * @param itemMateria the itemMateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemMateria,
     * or with status {@code 400 (Bad Request)} if the itemMateria is not valid,
     * or with status {@code 404 (Not Found)} if the itemMateria is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemMateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-materias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemMateria> partialUpdateItemMateria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemMateria itemMateria
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemMateria partially : {}, {}", id, itemMateria);
        if (itemMateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemMateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemMateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemMateria> result = itemMateriaService.partialUpdate(itemMateria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemMateria.getId().toString())
        );
    }

    /**
     * {@code GET  /item-materias} : get all the itemMaterias.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemMaterias in body.
     */
    @GetMapping("/item-materias")
    public ResponseEntity<List<ItemMateria>> getAllItemMaterias(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ItemMaterias");
        Page<ItemMateria> page;
        if (eagerload) {
            page = itemMateriaService.findAllWithEagerRelationships(pageable);
        } else {
            page = itemMateriaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-materias/:id} : get the "id" itemMateria.
     *
     * @param id the id of the itemMateria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemMateria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-materias/{id}")
    public ResponseEntity<ItemMateria> getItemMateria(@PathVariable Long id) {
        log.debug("REST request to get ItemMateria : {}", id);
        Optional<ItemMateria> itemMateria = itemMateriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemMateria);
    }

    /**
     * {@code DELETE  /item-materias/:id} : delete the "id" itemMateria.
     *
     * @param id the id of the itemMateria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-materias/{id}")
    public ResponseEntity<Void> deleteItemMateria(@PathVariable Long id) {
        log.debug("REST request to delete ItemMateria : {}", id);
        itemMateriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
