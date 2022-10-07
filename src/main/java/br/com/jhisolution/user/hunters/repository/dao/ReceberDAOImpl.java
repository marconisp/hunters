package br.com.jhisolution.user.hunters.repository.dao;

import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
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
public class ReceberDAOImpl implements ReceberDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Receber> findAllByCriteria(FiltroReceberDTO filtro) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Receber> query = cb.createQuery(Receber.class);

        Root<Receber> root = query.from(Receber.class);
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

        if (Objects.nonNull(filtro.getReceberDe())) {
            predicates.add(cb.equal(root.get("receberDe"), filtro.getReceberDe()));
        }

        if (Objects.nonNull(filtro.getTipoReceber())) {
            predicates.add(cb.equal(root.get("tipoReceber"), filtro.getTipoReceber()));
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
