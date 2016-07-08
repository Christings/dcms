package com.web.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拼装菜单数据库语句对象
 *
 * @author 杜延雷
 * @date 2016-07-08
 */
public class MenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Short value) {
            addCriterion("LEVEL =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Short value) {
            addCriterion("LEVEL <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Short value) {
            addCriterion("LEVEL >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("LEVEL >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Short value) {
            addCriterion("LEVEL <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Short value) {
            addCriterion("LEVEL <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Short> values) {
            addCriterion("LEVEL in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Short> values) {
            addCriterion("LEVEL not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Short value1, Short value2) {
            addCriterion("LEVEL between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Short value1, Short value2) {
            addCriterion("LEVEL not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("RANK is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("RANK is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Short value) {
            addCriterion("RANK =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Short value) {
            addCriterion("RANK <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Short value) {
            addCriterion("RANK >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Short value) {
            addCriterion("RANK >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Short value) {
            addCriterion("RANK <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Short value) {
            addCriterion("RANK <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Short> values) {
            addCriterion("RANK in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Short> values) {
            addCriterion("RANK not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Short value1, Short value2) {
            addCriterion("RANK between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Short value1, Short value2) {
            addCriterion("RANK not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("URL is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("URL is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("URL =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("URL <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("URL >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("URL >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("URL <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("URL <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("URL like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("URL not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("URL in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("URL not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("URL between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("URL not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("PARENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("PARENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("PARENT_ID =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("PARENT_ID <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("PARENT_ID >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_ID >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("PARENT_ID <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_ID <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("PARENT_ID like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("PARENT_ID not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("PARENT_ID in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("PARENT_ID not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("PARENT_ID between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_ID not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andIconIdIsNull() {
            addCriterion("ICON_ID is null");
            return (Criteria) this;
        }

        public Criteria andIconIdIsNotNull() {
            addCriterion("ICON_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIconIdEqualTo(String value) {
            addCriterion("ICON_ID =", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotEqualTo(String value) {
            addCriterion("ICON_ID <>", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdGreaterThan(String value) {
            addCriterion("ICON_ID >", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_ID >=", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLessThan(String value) {
            addCriterion("ICON_ID <", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLessThanOrEqualTo(String value) {
            addCriterion("ICON_ID <=", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLike(String value) {
            addCriterion("ICON_ID like", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotLike(String value) {
            addCriterion("ICON_ID not like", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdIn(List<String> values) {
            addCriterion("ICON_ID in", values, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotIn(List<String> values) {
            addCriterion("ICON_ID not in", values, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdBetween(String value1, String value2) {
            addCriterion("ICON_ID between", value1, value2, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotBetween(String value1, String value2) {
            addCriterion("ICON_ID not between", value1, value2, "iconId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Short value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Short value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Short value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Short value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Short value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Short> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Short> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Short value1, Short value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Short value1, Short value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIsNull() {
            addCriterion("UPDATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIsNotNull() {
            addCriterion("UPDATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateNameEqualTo(String value) {
            addCriterion("UPDATE_NAME =", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotEqualTo(String value) {
            addCriterion("UPDATE_NAME <>", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameGreaterThan(String value) {
            addCriterion("UPDATE_NAME >", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_NAME >=", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLessThan(String value) {
            addCriterion("UPDATE_NAME <", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_NAME <=", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameLike(String value) {
            addCriterion("UPDATE_NAME like", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotLike(String value) {
            addCriterion("UPDATE_NAME not like", value, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameIn(List<String> values) {
            addCriterion("UPDATE_NAME in", values, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotIn(List<String> values) {
            addCriterion("UPDATE_NAME not in", values, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameBetween(String value1, String value2) {
            addCriterion("UPDATE_NAME between", value1, value2, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateNameNotBetween(String value1, String value2) {
            addCriterion("UPDATE_NAME not between", value1, value2, "updateName");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateIsNull() {
            addCriterion("UPDATE_CREATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateIsNotNull() {
            addCriterion("UPDATE_CREATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateEqualTo(Date value) {
            addCriterion("UPDATE_CREATE =", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateNotEqualTo(Date value) {
            addCriterion("UPDATE_CREATE <>", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateGreaterThan(Date value) {
            addCriterion("UPDATE_CREATE >", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_CREATE >=", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateLessThan(Date value) {
            addCriterion("UPDATE_CREATE <", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_CREATE <=", value, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateIn(List<Date> values) {
            addCriterion("UPDATE_CREATE in", values, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateNotIn(List<Date> values) {
            addCriterion("UPDATE_CREATE not in", values, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_CREATE between", value1, value2, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andUpdateCreateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_CREATE not between", value1, value2, "updateCreate");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("CREATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("CREATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("CREATE_NAME =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("CREATE_NAME <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("CREATE_NAME >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("CREATE_NAME <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("CREATE_NAME like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("CREATE_NAME not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("CREATE_NAME in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("CREATE_NAME not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("CREATE_NAME between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("CREATE_NAME not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
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