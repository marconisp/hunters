package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.repository.ReceberRepository;
import br.com.jhisolution.user.hunters.service.ReceberService;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.RelatorioReceberDTO;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Receber}.
 */
@RestController
@RequestMapping("/api")
public class ReceberResource {

    private final Logger log = LoggerFactory.getLogger(ReceberResource.class);

    private static final String ENTITY_NAME = "controleReceber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceberService receberService;

    private final ReceberRepository receberRepository;

    public ReceberResource(ReceberService receberService, ReceberRepository receberRepository) {
        this.receberService = receberService;
        this.receberRepository = receberRepository;
    }

    /**
     * {@code POST  /recebers} : Create a new receber.
     *
     * @param receber the receber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receber, or with status {@code 400 (Bad Request)} if the receber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recebers")
    public ResponseEntity<Receber> createReceber(@Valid @RequestBody Receber receber) throws URISyntaxException {
        log.debug("REST request to save Receber : {}", receber);
        if (receber.getId() != null) {
            throw new BadRequestAlertException("A new receber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Receber result = receberService.save(receber);
        return ResponseEntity
            .created(new URI("/api/recebers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recebers/:id} : Updates an existing receber.
     *
     * @param id the id of the receber to save.
     * @param receber the receber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receber,
     * or with status {@code 400 (Bad Request)} if the receber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recebers/{id}")
    public ResponseEntity<Receber> updateReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Receber receber
    ) throws URISyntaxException {
        log.debug("REST request to update Receber : {}, {}", id, receber);
        if (receber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Receber result = receberService.update(receber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recebers/:id} : Partial updates given fields of an existing receber, field will ignore if it is null
     *
     * @param id the id of the receber to save.
     * @param receber the receber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receber,
     * or with status {@code 400 (Bad Request)} if the receber is not valid,
     * or with status {@code 404 (Not Found)} if the receber is not found,
     * or with status {@code 500 (Internal Server Error)} if the receber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recebers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Receber> partialUpdateReceber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Receber receber
    ) throws URISyntaxException {
        log.debug("REST request to partial update Receber partially : {}, {}", id, receber);
        if (receber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Receber> result = receberService.partialUpdate(receber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receber.getId().toString())
        );
    }

    /**
     * {@code GET  /recebers} : get all the recebers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recebers in body.
     */
    @GetMapping("/recebers")
    public ResponseEntity<List<Receber>> getAllRecebers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Recebers");
        Page<Receber> page;
        if (eagerload) {
            page = receberService.findAllWithEagerRelationships(pageable);
        } else {
            page = receberService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recebers/:id} : get the "id" receber.
     *
     * @param id the id of the receber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recebers/{id}")
    public ResponseEntity<Receber> getReceber(@PathVariable Long id) {
        log.debug("REST request to get Receber : {}", id);
        Optional<Receber> receber = receberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receber);
    }

    /**
     * {@code DELETE  /recebers/:id} : delete the "id" receber.
     *
     * @param id the id of the receber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recebers/{id}")
    public ResponseEntity<Void> deleteReceber(@PathVariable Long id) {
        log.debug("REST request to delete Receber : {}", id);
        receberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/recebers/report/periodo")
    public ResponseEntity<RelatorioReceberDTO> getContasReceber(@Valid @RequestBody FiltroReceberDTO filtro) throws URISyntaxException {
        log.debug("REST request to get a list of Recebers period - initial:{} to final:{}", filtro.getDataInicio(), filtro.getDataFim());
        RelatorioReceberDTO dto = receberService.findAllByDataInicialAndDataFinal(filtro);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/recebers/report/periodo/jasper")
    public ResponseEntity<Resource> getContasReceberJasper(@Valid @RequestBody FiltroReceberDTO filtro, HttpServletRequest request)
        throws URISyntaxException, IOException, java.io.IOException {
        log.debug("***************************************************************************************");
        log.debug("REST request to get a list of Recebers period - initial:{} to final:{}", filtro.getDataInicio(), filtro.getDataFim());
        log.debug("***************************************************************************************");
        // Load file as Resource
        Resource resource = receberService.findAllByDataInicialAndDataFinalJasper(filtro);
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
