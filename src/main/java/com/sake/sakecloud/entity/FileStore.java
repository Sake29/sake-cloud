package com.sake.sakecloud.entity;

public class FileStore {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_store.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_store.USERID
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_store.CURRENT_SIZE
     *
     * @mbggenerated
     */
    private Double currentSize;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_store.MAX_SIZE
     *
     * @mbggenerated
     */
    private Double maxSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_file_store.ID
     *
     * @return the value of t_file_store.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_file_store.ID
     *
     * @param id the value for t_file_store.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_file_store.USERID
     *
     * @return the value of t_file_store.USERID
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_file_store.USERID
     *
     * @param userid the value for t_file_store.USERID
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_file_store.CURRENT_SIZE
     *
     * @return the value of t_file_store.CURRENT_SIZE
     *
     * @mbggenerated
     */
    public Double getCurrentSize() {
        return currentSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_file_store.CURRENT_SIZE
     *
     * @param currentSize the value for t_file_store.CURRENT_SIZE
     *
     * @mbggenerated
     */
    public void setCurrentSize(Double currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_file_store.MAX_SIZE
     *
     * @return the value of t_file_store.MAX_SIZE
     *
     * @mbggenerated
     */
    public Double getMaxSize() {
        return maxSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_file_store.MAX_SIZE
     *
     * @param maxSize the value for t_file_store.MAX_SIZE
     *
     * @mbggenerated
     */
    public void setMaxSize(Double maxSize) {
        this.maxSize = maxSize;
    }
}