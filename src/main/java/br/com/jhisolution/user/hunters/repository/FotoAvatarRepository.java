package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoAvatar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoAvatarRepository extends JpaRepository<FotoAvatar, Long> {}
