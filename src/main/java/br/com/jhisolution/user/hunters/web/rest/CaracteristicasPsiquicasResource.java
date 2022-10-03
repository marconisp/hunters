package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas;
import br.com.jhisolution.user.hunters.repository.CaracteristicasPsiquicasRepository;
import br.com.jhisolution.user.hunters.service.CaracteristicasPsiquicasService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas}.
 */
@RestController
@RequestMapping("/api")
public class CaracteristicasPsiquicasResource {

    private final Logger log = LoggerFactory.getLogger(CaracteristicasPsiquicasResource.class);

    private static final String ENTITY_NAME = "matriculaCaracteristicasPsiquicas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaracteristicasPsiquicasService caracteristicasPsiquicasService;

    private final CaracteristicasPsiquicasRepository caracteristicasPsiquicasRepository;

    public CaracteristicasPsiquicasResource(
        CaracteristicasPsiquicasService caracteristicasPsiquicasService,
        CaracteristicasPsiquicasRepository caracteristicasPsiquicasRepository
    ) {
        this.caracteristicasPsiquicasService = caracteristicasPsiquicasService;
        this.caracteristicasPsiquicasRepository = caracteristicasPsiquicasRepository;
    }

    /**
     * {@code POST  /caracteristicas-psiquicas} : Create a new caracteristicasPsiquicas.
     *
     * @param caracteristicasPsiquicas the caracteristicasPsiquicas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caracteristicasPsiquicas, or with status {@code 400 (Bad Request)} if the caracteristicasPsiquicas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/caracteristicas-psiquicas")
    public ResponseEntity<CaracteristicasPsiquicas> createCaracteristicasPsiquicas(
        @Valid @RequestBody CaracteristicasPsiquicas caracteristicasPsiquicas
    ) throws URISyntaxException {
        log.debug("REST request to save CaracteristicasPsiquicas : {}", caracteristicasPsiquicas);
        if (caracteristicasPsiquicas.getId() != null) {
            throw new BadRequestAlertException("A new caracteristicasPsiquicas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaracteristicasPsiquicas result = caracteristicasPsiquicasService.save(caracteristicasPsiquicas);
        return ResponseEntity
            .created(new URI("/api/caracteristicas-psiquicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /caracteristicas-psiquicas/:id} : Updates an existing caracteristicasPsiquicas.
     *
     * @param id the id of the caracteristicasPsiquicas to save.
     * @param caracteristicasPsiquicas the caracteristicasPsiquicas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicasPsiquicas,
     * or with status {@code 400 (Bad Request)} if the caracteristicasPsiquicas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicasPsiquicas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/caracteristicas-psiquicas/{id}")
    public ResponseEntity<CaracteristicasPsiquicas> updateCaracteristicasPsiquicas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CaracteristicasPsiquicas caracteristicasPsiquicas
    ) throws URISyntaxException {
        log.debug("REST request to update CaracteristicasPsiquicas : {}, {}", id, caracteristicasPsiquicas);
        if (caracteristicasPsiquicas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicasPsiquicas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caracteristicasPsiquicasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaracteristicasPsiquicas result = caracteristicasPsiquicasService.update(caracteristicasPsiquicas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicasPsiquicas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /caracteristicas-psiquicas/:id} : Partial updates given fields of an existing caracteristicasPsiquicas, field will ignore if it is null
     *
     * @param id the id of the caracteristicasPsiquicas to save.
     * @param caracteristicasPsiquicas the caracteristicasPsiquicas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicasPsiquicas,
     * or with status {@code 400 (Bad Request)} if the caracteristicasPsiquicas is not valid,
     * or with status {@code 404 (Not Found)} if the caracteristicasPsiquicas is not found,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicasPsiquicas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/caracteristicas-psiquicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaracteristicasPsiquicas> partialUpdateCaracteristicasPsiquicas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CaracteristicasPsiquicas caracteristicasPsiquicas
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaracteristicasPsiquicas partially : {}, {}", id, caracteristicasPsiquicas);
        if (caracteristicasPsiquicas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicasPsiquicas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caracteristicasPsiquicasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaracteristicasPsiquicas> result = caracteristicasPsiquicasService.partialUpdate(caracteristicasPsiquicas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicasPsiquicas.getId().toString())
        );
    }

    /**
     * {@code GET  /caracteristicas-psiquicas} : get all the caracteristicasPsiquicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caracteristicasPsiquicas in body.
     */
    @GetMapping("/caracteristicas-psiquicas")
    public ResponseEntity<List<CaracteristicasPsiquicas>> getAllCaracteristicasPsiquicas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CaracteristicasPsiquicas");
        Page<CaracteristicasPsiquicas> page = caracteristicasPsiquicasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /caracteristicas-psiquicas/:id} : get the "id" caracteristicasPsiquicas.
     *
     * @param id the id of the caracteristicasPsiquicas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caracteristicasPsiquicas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/caracteristicas-psiquicas/{id}")
    public ResponseEntity<CaracteristicasPsiquicas> getCaracteristicasPsiquicas(@PathVariable Long id) {
        log.debug("REST request to get CaracteristicasPsiquicas : {}", id);
        Optional<CaracteristicasPsiquicas> caracteristicasPsiquicas = caracteristicasPsiquicasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicasPsiquicas);
    }

    /**
     * {@code DELETE  /caracteristicas-psiquicas/:id} : delete the "id" caracteristicasPsiquicas.
     *
     * @param id the id of the caracteristicasPsiquicas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/caracteristicas-psiquicas/{id}")
    public ResponseEntity<Void> deleteCaracteristicasPsiquicas(@PathVariable Long id) {
        log.debug("REST request to delete CaracteristicasPsiquicas : {}", id);
        caracteristicasPsiquicasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
