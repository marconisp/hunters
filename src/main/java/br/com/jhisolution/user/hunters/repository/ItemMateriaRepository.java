package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.ItemMateria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ItemMateria entity.
 */
@Repository
public interface ItemMateriaRepository extends JpaRepository<ItemMateria, Long> {
    default Optional<ItemMateria> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ItemMateria> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ItemMateria> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct itemMateria from ItemMateria itemMateria left join fetch itemMateria.materia",
        countQuery = "select count(distinct itemMateria) from ItemMateria itemMateria"
    )
    Page<ItemMateria> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct itemMateria from ItemMateria itemMateria left join fetch itemMateria.materia")
    List<ItemMateria> findAllWithToOneRelationships();

    @Query("select itemMateria from ItemMateria itemMateria left join fetch itemMateria.materia where itemMateria.id =:id")
    Optional<ItemMateria> findOneWithToOneRelationships(@Param("id") Long id);
}
