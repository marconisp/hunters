package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.repository.PagarRepository;
import br.com.jhisolution.user.hunters.service.PagarService;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.PagarDTO;
import br.com.jhisolution.user.hunters.web.rest.errors.BadRequestAlertException;
import io.jsonwebtoken.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Pagar}.
 */
@RestController
@RequestMapping("/api")
public class PagarResource {

    private final Logger log = LoggerFactory.getLogger(PagarResource.class);

    private static final String ENTITY_NAME = "controlePagar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagarService pagarService;

    private final PagarRepository pagarRepository;

    public PagarResource(PagarService pagarService, PagarRepository pagarRepository) {
        this.pagarService = pagarService;
        this.pagarRepository = pagarRepository;
    }

    /**
     * {@code POST  /pagars} : Create a new pagar.
     *
     * @param pagar the pagar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagar, or with status {@code 400 (Bad Request)} if the pagar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pagars")
    public ResponseEntity<Pagar> createPagar(@Valid @RequestBody Pagar pagar) throws URISyntaxException {
        log.debug("REST request to save Pagar : {}", pagar);
        if (pagar.getId() != null) {
            throw new BadRequestAlertException("A new pagar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pagar result = pagarService.save(pagar);
        return ResponseEntity
            .created(new URI("/api/pagars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pagars/:id} : Updates an existing pagar.
     *
     * @param id the id of the pagar to save.
     * @param pagar the pagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagar,
     * or with status {@code 400 (Bad Request)} if the pagar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pagars/{id}")
    public ResponseEntity<Pagar> updatePagar(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Pagar pagar)
        throws URISyntaxException {
        log.debug("REST request to update Pagar : {}, {}", id, pagar);
        if (pagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pagar result = pagarService.update(pagar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pagars/:id} : Partial updates given fields of an existing pagar, field will ignore if it is null
     *
     * @param id the id of the pagar to save.
     * @param pagar the pagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagar,
     * or with status {@code 400 (Bad Request)} if the pagar is not valid,
     * or with status {@code 404 (Not Found)} if the pagar is not found,
     * or with status {@code 500 (Internal Server Error)} if the pagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pagars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pagar> partialUpdatePagar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pagar pagar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pagar partially : {}, {}", id, pagar);
        if (pagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pagar> result = pagarService.partialUpdate(pagar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagar.getId().toString())
        );
    }

    /**
     * {@code GET  /pagars} : get all the pagars.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagars in body.
     */
    @GetMapping("/pagars")
    public ResponseEntity<List<Pagar>> getAllPagars(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Pagars");
        Page<Pagar> page;
        if (eagerload) {
            page = pagarService.findAllWithEagerRelationships(pageable);
        } else {
            page = pagarService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pagars/:id} : get the "id" pagar.
     *
     * @param id the id of the pagar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pagars/{id}")
    public ResponseEntity<Pagar> getPagar(@PathVariable Long id) {
        log.debug("REST request to get Pagar : {}", id);
        Optional<Pagar> pagar = pagarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagar);
    }

    /**
     * {@code DELETE  /pagars/:id} : delete the "id" pagar.
     *
     * @param id the id of the pagar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pagars/{id}")
    public ResponseEntity<Void> deletePagar(@PathVariable Long id) {
        log.debug("REST request to delete Pagar : {}", id);
        pagarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/pagars/report/periodo")
    public ResponseEntity<List<PagarDTO>> getContasReceber(@Valid @RequestBody FiltroPagarDTO filtro) throws URISyntaxException {
        log.debug("REST request to get a list of Pagars period - initial:{} to final:{}", filtro.getDataInicio(), filtro.getDataFim());
        List<PagarDTO> lista = pagarService.findAllByDataInicialAndDataFinal(filtro);
        return ResponseEntity.ok().body(lista);
    }

    @PostMapping("/pagars/report/periodo/jasper")
    public ResponseEntity<Resource> getContasReceberJasper(@Valid @RequestBody FiltroPagarDTO filtro, HttpServletRequest request)
        throws URISyntaxException, IOException, java.io.IOException {
        log.debug("***************************************************************************************");
        log.debug("REST request to get a list of Recebers period - initial:{} to final:{}", filtro.getDataInicio(), filtro.getDataFim());
        log.debug("***************************************************************************************");
        // Load file as Resource
        Resource resource = pagarService.findAllByDataInicialAndDataFinalJasper(filtro);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(contentType))
            .contentLength(resource.getFile().length())
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
            .headers(HeaderUtil.createAlert(applicationName, "Orders exported successfully", resource.toString()))
            .body(resource);
    }
}
