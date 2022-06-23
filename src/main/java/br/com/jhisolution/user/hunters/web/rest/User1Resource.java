package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.domain.User1;
import br.com.jhisolution.user.hunters.repository.User1Repository;
import br.com.jhisolution.user.hunters.service.User1Service;
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
 * REST controller for managing {@link br.com.jhisolution.user.hunters.domain.User1}.
 */
@RestController
@RequestMapping("/api")
public class User1Resource {

    private final Logger log = LoggerFactory.getLogger(User1Resource.class);

    private static final String ENTITY_NAME = "userUser1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final User1Service user1Service;

    private final User1Repository user1Repository;

    public User1Resource(User1Service user1Service, User1Repository user1Repository) {
        this.user1Service = user1Service;
        this.user1Repository = user1Repository;
    }

    /**
     * {@code POST  /user-1-s} : Create a new user1.
     *
     * @param user1 the user1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user1, or with status {@code 400 (Bad Request)} if the user1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-1-s")
    public ResponseEntity<User1> createUser1(@Valid @RequestBody User1 user1) throws URISyntaxException {
        log.debug("REST request to save User1 : {}", user1);
        if (user1.getId() != null) {
            throw new BadRequestAlertException("A new user1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User1 result = user1Service.save(user1);
        return ResponseEntity
            .created(new URI("/api/user-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-1-s/:id} : Updates an existing user1.
     *
     * @param id the id of the user1 to save.
     * @param user1 the user1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user1,
     * or with status {@code 400 (Bad Request)} if the user1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the user1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-1-s/{id}")
    public ResponseEntity<User1> updateUser1(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody User1 user1)
        throws URISyntaxException {
        log.debug("REST request to update User1 : {}, {}", id, user1);
        if (user1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, user1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!user1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        User1 result = user1Service.update(user1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, user1.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-1-s/:id} : Partial updates given fields of an existing user1, field will ignore if it is null
     *
     * @param id the id of the user1 to save.
     * @param user1 the user1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user1,
     * or with status {@code 400 (Bad Request)} if the user1 is not valid,
     * or with status {@code 404 (Not Found)} if the user1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the user1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-1-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<User1> partialUpdateUser1(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody User1 user1
    ) throws URISyntaxException {
        log.debug("REST request to partial update User1 partially : {}, {}", id, user1);
        if (user1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, user1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!user1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<User1> result = user1Service.partialUpdate(user1);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, user1.getId().toString())
        );
    }

    /**
     * {@code GET  /user-1-s} : get all the user1s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of user1s in body.
     */
    @GetMapping("/user-1-s")
    public ResponseEntity<List<User1>> getAllUser1s(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of User1s");
        Page<User1> page = user1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-1-s/:id} : get the "id" user1.
     *
     * @param id the id of the user1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the user1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-1-s/{id}")
    public ResponseEntity<User1> getUser1(@PathVariable Long id) {
        log.debug("REST request to get User1 : {}", id);
        Optional<User1> user1 = user1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(user1);
    }

    /**
     * {@code DELETE  /user-1-s/:id} : delete the "id" user1.
     *
     * @param id the id of the user1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-1-s/{id}")
    public ResponseEntity<Void> deleteUser1(@PathVariable Long id) {
        log.debug("REST request to delete User1 : {}", id);
        user1Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
