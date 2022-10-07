package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import br.com.jhisolution.user.hunters.repository.TipoMensagemRepository;
import br.com.jhisolution.user.hunters.service.TipoMensagemService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.TipoMensagem}.
 */
@RestController
@RequestMapping("/api")
public class TipoMensagemResource {

    private final Logger log = LoggerFactory.getLogger(TipoMensagemResource.class);

    private static final String ENTITY_NAME = "configTipoMensagem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoMensagemService tipoMensagemService;

    private final TipoMensagemRepository tipoMensagemRepository;

    public TipoMensagemResource(TipoMensagemService tipoMensagemService, TipoMensagemRepository tipoMensagemRepository) {
        this.tipoMensagemService = tipoMensagemService;
        this.tipoMensagemRepository = tipoMensagemRepository;
    }

    /**
     * {@code POST  /tipo-mensagems} : Create a new tipoMensagem.
     *
     * @param tipoMensagem the tipoMensagem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoMensagem, or with status {@code 400 (Bad Request)} if the tipoMensagem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-mensagems")
    public ResponseEntity<TipoMensagem> createTipoMensagem(@Valid @RequestBody TipoMensagem tipoMensagem) throws URISyntaxException {
        log.debug("REST request to save TipoMensagem : {}", tipoMensagem);
        if (tipoMensagem.getId() != null) {
            throw new BadRequestAlertException("A new tipoMensagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMensagem result = tipoMensagemService.save(tipoMensagem);
        return ResponseEntity
            .created(new URI("/api/tipo-mensagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-mensagems/:id} : Updates an existing tipoMensagem.
     *
     * @param id the id of the tipoMensagem to save.
     * @param tipoMensagem the tipoMensagem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMensagem,
     * or with status {@code 400 (Bad Request)} if the tipoMensagem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoMensagem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-mensagems/{id}")
    public ResponseEntity<TipoMensagem> updateTipoMensagem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoMensagem tipoMensagem
    ) throws URISyntaxException {
        log.debug("REST request to update TipoMensagem : {}, {}", id, tipoMensagem);
        if (tipoMensagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoMensagem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoMensagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoMensagem result = tipoMensagemService.update(tipoMensagem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoMensagem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-mensagems/:id} : Partial updates given fields of an existing tipoMensagem, field will ignore if it is null
     *
     * @param id the id of the tipoMensagem to save.
     * @param tipoMensagem the tipoMensagem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMensagem,
     * or with status {@code 400 (Bad Request)} if the tipoMensagem is not valid,
     * or with status {@code 404 (Not Found)} if the tipoMensagem is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoMensagem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-mensagems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoMensagem> partialUpdateTipoMensagem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoMensagem tipoMensagem
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoMensagem partially : {}, {}", id, tipoMensagem);
        if (tipoMensagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoMensagem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoMensagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoMensagem> result = tipoMensagemService.partialUpdate(tipoMensagem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoMensagem.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-mensagems} : get all the tipoMensagems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoMensagems in body.
     */
    @GetMapping("/tipo-mensagems")
    public ResponseEntity<List<TipoMensagem>> getAllTipoMensagems(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoMensagems");
        Page<TipoMensagem> page = tipoMensagemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-mensagems/:id} : get the "id" tipoMensagem.
     *
     * @param id the id of the tipoMensagem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoMensagem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-mensagems/{id}")
    public ResponseEntity<TipoMensagem> getTipoMensagem(@PathVariable Long id) {
        log.debug("REST request to get TipoMensagem : {}", id);
        Optional<TipoMensagem> tipoMensagem = tipoMensagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoMensagem);
    }

    /**
     * {@code DELETE  /tipo-mensagems/:id} : delete the "id" tipoMensagem.
     *
     * @param id the id of the tipoMensagem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-mensagems/{id}")
    public ResponseEntity<Void> deleteTipoMensagem(@PathVariable Long id) {
        log.debug("REST request to delete TipoMensagem : {}", id);
        tipoMensagemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
