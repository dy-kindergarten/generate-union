package com.reco.generate.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public SongExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andResidIsNull() {
            addCriterion("resid is null");
            return (Criteria) this;
        }

        public Criteria andResidIsNotNull() {
            addCriterion("resid is not null");
            return (Criteria) this;
        }

        public Criteria andResidEqualTo(String value) {
            addCriterion("resid =", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidNotEqualTo(String value) {
            addCriterion("resid <>", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidGreaterThan(String value) {
            addCriterion("resid >", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidGreaterThanOrEqualTo(String value) {
            addCriterion("resid >=", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidLessThan(String value) {
            addCriterion("resid <", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidLessThanOrEqualTo(String value) {
            addCriterion("resid <=", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidLike(String value) {
            addCriterion("resid like", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidNotLike(String value) {
            addCriterion("resid not like", value, "resid");
            return (Criteria) this;
        }

        public Criteria andResidIn(List<String> values) {
            addCriterion("resid in", values, "resid");
            return (Criteria) this;
        }

        public Criteria andResidNotIn(List<String> values) {
            addCriterion("resid not in", values, "resid");
            return (Criteria) this;
        }

        public Criteria andResidBetween(String value1, String value2) {
            addCriterion("resid between", value1, value2, "resid");
            return (Criteria) this;
        }

        public Criteria andResidNotBetween(String value1, String value2) {
            addCriterion("resid not between", value1, value2, "resid");
            return (Criteria) this;
        }

        public Criteria andCnameIsNull() {
            addCriterion("cname is null");
            return (Criteria) this;
        }

        public Criteria andCnameIsNotNull() {
            addCriterion("cname is not null");
            return (Criteria) this;
        }

        public Criteria andCnameEqualTo(String value) {
            addCriterion("cname =", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotEqualTo(String value) {
            addCriterion("cname <>", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameGreaterThan(String value) {
            addCriterion("cname >", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameGreaterThanOrEqualTo(String value) {
            addCriterion("cname >=", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLessThan(String value) {
            addCriterion("cname <", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLessThanOrEqualTo(String value) {
            addCriterion("cname <=", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLike(String value) {
            addCriterion("cname like", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotLike(String value) {
            addCriterion("cname not like", value, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameIn(List<String> values) {
            addCriterion("cname in", values, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotIn(List<String> values) {
            addCriterion("cname not in", values, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameBetween(String value1, String value2) {
            addCriterion("cname between", value1, value2, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameNotBetween(String value1, String value2) {
            addCriterion("cname not between", value1, value2, "cname");
            return (Criteria) this;
        }

        public Criteria andCnameLenIsNull() {
            addCriterion("cname_len is null");
            return (Criteria) this;
        }

        public Criteria andCnameLenIsNotNull() {
            addCriterion("cname_len is not null");
            return (Criteria) this;
        }

        public Criteria andCnameLenEqualTo(Integer value) {
            addCriterion("cname_len =", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenNotEqualTo(Integer value) {
            addCriterion("cname_len <>", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenGreaterThan(Integer value) {
            addCriterion("cname_len >", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenGreaterThanOrEqualTo(Integer value) {
            addCriterion("cname_len >=", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenLessThan(Integer value) {
            addCriterion("cname_len <", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenLessThanOrEqualTo(Integer value) {
            addCriterion("cname_len <=", value, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenIn(List<Integer> values) {
            addCriterion("cname_len in", values, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenNotIn(List<Integer> values) {
            addCriterion("cname_len not in", values, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenBetween(Integer value1, Integer value2) {
            addCriterion("cname_len between", value1, value2, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andCnameLenNotBetween(Integer value1, Integer value2) {
            addCriterion("cname_len not between", value1, value2, "cnameLen");
            return (Criteria) this;
        }

        public Criteria andAbbrIsNull() {
            addCriterion("abbr is null");
            return (Criteria) this;
        }

        public Criteria andAbbrIsNotNull() {
            addCriterion("abbr is not null");
            return (Criteria) this;
        }

        public Criteria andAbbrEqualTo(String value) {
            addCriterion("abbr =", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrNotEqualTo(String value) {
            addCriterion("abbr <>", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrGreaterThan(String value) {
            addCriterion("abbr >", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("abbr >=", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrLessThan(String value) {
            addCriterion("abbr <", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrLessThanOrEqualTo(String value) {
            addCriterion("abbr <=", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrLike(String value) {
            addCriterion("abbr like", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrNotLike(String value) {
            addCriterion("abbr not like", value, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrIn(List<String> values) {
            addCriterion("abbr in", values, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrNotIn(List<String> values) {
            addCriterion("abbr not in", values, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrBetween(String value1, String value2) {
            addCriterion("abbr between", value1, value2, "abbr");
            return (Criteria) this;
        }

        public Criteria andAbbrNotBetween(String value1, String value2) {
            addCriterion("abbr not between", value1, value2, "abbr");
            return (Criteria) this;
        }

        public Criteria andIdArtistIsNull() {
            addCriterion("id_artist is null");
            return (Criteria) this;
        }

        public Criteria andIdArtistIsNotNull() {
            addCriterion("id_artist is not null");
            return (Criteria) this;
        }

        public Criteria andIdArtistEqualTo(Integer value) {
            addCriterion("id_artist =", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistNotEqualTo(Integer value) {
            addCriterion("id_artist <>", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistGreaterThan(Integer value) {
            addCriterion("id_artist >", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_artist >=", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistLessThan(Integer value) {
            addCriterion("id_artist <", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistLessThanOrEqualTo(Integer value) {
            addCriterion("id_artist <=", value, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistIn(List<Integer> values) {
            addCriterion("id_artist in", values, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistNotIn(List<Integer> values) {
            addCriterion("id_artist not in", values, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistBetween(Integer value1, Integer value2) {
            addCriterion("id_artist between", value1, value2, "idArtist");
            return (Criteria) this;
        }

        public Criteria andIdArtistNotBetween(Integer value1, Integer value2) {
            addCriterion("id_artist not between", value1, value2, "idArtist");
            return (Criteria) this;
        }

        public Criteria andCartistIsNull() {
            addCriterion("cartist is null");
            return (Criteria) this;
        }

        public Criteria andCartistIsNotNull() {
            addCriterion("cartist is not null");
            return (Criteria) this;
        }

        public Criteria andCartistEqualTo(String value) {
            addCriterion("cartist =", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistNotEqualTo(String value) {
            addCriterion("cartist <>", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistGreaterThan(String value) {
            addCriterion("cartist >", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistGreaterThanOrEqualTo(String value) {
            addCriterion("cartist >=", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistLessThan(String value) {
            addCriterion("cartist <", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistLessThanOrEqualTo(String value) {
            addCriterion("cartist <=", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistLike(String value) {
            addCriterion("cartist like", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistNotLike(String value) {
            addCriterion("cartist not like", value, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistIn(List<String> values) {
            addCriterion("cartist in", values, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistNotIn(List<String> values) {
            addCriterion("cartist not in", values, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistBetween(String value1, String value2) {
            addCriterion("cartist between", value1, value2, "cartist");
            return (Criteria) this;
        }

        public Criteria andCartistNotBetween(String value1, String value2) {
            addCriterion("cartist not between", value1, value2, "cartist");
            return (Criteria) this;
        }

        public Criteria andVtypeIsNull() {
            addCriterion("vtype is null");
            return (Criteria) this;
        }

        public Criteria andVtypeIsNotNull() {
            addCriterion("vtype is not null");
            return (Criteria) this;
        }

        public Criteria andVtypeEqualTo(Integer value) {
            addCriterion("vtype =", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeNotEqualTo(Integer value) {
            addCriterion("vtype <>", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeGreaterThan(Integer value) {
            addCriterion("vtype >", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("vtype >=", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeLessThan(Integer value) {
            addCriterion("vtype <", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeLessThanOrEqualTo(Integer value) {
            addCriterion("vtype <=", value, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeIn(List<Integer> values) {
            addCriterion("vtype in", values, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeNotIn(List<Integer> values) {
            addCriterion("vtype not in", values, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeBetween(Integer value1, Integer value2) {
            addCriterion("vtype between", value1, value2, "vtype");
            return (Criteria) this;
        }

        public Criteria andVtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("vtype not between", value1, value2, "vtype");
            return (Criteria) this;
        }

        public Criteria andClanguageIsNull() {
            addCriterion("clanguage is null");
            return (Criteria) this;
        }

        public Criteria andClanguageIsNotNull() {
            addCriterion("clanguage is not null");
            return (Criteria) this;
        }

        public Criteria andClanguageEqualTo(Integer value) {
            addCriterion("clanguage =", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageNotEqualTo(Integer value) {
            addCriterion("clanguage <>", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageGreaterThan(Integer value) {
            addCriterion("clanguage >", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageGreaterThanOrEqualTo(Integer value) {
            addCriterion("clanguage >=", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageLessThan(Integer value) {
            addCriterion("clanguage <", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageLessThanOrEqualTo(Integer value) {
            addCriterion("clanguage <=", value, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageIn(List<Integer> values) {
            addCriterion("clanguage in", values, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageNotIn(List<Integer> values) {
            addCriterion("clanguage not in", values, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageBetween(Integer value1, Integer value2) {
            addCriterion("clanguage between", value1, value2, "clanguage");
            return (Criteria) this;
        }

        public Criteria andClanguageNotBetween(Integer value1, Integer value2) {
            addCriterion("clanguage not between", value1, value2, "clanguage");
            return (Criteria) this;
        }

        public Criteria andPicIsNull() {
            addCriterion("pic is null");
            return (Criteria) this;
        }

        public Criteria andPicIsNotNull() {
            addCriterion("pic is not null");
            return (Criteria) this;
        }

        public Criteria andPicEqualTo(String value) {
            addCriterion("pic =", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotEqualTo(String value) {
            addCriterion("pic <>", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThan(String value) {
            addCriterion("pic >", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThanOrEqualTo(String value) {
            addCriterion("pic >=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThan(String value) {
            addCriterion("pic <", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThanOrEqualTo(String value) {
            addCriterion("pic <=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLike(String value) {
            addCriterion("pic like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotLike(String value) {
            addCriterion("pic not like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicIn(List<String> values) {
            addCriterion("pic in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotIn(List<String> values) {
            addCriterion("pic not in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicBetween(String value1, String value2) {
            addCriterion("pic between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotBetween(String value1, String value2) {
            addCriterion("pic not between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andCsortIsNull() {
            addCriterion("csort is null");
            return (Criteria) this;
        }

        public Criteria andCsortIsNotNull() {
            addCriterion("csort is not null");
            return (Criteria) this;
        }

        public Criteria andCsortEqualTo(Integer value) {
            addCriterion("csort =", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortNotEqualTo(Integer value) {
            addCriterion("csort <>", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortGreaterThan(Integer value) {
            addCriterion("csort >", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortGreaterThanOrEqualTo(Integer value) {
            addCriterion("csort >=", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortLessThan(Integer value) {
            addCriterion("csort <", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortLessThanOrEqualTo(Integer value) {
            addCriterion("csort <=", value, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortIn(List<Integer> values) {
            addCriterion("csort in", values, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortNotIn(List<Integer> values) {
            addCriterion("csort not in", values, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortBetween(Integer value1, Integer value2) {
            addCriterion("csort between", value1, value2, "csort");
            return (Criteria) this;
        }

        public Criteria andCsortNotBetween(Integer value1, Integer value2) {
            addCriterion("csort not between", value1, value2, "csort");
            return (Criteria) this;
        }

        public Criteria andClineIsNull() {
            addCriterion("cline is null");
            return (Criteria) this;
        }

        public Criteria andClineIsNotNull() {
            addCriterion("cline is not null");
            return (Criteria) this;
        }

        public Criteria andClineEqualTo(Integer value) {
            addCriterion("cline =", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineNotEqualTo(Integer value) {
            addCriterion("cline <>", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineGreaterThan(Integer value) {
            addCriterion("cline >", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineGreaterThanOrEqualTo(Integer value) {
            addCriterion("cline >=", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineLessThan(Integer value) {
            addCriterion("cline <", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineLessThanOrEqualTo(Integer value) {
            addCriterion("cline <=", value, "cline");
            return (Criteria) this;
        }

        public Criteria andClineIn(List<Integer> values) {
            addCriterion("cline in", values, "cline");
            return (Criteria) this;
        }

        public Criteria andClineNotIn(List<Integer> values) {
            addCriterion("cline not in", values, "cline");
            return (Criteria) this;
        }

        public Criteria andClineBetween(Integer value1, Integer value2) {
            addCriterion("cline between", value1, value2, "cline");
            return (Criteria) this;
        }

        public Criteria andClineNotBetween(Integer value1, Integer value2) {
            addCriterion("cline not between", value1, value2, "cline");
            return (Criteria) this;
        }

        public Criteria andDtimeIsNull() {
            addCriterion("dtime is null");
            return (Criteria) this;
        }

        public Criteria andDtimeIsNotNull() {
            addCriterion("dtime is not null");
            return (Criteria) this;
        }

        public Criteria andDtimeEqualTo(Date value) {
            addCriterion("dtime =", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeNotEqualTo(Date value) {
            addCriterion("dtime <>", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeGreaterThan(Date value) {
            addCriterion("dtime >", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dtime >=", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeLessThan(Date value) {
            addCriterion("dtime <", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeLessThanOrEqualTo(Date value) {
            addCriterion("dtime <=", value, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeIn(List<Date> values) {
            addCriterion("dtime in", values, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeNotIn(List<Date> values) {
            addCriterion("dtime not in", values, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeBetween(Date value1, Date value2) {
            addCriterion("dtime between", value1, value2, "dtime");
            return (Criteria) this;
        }

        public Criteria andDtimeNotBetween(Date value1, Date value2) {
            addCriterion("dtime not between", value1, value2, "dtime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated do_not_delete_during_merge Sun May 05 11:55:33 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated Sun May 05 11:55:33 CST 2019
     */
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