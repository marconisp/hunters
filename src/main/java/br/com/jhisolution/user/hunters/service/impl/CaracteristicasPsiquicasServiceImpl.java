package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas;
import br.com.jhisolution.user.hunters.repository.CaracteristicasPsiquicasRepository;
import br.com.jhisolution.user.hunters.service.CaracteristicasPsiquicasService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaracteristicasPsiquicas}.
 */
@Service
@Transactional
public class CaracteristicasPsiquicasServiceImpl implements CaracteristicasPsiquicasService {

    private final Logger log = LoggerFactory.getLogger(CaracteristicasPsiquicasServiceImpl.class);

    private final CaracteristicasPsiquicasRepository caracteristicasPsiquicasRepository;

    public CaracteristicasPsiquicasServiceImpl(CaracteristicasPsiquicasRepository caracteristicasPsiquicasRepository) {
        this.caracteristicasPsiquicasRepository = caracteristicasPsiquicasRepository;
    }

    @Override
    public CaracteristicasPsiquicas save(CaracteristicasPsiquicas caracteristicasPsiquicas) {
        log.debug("Request to save CaracteristicasPsiquicas : {}", caracteristicasPsiquicas);
        return caracteristicasPsiquicasRepository.save(caracteristicasPsiquicas);
    }

    @Override
    public CaracteristicasPsiquicas update(CaracteristicasPsiquicas caracteristicasPsiquicas) {
        log.debug("Request to save CaracteristicasPsiquicas : {}", caracteristicasPsiquicas);
        return caracteristicasPsiquicasRepository.save(caracteristicasPsiquicas);
    }

    @Override
    public Optional<CaracteristicasPsiquicas> partialUpdate(CaracteristicasPsiquicas caracteristicasPsiquicas) {
        log.debug("Request to partially update CaracteristicasPsiquicas : {}", caracteristicasPsiquicas);

        return caracteristicasPsiquicasRepository
            .findById(caracteristicasPsiquicas.getId())
            .map(existingCaracteristicasPsiquicas -> {
                if (caracteristicasPsiquicas.getNome() != null) {
                    existingCaracteristicasPsiquicas.setNome(caracteristicasPsiquicas.getNome());
                }

                return existingCaracteristicasPsiquicas;
            })
            .map(caracteristicasPsiquicasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CaracteristicasPsiquicas> findAll(Pageable pageable) {
        log.debug("Request to get all CaracteristicasPsiquicas");
        return caracteristicasPsiquicasRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CaracteristicasPsiquicas> findOne(Long id) {
        log.debug("Request to get CaracteristicasPsiquicas : {}", id);
        return caracteristicasPsiquicasRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CaracteristicasPsiquicas : {}", id);
        caracteristicasPsiquicasRepository.deleteById(id);
    }
}
