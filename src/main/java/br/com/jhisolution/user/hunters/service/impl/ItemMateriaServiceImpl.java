package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.ItemMateria;
import br.com.jhisolution.user.hunters.repository.ItemMateriaRepository;
import br.com.jhisolution.user.hunters.service.ItemMateriaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemMateria}.
 */
@Service
@Transactional
public class ItemMateriaServiceImpl implements ItemMateriaService {

    private final Logger log = LoggerFactory.getLogger(ItemMateriaServiceImpl.class);

    private final ItemMateriaRepository itemMateriaRepository;

    public ItemMateriaServiceImpl(ItemMateriaRepository itemMateriaRepository) {
        this.itemMateriaRepository = itemMateriaRepository;
    }

    @Override
    public ItemMateria save(ItemMateria itemMateria) {
        log.debug("Request to save ItemMateria : {}", itemMateria);
        return itemMateriaRepository.save(itemMateria);
    }

    @Override
    public ItemMateria update(ItemMateria itemMateria) {
        log.debug("Request to save ItemMateria : {}", itemMateria);
        return itemMateriaRepository.save(itemMateria);
    }

    @Override
    public Optional<ItemMateria> partialUpdate(ItemMateria itemMateria) {
        log.debug("Request to partially update ItemMateria : {}", itemMateria);

        return itemMateriaRepository
            .findById(itemMateria.getId())
            .map(existingItemMateria -> {
                if (itemMateria.getNota() != null) {
                    existingItemMateria.setNota(itemMateria.getNota());
                }
                if (itemMateria.getObs() != null) {
                    existingItemMateria.setObs(itemMateria.getObs());
                }

                return existingItemMateria;
            })
            .map(itemMateriaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemMateria> findAll(Pageable pageable) {
        log.debug("Request to get all ItemMaterias");
        return itemMateriaRepository.findAll(pageable);
    }

    public Page<ItemMateria> findAllWithEagerRelationships(Pageable pageable) {
        return itemMateriaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemMateria> findOne(Long id) {
        log.debug("Request to get ItemMateria : {}", id);
        return itemMateriaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemMateria : {}", id);
        itemMateriaRepository.deleteById(id);
    }
}
