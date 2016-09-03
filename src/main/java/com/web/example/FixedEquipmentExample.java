package com.web.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拼接 机柜查询SQL语句辅助类
 *
 * @author 杜延雷
 * @date 2016-08-25
 */
public class FixedEquipmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FixedEquipmentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEquNameIsNull() {
            addCriterion("equ_name is null");
            return (Criteria) this;
        }

        public Criteria andEquNameIsNotNull() {
            addCriterion("equ_name is not null");
            return (Criteria) this;
        }

        public Criteria andEquNameEqualTo(String value) {
            addCriterion("equ_name =", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameNotEqualTo(String value) {
            addCriterion("equ_name <>", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameGreaterThan(String value) {
            addCriterion("equ_name >", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameGreaterThanOrEqualTo(String value) {
            addCriterion("equ_name >=", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameLessThan(String value) {
            addCriterion("equ_name <", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameLessThanOrEqualTo(String value) {
            addCriterion("equ_name <=", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameLike(String value) {
            addCriterion("equ_name like", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameNotLike(String value) {
            addCriterion("equ_name not like", value, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameIn(List<String> values) {
            addCriterion("equ_name in", values, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameNotIn(List<String> values) {
            addCriterion("equ_name not in", values, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameBetween(String value1, String value2) {
            addCriterion("equ_name between", value1, value2, "equName");
            return (Criteria) this;
        }

        public Criteria andEquNameNotBetween(String value1, String value2) {
            addCriterion("equ_name not between", value1, value2, "equName");
            return (Criteria) this;
        }

        public Criteria andEquTypeIsNull() {
            addCriterion("equ_type is null");
            return (Criteria) this;
        }

        public Criteria andEquTypeIsNotNull() {
            addCriterion("equ_type is not null");
            return (Criteria) this;
        }

        public Criteria andEquTypeEqualTo(String value) {
            addCriterion("equ_type =", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeNotEqualTo(String value) {
            addCriterion("equ_type <>", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeGreaterThan(String value) {
            addCriterion("equ_type >", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeGreaterThanOrEqualTo(String value) {
            addCriterion("equ_type >=", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeLessThan(String value) {
            addCriterion("equ_type <", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeLessThanOrEqualTo(String value) {
            addCriterion("equ_type <=", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeLike(String value) {
            addCriterion("equ_type like", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeNotLike(String value) {
            addCriterion("equ_type not like", value, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeIn(List<String> values) {
            addCriterion("equ_type in", values, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeNotIn(List<String> values) {
            addCriterion("equ_type not in", values, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeBetween(String value1, String value2) {
            addCriterion("equ_type between", value1, value2, "equType");
            return (Criteria) this;
        }

        public Criteria andEquTypeNotBetween(String value1, String value2) {
            addCriterion("equ_type not between", value1, value2, "equType");
            return (Criteria) this;
        }

        public Criteria andEquVendorIsNull() {
            addCriterion("equ_vendor is null");
            return (Criteria) this;
        }

        public Criteria andEquVendorIsNotNull() {
            addCriterion("equ_vendor is not null");
            return (Criteria) this;
        }

        public Criteria andEquVendorEqualTo(String value) {
            addCriterion("equ_vendor =", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorNotEqualTo(String value) {
            addCriterion("equ_vendor <>", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorGreaterThan(String value) {
            addCriterion("equ_vendor >", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorGreaterThanOrEqualTo(String value) {
            addCriterion("equ_vendor >=", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorLessThan(String value) {
            addCriterion("equ_vendor <", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorLessThanOrEqualTo(String value) {
            addCriterion("equ_vendor <=", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorLike(String value) {
            addCriterion("equ_vendor like", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorNotLike(String value) {
            addCriterion("equ_vendor not like", value, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorIn(List<String> values) {
            addCriterion("equ_vendor in", values, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorNotIn(List<String> values) {
            addCriterion("equ_vendor not in", values, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorBetween(String value1, String value2) {
            addCriterion("equ_vendor between", value1, value2, "equVendor");
            return (Criteria) this;
        }

        public Criteria andEquVendorNotBetween(String value1, String value2) {
            addCriterion("equ_vendor not between", value1, value2, "equVendor");
            return (Criteria) this;
        }

        public Criteria andRsoPathIsNull() {
            addCriterion("rso_path is null");
            return (Criteria) this;
        }

        public Criteria andRsoPathIsNotNull() {
            addCriterion("rso_path is not null");
            return (Criteria) this;
        }

        public Criteria andRsoPathEqualTo(String value) {
            addCriterion("rso_path =", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathNotEqualTo(String value) {
            addCriterion("rso_path <>", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathGreaterThan(String value) {
            addCriterion("rso_path >", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathGreaterThanOrEqualTo(String value) {
            addCriterion("rso_path >=", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathLessThan(String value) {
            addCriterion("rso_path <", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathLessThanOrEqualTo(String value) {
            addCriterion("rso_path <=", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathLike(String value) {
            addCriterion("rso_path like", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathNotLike(String value) {
            addCriterion("rso_path not like", value, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathIn(List<String> values) {
            addCriterion("rso_path in", values, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathNotIn(List<String> values) {
            addCriterion("rso_path not in", values, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathBetween(String value1, String value2) {
            addCriterion("rso_path between", value1, value2, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andRsoPathNotBetween(String value1, String value2) {
            addCriterion("rso_path not between", value1, value2, "rsoPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathIsNull() {
            addCriterion("max_path is null");
            return (Criteria) this;
        }

        public Criteria andMaxPathIsNotNull() {
            addCriterion("max_path is not null");
            return (Criteria) this;
        }

        public Criteria andMaxPathEqualTo(String value) {
            addCriterion("max_path =", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathNotEqualTo(String value) {
            addCriterion("max_path <>", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathGreaterThan(String value) {
            addCriterion("max_path >", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathGreaterThanOrEqualTo(String value) {
            addCriterion("max_path >=", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathLessThan(String value) {
            addCriterion("max_path <", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathLessThanOrEqualTo(String value) {
            addCriterion("max_path <=", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathLike(String value) {
            addCriterion("max_path like", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathNotLike(String value) {
            addCriterion("max_path not like", value, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathIn(List<String> values) {
            addCriterion("max_path in", values, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathNotIn(List<String> values) {
            addCriterion("max_path not in", values, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathBetween(String value1, String value2) {
            addCriterion("max_path between", value1, value2, "maxPath");
            return (Criteria) this;
        }

        public Criteria andMaxPathNotBetween(String value1, String value2) {
            addCriterion("max_path not between", value1, value2, "maxPath");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusMsgIsNull() {
            addCriterion("status_msg is null");
            return (Criteria) this;
        }

        public Criteria andStatusMsgIsNotNull() {
            addCriterion("status_msg is not null");
            return (Criteria) this;
        }

        public Criteria andStatusMsgEqualTo(String value) {
            addCriterion("status_msg =", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgNotEqualTo(String value) {
            addCriterion("status_msg <>", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgGreaterThan(String value) {
            addCriterion("status_msg >", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgGreaterThanOrEqualTo(String value) {
            addCriterion("status_msg >=", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgLessThan(String value) {
            addCriterion("status_msg <", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgLessThanOrEqualTo(String value) {
            addCriterion("status_msg <=", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgLike(String value) {
            addCriterion("status_msg like", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgNotLike(String value) {
            addCriterion("status_msg not like", value, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgIn(List<String> values) {
            addCriterion("status_msg in", values, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgNotIn(List<String> values) {
            addCriterion("status_msg not in", values, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgBetween(String value1, String value2) {
            addCriterion("status_msg between", value1, value2, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusMsgNotBetween(String value1, String value2) {
            addCriterion("status_msg not between", value1, value2, "statusMsg");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("create_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("create_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("create_name =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("create_name <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("create_name >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_name >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("create_name <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("create_name <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("create_name like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("create_name not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("create_name in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("create_name not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("create_name between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("create_name not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIsNull() {
            addCriterion("update_name is null");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIsNotNull() {
            addCriterion("update_name is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateNameEqualTo(String value) {
            addCriterion("update_name =", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotEqualTo(String value) {
            addCriterion("update_name <>", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameGreaterThan(String value) {
            addCriterion("update_name >", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameGreaterThanOrEqualTo(String value) {
            addCriterion("update_name >=", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLessThan(String value) {
            addCriterion("update_name <", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLessThanOrEqualTo(String value) {
            addCriterion("update_name <=", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLike(String value) {
            addCriterion("update_name like", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotLike(String value) {
            addCriterion("update_name not like", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIn(List<String> values) {
            addCriterion("update_name in", values, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotIn(List<String> values) {
            addCriterion("update_name not in", values, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameBetween(String value1, String value2) {
            addCriterion("update_name between", value1, value2, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotBetween(String value1, String value2) {
            addCriterion("update_name not between", value1, value2, "updateName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}