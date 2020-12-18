package com.sake.sakecloud.mapper;

import com.sake.sakecloud.entity.FileStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileStoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file_store
     *
     * @mbggenerated
     */
    int insert(FileStore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_file_store
     *
     * @mbggenerated
     */
    int insertSelective(FileStore record);

    void updateUserCurrentSizeBy(@Param("username") String username,@Param("currentSize") Double currentSize);
}