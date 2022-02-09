package com.example.demo.repository;

import com.example.demo.entity.BankEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class BankCustomRepoImpl implements BankCustomRepo {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<BankEntity> findAndSortByCriteria(String criteriaName, String criteriaValue, String orderBy, String orderDirection) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(BankEntity.class); //
        Root<BankEntity> bankEntityRoot = cq.from(BankEntity.class);

        String[] longFields={"id"};
        Predicate criteriaPredicate;
        if(!criteriaName.isEmpty()){
            if(Arrays.asList(longFields).contains(criteriaName))
                criteriaPredicate = cb.equal(bankEntityRoot.get(criteriaName),criteriaValue);
            else
                criteriaPredicate = cb.like(bankEntityRoot.get(criteriaName),"%"+criteriaValue+"%");
            cq.where(criteriaPredicate);
        }
        if(orderBy!="") {
            switch (orderDirection) {
                case "asc":
                    cq.orderBy(cb.asc(bankEntityRoot.get(orderBy)));
                    break;
                case "desc":
                    cq.orderBy(cb.desc(bankEntityRoot.get(orderBy)));
                    break;
            }
        }
        TypedQuery<BankEntity> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
