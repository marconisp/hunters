package br.com.jhisolution.user.hunters.repository.dao;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class PagarDAOImpl implements PagarDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pagar> findAllByCriteria(FiltroPagarDTO filtro) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pagar> query = cb.createQuery(Pagar.class);

        Root<Pagar> root = query.from(Pagar.class);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(filtro.getDataInicio())) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("data"), filtro.getDataInicio()));
        }

        if (Objects.nonNull(filtro.getDataFim())) {
            predicates.add(cb.lessThanOrEqualTo(root.get("data"), filtro.getDataFim()));
        }

        if (Objects.nonNull(filtro.getStatus())) {
            predicates.add(cb.equal(root.get("status"), filtro.getStatus()));
        }

        if (Objects.nonNull(filtro.getPagarPara())) {
            predicates.add(cb.equal(root.get("pagarPara"), filtro.getPagarPara()));
        }

        if (Objects.nonNull(filtro.getTipoPagar())) {
            predicates.add(cb.equal(root.get("tipoPagar"), filtro.getTipoPagar()));
        }

        if (Objects.nonNull(filtro.getTransacao())) {
            predicates.add(cb.equal(root.get("tipoTransacao"), filtro.getTransacao()));
        }

        if (Objects.nonNull(filtro.getDadosPessoais())) {
            predicates.add(cb.equal(root.get("dadosPessoais"), filtro.getDadosPessoais()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
