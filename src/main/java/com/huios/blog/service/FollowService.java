package com.huios.blog.service;

import com.huios.blog.service.dto.FollowDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.huios.blog.domain.Follow}.
 */
public interface FollowService {
    /**
     * Save a follow.
     *
     * @param followDTO the entity to save.
     * @return the persisted entity.
     */
    FollowDTO save(FollowDTO followDTO);

    /**
     * Partially updates a follow.
     *
     * @param followDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FollowDTO> partialUpdate(FollowDTO followDTO);

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FollowDTO> findAll(Pageable pageable);

    /**
     * Get the "id" follow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FollowDTO> findOne(Long id);

    /**
     * Delete the "id" follow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the follow corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FollowDTO> search(String query, Pageable pageable);
}
