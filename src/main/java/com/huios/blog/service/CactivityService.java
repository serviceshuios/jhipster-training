package com.huios.blog.service;

import com.huios.blog.service.dto.CactivityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.huios.blog.domain.Cactivity}.
 */
public interface CactivityService {
    /**
     * Save a cactivity.
     *
     * @param cactivityDTO the entity to save.
     * @return the persisted entity.
     */
    CactivityDTO save(CactivityDTO cactivityDTO);

    /**
     * Partially updates a cactivity.
     *
     * @param cactivityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CactivityDTO> partialUpdate(CactivityDTO cactivityDTO);

    /**
     * Get all the cactivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CactivityDTO> findAll(Pageable pageable);

    /**
     * Get all the cactivities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CactivityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" cactivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CactivityDTO> findOne(Long id);

    /**
     * Delete the "id" cactivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cactivity corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CactivityDTO> search(String query, Pageable pageable);
}
