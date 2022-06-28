package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.Mensagem;
import br.com.jhisolution.user.hunters.repository.MensagemRepository;
import br.com.jhisolution.user.hunters.service.MensagemService;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.Mensagem}.
 */
@RestController
@RequestMapping("/api")
public class MensagemResource {

    private final Logger log = LoggerFactory.getLogger(MensagemResource.class);

    private static final String ENTITY_NAME = "userMensagem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensagemService mensagemService;

    private final MensagemRepository mensagemRepository;

    public MensagemResource(MensagemService mensagemService, MensagemRepository mensagemRepository) {
        this.mensagemService = mensagemService;
        this.mensagemRepository = mensagemRepository;
    }

    /**
     * {@code POST  /mensagems} : Create a new mensagem.
     *
     * @param mensagem the mensagem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensagem, or with status {@code 400 (Bad Request)} if the mensagem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mensagems")
    public ResponseEntity<Mensagem> createMensagem(@Valid @RequestBody Mensagem mensagem) throws URISyntaxException {
        log.debug("REST request to save Mensagem : {}", mensagem);
        if (mensagem.getId() != null) {
            throw new BadRequestAlertException("A new mensagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mensagem result = mensagemService.save(mensagem);
        return ResponseEntity
            .created(new URI("/api/mensagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mensagems/:id} : Updates an existing mensagem.
     *
     * @param id the id of the mensagem to save.
     * @param mensagem the mensagem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensagem,
     * or with status {@code 400 (Bad Request)} if the mensagem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensagem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mensagems/{id}")
    public ResponseEntity<Mensagem> updateMensagem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Mensagem mensagem
    ) throws URISyntaxException {
        log.debug("REST request to update Mensagem : {}, {}", id, mensagem);
        if (mensagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensagem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mensagem result = mensagemService.update(mensagem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensagem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mensagems/:id} : Partial updates given fields of an existing mensagem, field will ignore if it is null
     *
     * @param id the id of the mensagem to save.
     * @param mensagem the mensagem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensagem,
     * or with status {@code 400 (Bad Request)} if the mensagem is not valid,
     * or with status {@code 404 (Not Found)} if the mensagem is not found,
     * or with status {@code 500 (Internal Server Error)} if the mensagem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mensagems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mensagem> partialUpdateMensagem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Mensagem mensagem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mensagem partially : {}, {}", id, mensagem);
        if (mensagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensagem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mensagem> result = mensagemService.partialUpdate(mensagem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensagem.getId().toString())
        );
    }

    /**
     * {@code GET  /mensagems} : get all the mensagems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensagems in body.
     */
    @GetMapping("/mensagems")
    public ResponseEntity<List<Mensagem>> getAllMensagems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Mensagems");
        Page<Mensagem> page;
        if (eagerload) {
            page = mensagemService.findAllWithEagerRelationships(pageable);
        } else {
            page = mensagemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/mensagems/dadospessoais/{id}")
    public ResponseEntity<List<Mensagem>> getAllByDadoPessoalId(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Enderecos by dados pessoais");
        Page<Mensagem> page = mensagemService.findAllByDadosPessoaisId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensagems/:id} : get the "id" mensagem.
     *
     * @param id the id of the mensagem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensagem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mensagems/{id}")
    public ResponseEntity<Mensagem> getMensagem(@PathVariable Long id) {
        log.debug("REST request to get Mensagem : {}", id);
        Optional<Mensagem> mensagem = mensagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensagem);
    }

    /**
     * {@code DELETE  /mensagems/:id} : delete the "id" mensagem.
     *
     * @param id the id of the mensagem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mensagems/{id}")
    public ResponseEntity<Void> deleteMensagem(@PathVariable Long id) {
        log.debug("REST request to delete Mensagem : {}", id);
        mensagemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
