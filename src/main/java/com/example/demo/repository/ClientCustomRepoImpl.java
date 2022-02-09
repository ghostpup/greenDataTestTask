package com.example.demo.repository;

import com.example.demo.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class ClientCustomRepoImpl implements ClientCustomRepo {

    @Autowired
    EntityManager entityManager;

    public List<ClientEntity> findAndSortByCriteria(String criteriaName, String criteriaValue, String orderBy, String orderDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(ClientEntity.class); //
        Root<ClientEntity> clientEntityRoot = cq.from(ClientEntity.class);

        String[] longFields={"id","orgForm"};
        Predicate criteriaPredicate;
        if(!criteriaName.isEmpty()){
            if(Arrays.asList(longFields).contains(criteriaName))
                criteriaPredicate = cb.equal(clientEntityRoot.get(criteriaName),criteriaValue);
            else
                criteriaPredicate = cb.like(clientEntityRoot.get(criteriaName),"%"+criteriaValue+"%");
            cq.where(criteriaPredicate);
        }
        if(orderBy!="") {
            switch (orderDirection) {
                case "asc":
                    cq.orderBy(cb.asc(clientEntityRoot.get(orderBy)));
                    break;
                case "desc":
                    cq.orderBy(cb.desc(clientEntityRoot.get(orderBy)));
                    break;
            }
        }
        TypedQuery<ClientEntity> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
