package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.User1;
import br.com.jhisolution.user.hunters.repository.User1Repository;
import br.com.jhisolution.user.hunters.service.User1Service;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link User1}.
 */
@Service
@Transactional
public class User1ServiceImpl implements User1Service {

    private final Logger log = LoggerFactory.getLogger(User1ServiceImpl.class);

    private final User1Repository user1Repository;

    public User1ServiceImpl(User1Repository user1Repository) {
        this.user1Repository = user1Repository;
    }

    @Override
    public User1 save(User1 user1) {
        log.debug("Request to save User1 : {}", user1);
        return user1Repository.save(user1);
    }

    @Override
    public User1 update(User1 user1) {
        log.debug("Request to save User1 : {}", user1);
        return user1Repository.save(user1);
    }

    @Override
    public Optional<User1> partialUpdate(User1 user1) {
        log.debug("Request to partially update User1 : {}", user1);

        return user1Repository
            .findById(user1.getId())
            .map(existingUser1 -> {
                if (user1.getFirstName() != null) {
                    existingUser1.setFirstName(user1.getFirstName());
                }
                if (user1.getLastName() != null) {
                    existingUser1.setLastName(user1.getLastName());
                }
                if (user1.getEmail() != null) {
                    existingUser1.setEmail(user1.getEmail());
                }

                return existingUser1;
            })
            .map(user1Repository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User1> findAll(Pageable pageable) {
        log.debug("Request to get all User1s");
        return user1Repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User1> findOne(Long id) {
        log.debug("Request to get User1 : {}", id);
        return user1Repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User1 : {}", id);
        user1Repository.deleteById(id);
    }
}
