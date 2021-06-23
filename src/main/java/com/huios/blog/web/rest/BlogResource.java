package com.huios.blog.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.huios.blog.repository.AppuserRepository;
import com.huios.blog.repository.BlogRepository;
import com.huios.blog.repository.UserRepository;
import com.huios.blog.security.AuthoritiesConstants;
import com.huios.blog.security.SecurityUtils;
import com.huios.blog.service.BlogQueryService;
import com.huios.blog.service.BlogService;
import com.huios.blog.service.criteria.BlogCriteria;
import com.huios.blog.service.dto.BlogDTO;
import com.huios.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.huios.blog.domain.Blog}.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "blog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogService blogService;

    private final BlogRepository blogRepository;

    private final BlogQueryService blogQueryService;

    private final UserRepository userRepository;

    private final AppuserRepository appuserRepository;

    public BlogResource(
        BlogService blogService,
        BlogRepository blogRepository,
        BlogQueryService blogQueryService,
        UserRepository userRepository,
        AppuserRepository appuserRepository
    ) {
        this.blogService = blogService;
        this.blogRepository = blogRepository;
        this.blogQueryService = blogQueryService;
        this.userRepository = userRepository;
        this.appuserRepository = appuserRepository;
    }

    /**
     * {@code POST  /blogs} : Create a new blog.
     *
     * @param blogDTO the blogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogDTO, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogs")
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogDTO) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blogDTO);
        if (blogDTO.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //BlogDTO result = blogService.save(blogDTO);
        /*
        ajout du contrôle de la personne connectée avant d'enregister le blog
        */
        BlogDTO result = new BlogDTO();
        // s'il y a un utilisateur connecté
        if (userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).isPresent()) { // si l'utilisateur connecté est un USER
            if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.USER)) {
                // récupération de AppUser (user du blog)
                AppUser loggedAppuser = appuserRepository.findByUserId(
                    userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()
                );
                // si User du blog = User connecté
                if (blogDTO.getAppuser().getId().equals(loggedAppuser.getId())) {
                    result = blogService.save(blogDTO);
                    log.debug("Blog DTO to create, belongs to current user: {} ", blogDTO.toString());
                }
            }
            // si l'utilisateur connecté est un ADMIN
            if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
                result = blogService.save(blogDTO);
                log.debug("Blog DTO to create, belongs to current user: {} ", blogDTO.toString());
            }
        }
        return ResponseEntity
            .created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blogs/:id} : Updates an existing blog.
     *
     * @param id the id of the blogDTO to save.
     * @param blogDTO the blogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogDTO,
     * or with status {@code 400 (Bad Request)} if the blogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blogs/{id}")
    public ResponseEntity<BlogDTO> updateBlog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlogDTO blogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Blog : {}, {}", id, blogDTO);
        if (blogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BlogDTO result = blogService.save(blogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blogs/:id} : Partial updates given fields of an existing blog, field will ignore if it is null
     *
     * @param id the id of the blogDTO to save.
     * @param blogDTO the blogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogDTO,
     * or with status {@code 400 (Bad Request)} if the blogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blogs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BlogDTO> partialUpdateBlog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlogDTO blogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Blog partially : {}, {}", id, blogDTO);
        if (blogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlogDTO> result = blogService.partialUpdate(blogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDTO>> getAllBlogs(BlogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Blogs by criteria: {}", criteria);
        // Page<BlogDTO> page = blogQueryService.findByCriteria(criteria, pageable);
        Page<BlogDTO> page;
        if (
            SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN) ||
            SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.USER)
        ) {
            BlogCriteria loggedCriteria = new BlogCriteria();
            LongFilter longFilter = new LongFilter();
            if (userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).isPresent()) {
                AppUser loggedAppuser = appuserRepository.findByUserId(
                    userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()
                );
                loggedCriteria.setAppuserId(longFilter.setEquals(loggedAppuser.getId()));
            }
            page = blogQueryService.findByCriteria(loggedCriteria, pageable);
        } else {
            page = blogQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blogs/count} : count all the blogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blogs/count")
    public ResponseEntity<Long> countBlogs(BlogCriteria criteria) {
        log.debug("REST request to count Blogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(blogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blogs/:id} : get the "id" blog.
     *
     * @param id the id of the blogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blogs/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<BlogDTO> blogDTO = blogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogDTO);
    }

    /**
     * {@code DELETE  /blogs/:id} : delete the "id" blog.
     *
     * @param id the id of the blogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);
        blogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/blogs?query=:query} : search for the blog corresponding
     * to the query.
     *
     * @param query the query of the blog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/blogs")
    public ResponseEntity<List<BlogDTO>> searchBlogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Blogs for query {}", query);
        Page<BlogDTO> page = blogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
