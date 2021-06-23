package com.huios.blog.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.huios.blog.domain.Appphoto;
import com.huios.blog.repository.AppphotoRepository;
import com.huios.blog.repository.search.AppphotoSearchRepository;
import com.huios.blog.service.AppphotoService;
import com.huios.blog.service.dto.AppphotoDTO;
import com.huios.blog.service.mapper.AppphotoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Appphoto}.
 */
@Service
@Transactional
public class AppphotoServiceImpl implements AppphotoService {

    private final Logger log = LoggerFactory.getLogger(AppphotoServiceImpl.class);

    private final AppphotoRepository appphotoRepository;

    private final AppphotoMapper appphotoMapper;

    private final AppphotoSearchRepository appphotoSearchRepository;

    public AppphotoServiceImpl(
        AppphotoRepository appphotoRepository,
        AppphotoMapper appphotoMapper,
        AppphotoSearchRepository appphotoSearchRepository
    ) {
        this.appphotoRepository = appphotoRepository;
        this.appphotoMapper = appphotoMapper;
        this.appphotoSearchRepository = appphotoSearchRepository;
    }

    @Override
    public AppphotoDTO save(AppphotoDTO appphotoDTO) {
        log.debug("Request to save Appphoto : {}", appphotoDTO);
        Appphoto appphoto = appphotoMapper.toEntity(appphotoDTO);
        appphoto = appphotoRepository.save(appphoto);
        AppphotoDTO result = appphotoMapper.toDto(appphoto);
        appphotoSearchRepository.save(appphoto);
        return result;
    }

    @Override
    public Optional<AppphotoDTO> partialUpdate(AppphotoDTO appphotoDTO) {
        log.debug("Request to partially update Appphoto : {}", appphotoDTO);

        return appphotoRepository
            .findById(appphotoDTO.getId())
            .map(
                existingAppphoto -> {
                    appphotoMapper.partialUpdate(existingAppphoto, appphotoDTO);

                    return existingAppphoto;
                }
            )
            .map(appphotoRepository::save)
            .map(
                savedAppphoto -> {
                    appphotoSearchRepository.save(savedAppphoto);

                    return savedAppphoto;
                }
            )
            .map(appphotoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppphotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Appphotos");
        return appphotoRepository.findAll(pageable).map(appphotoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppphotoDTO> findOne(Long id) {
        log.debug("Request to get Appphoto : {}", id);
        return appphotoRepository.findById(id).map(appphotoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appphoto : {}", id);
        appphotoRepository.deleteById(id);
        appphotoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppphotoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Appphotos for query {}", query);
        return appphotoSearchRepository.search(queryStringQuery(query), pageable).map(appphotoMapper::toDto);
    }
}
