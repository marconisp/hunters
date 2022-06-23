package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.User1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the User1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface User1Repository extends JpaRepository<User1, Long> {}
