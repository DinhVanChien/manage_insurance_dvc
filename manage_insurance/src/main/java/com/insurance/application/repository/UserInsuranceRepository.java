package com.insurance.application.repository;

import com.insurance.application.entity.User;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserInsuranceRepository {

    private EntityManager entityManager;

    @Autowired
    public UserInsuranceRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int getTotalUser(int companyId, String name, String insuranceNumber, String placeOfRegister) {
        try {
            StringBuilder sql = new StringBuilder();
            Map<String, String> listPram = new HashMap<String, String>();
            sql.append("SELECT COUNT(u.insurance_id) FROM USERS u");
            sql.append(" JOIN INSURANCE i");
            sql.append(" ON u.insurance_id = i.id");
            sql.append(" WHERE u.company_id = :companyId");
            if (!StringUtils.isBlank(name)) {
                sql.append(" AND LOWER(u.user_full_name) LIKE LOWER(:name)");
                listPram.put("name", Common.sqlLike(name));
            }
            if (!StringUtils.isBlank(insuranceNumber)) {
                sql.append(" AND LOWER(i.insurance_number) LIKE LOWER(:insuranceNumber)");
                listPram.put("insuranceNumber", Common.sqlLike(insuranceNumber));
            }
            if (!StringUtils.isBlank(placeOfRegister)) {
                sql.append(" AND LOWER(i.place_of_register) LIKE LOWER(:placeOfRegister)");
                listPram.put("placeOfRegister", Common.sqlLike(placeOfRegister));
            }
            Query query = (Query) entityManager.createNativeQuery(sql.toString());
            query.setParameter("companyId", companyId);
            for (Map.Entry<String, String> entry : listPram.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            Number totalUser = (Number) (query.uniqueResult());
            return totalUser.intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public List<User> getListUser(int companyId, String name,
                                  String insuranceNumber,
                                  String placeOfRegister,
                                  String sortName,
                                  String sortInNum,
                                  String sortCreateDate,
                                  int page) {
        int offset = Common.getOffset(page, Constant.DEFAULT_RECORD_PAGE);
        try {
            StringBuilder sql = new StringBuilder();
            Map<String, String> listPram = new HashMap<String, String>();
            sql.append("SELECT u.* FROM USERS u");
            sql.append(" JOIN INSURANCE i");
            sql.append(" ON u.insurance_id = i.id");
            sql.append(" WHERE u.company_id = :companyId");
            if (!StringUtils.isBlank(name)) {
                sql.append(" AND LOWER(u.user_full_name) LIKE LOWER(:name)");
                listPram.put("name", Common.sqlLike(name));
            }
            if (!StringUtils.isBlank(insuranceNumber)) {
                sql.append(" AND LOWER(i.insurance_number) LIKE LOWER(:insuranceNumber)");
                listPram.put("insuranceNumber", Common.sqlLike(insuranceNumber));
            }
            if (!StringUtils.isBlank(placeOfRegister)) {
                sql.append(" AND LOWER(i.place_of_register) LIKE LOWER(:placeOfRegister)");
                listPram.put("placeOfRegister", Common.sqlLike(placeOfRegister));
            }
            if(sortName == null && sortInNum == null) {
                sql.append(" ORDER BY u.CREATE_DATE ").append(sortCreateDate);
            } else {
                if(sortName != null) {
                    sql.append(" ORDER BY u.user_full_name ").append(sortName);
                } else {
                    sql.append(" ORDER BY i.insurance_number ").append(sortInNum);
                }
            }

            // nếu là export csv thì sẽ ko lấy theo dk offset và limit
            if (page != 0) {
                sql.append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT ");
                sql.append(Constant.DEFAULT_RECORD_PAGE).append(" ROWS ONLY");
            }
            Query query = (Query) entityManager.createNativeQuery(sql.toString(), User.class);
            query.setParameter("companyId", companyId);
            for (Map.Entry<String, String> entry : listPram.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public User getUser( String insuranceNumber, String fullName) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT u.* FROM USERS u");
            sql.append(" JOIN INSURANCE i");
            sql.append(" ON u.insurance_id = i.id");
            sql.append(" WHERE u.user_full_name = :fullName");
            sql.append(" AND i.insurance_number = :insuranceNumber");
            Query query = (Query) entityManager.createNativeQuery(sql.toString(), User.class);
            query.setParameter("fullName", fullName);
            query.setParameter("insuranceNumber", insuranceNumber);
            return (User) query.uniqueResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
