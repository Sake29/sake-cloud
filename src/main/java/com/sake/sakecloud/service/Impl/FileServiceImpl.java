package com.sake.sakecloud.service.Impl;

import com.sake.sakecloud.mapper.FileStoreMapper;
import com.sake.sakecloud.service.FileService;
import com.sake.sakecloud.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    FileUtil fileUtil;
    @Autowired
    FileStoreMapper fileStoreMapper;

    @Override
    public Boolean deleteFileBy(String filePath,String username) {
        String userFilePath = fileUtil.getUserFilePath(username);
        //用户最大容量
        Double maxSize = fileUtil.getMAX_SIZE();
        File file = new File(filePath);
        boolean res = FileUtils.deleteQuietly(file);
        if (res){
            //已使用容量
            double currentSize = (double)FileUtils.sizeOf(new File(userFilePath));
            //剩余容量
            double remainder = maxSize - currentSize;
            log.info(username + "已用容量为：" + currentSize/(1024*1024) + "MB，剩余容量为：" + remainder/(1024*1024) + "MB");
            //写入数据库
            fileStoreMapper.updateUserCurrentSizeBy(username,currentSize);
        }
        return res;
    }
}
