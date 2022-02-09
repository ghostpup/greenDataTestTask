package com.example.demo.repository;

import com.example.demo.entity.DepositEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class DepositCustomRepoImpl implements DepositCustomRepo {

    @Autowired
    EntityManager entityManager;

    public List<DepositEntity> findAndSortByCriteria(String criteriaName, String criteriaValue, String orderBy, String orderDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(DepositEntity.class); //
        Root<DepositEntity> depositEntityRoot = cq.from(DepositEntity.class);

//        String[] longFields={"id"};
        Predicate criteriaPredicate;
        if(!criteriaName.isEmpty()){
//            if(Arrays.asList(longFields).contains(criteriaName))
                criteriaPredicate = cb.equal(depositEntityRoot.get(criteriaName),criteriaValue);
//            else
//                criteriaPredicate = cb.like(depositEntityRoot.get(criteriaName),"%"+criteriaValue+"%");
            cq.where(criteriaPredicate);
        }
        if(orderBy!="") {
            switch (orderDirection) {
                case "asc":
                    cq.orderBy(cb.asc(depositEntityRoot.get(orderBy)));
                    break;
                case "desc":
                    cq.orderBy(cb.desc(depositEntityRoot.get(orderBy)));
                    break;
            }
        }
        TypedQuery<DepositEntity> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
