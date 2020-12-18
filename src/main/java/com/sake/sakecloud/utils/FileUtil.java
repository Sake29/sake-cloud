package com.sake.sakecloud.utils;

import com.alibaba.fastjson.JSONObject;
import com.sake.sakecloud.entity.FileStore;
import com.sake.sakecloud.entity.User;
import com.sake.sakecloud.entity.vo.ZtreeNodeVo;
import com.sake.sakecloud.mapper.FileStoreMapper;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource(value = "application.yml")
@Data
public class FileUtil {

    /**
     * 每个用户的最大存储空间（1GB=1073741824bytes）
     */
    private final Double MAX_SIZE = 1073741824.0;

    @Value("${file.path}")
    private String filePath;
    @Autowired
    private FileStoreMapper fileStoreMapper;


    /**
     * 初始化存储空间
     *
     * @param user 用户
     */
    public void initStorageSpace(User user){
        String userFilePath = filePath+ "/" + user.getUsername();

        //创建文件夹
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdir();
        }
        File userFile = new File(userFilePath);
        if (!userFile.exists()){
            userFile.mkdir();
        }
        //将存储空间写到数据库中
        FileStore fileStore = new FileStore();
        fileStore.setUserid(user.getId());
        fileStore.setMaxSize(MAX_SIZE);
        fileStore.setCurrentSize(getDirectorySize(user.getUsername()));
        fileStoreMapper.insert(fileStore);
    }

    /**
     * 获取目录大小
     *
     * @param file 文件
     * @return long
     */
    private long getDirectorySize(File file){
        return FileUtils.sizeOf(file);
    }

    /**
     * 获取目录大小
     *
     * @param user 用户
     * @return long
     */
    public Double getDirectorySize(String user){
        String userFilePath = filePath+ "/" + user;
        File file = new File(userFilePath);
        return (double) getDirectorySize(file);
    }

    /**
     * 获取文件树目录
     *
     * @param userName 用户名
     * @return {@link List<ZtreeNodeVo>}
     */
    public List<ZtreeNodeVo> getTree(String userName){
        String rootPath = filePath + "/" + userName;
        List<ZtreeNodeVo> nodes = new ArrayList<>();
        File file = new File(rootPath);
        ZtreeNodeVo node = traverse(file);
        nodes.add(node);
        return nodes;
    }

    /**
     * 获取文件的树结构
     *
     * @param filePath 文件路径
     * @return {@link List<ZtreeNodeVo>}
     */
    public List<ZtreeNodeVo> getTreeOnlyFile(String filePath){
        File rootfile = new File(filePath);
        //获取当前路径下所有文件
        File[] files = rootfile.listFiles();
        List<ZtreeNodeVo> nodes = new ArrayList<>();
        //遍历文件
        for (File file : files) {
            if (file.isFile()){
                ZtreeNodeVo pathNodeVo = new ZtreeNodeVo();
                pathNodeVo.setId(file.getAbsolutePath());
                pathNodeVo.setName(file.getName());
                pathNodeVo.setPid(file.getParent());
                nodes.add(pathNodeVo);
            }
        }
        return nodes;
    }

    /**
     * 只获取文件夹的树结构
     *
     * @param userName 用户名
     * @return {@link List<ZtreeNodeVo>}
     */
    public List<ZtreeNodeVo> getTreeOnlyDictionary(String userName){
        String rootPath = filePath + "/" + userName;
        List<ZtreeNodeVo> nodes = new ArrayList<>();
        File file = new File(rootPath);
        ZtreeNodeVo node = new ZtreeNodeVo();
        if (file.isDirectory()){
            node = traverseOnlyDictionary(file);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 递归获取所有文件及文件夹
     *
     * @param file 文件
     * @return {@link ZtreeNodeVo}
     */
    private ZtreeNodeVo traverse(File file) {
        ZtreeNodeVo pathNodeVo = new ZtreeNodeVo();
        pathNodeVo.setId(file.getAbsolutePath());
        pathNodeVo.setName(file.getName());
        pathNodeVo.setPid(file.getParent());
        if (file.isDirectory()) {
            List<ZtreeNodeVo> subNodeVos = new ArrayList<>();
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                return pathNodeVo;
            }
            for (File subFile : subFiles) {
                ZtreeNodeVo subNodeVo = traverse(subFile);
                subNodeVos.add(subNodeVo);
            }
            pathNodeVo.setChildren(subNodeVos);
        }
        return pathNodeVo;
    }

    /**
     * 递归获取所有文件夹节点
     *
     * @param file 文件
     * @return {@link ZtreeNodeVo}
     */
    private ZtreeNodeVo traverseOnlyDictionary(File file) {
        ZtreeNodeVo pathNodeVo = new ZtreeNodeVo();
        pathNodeVo.setId(file.getAbsolutePath());
        pathNodeVo.setName(file.getName());
        pathNodeVo.setPid(file.getParent());
        if (file.isDirectory()) {
            List<ZtreeNodeVo> subNodeVos = new ArrayList<>();
            //获取该文件夹下的所有文件（包括子文件夹）
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                return pathNodeVo;
            }
            for (File subFile : subFiles) {
                if (subFile.isDirectory()){
                    ZtreeNodeVo subNodeVo = traverseOnlyDictionary(subFile);
                    subNodeVos.add(subNodeVo);
                }
            }
            pathNodeVo.setChildren(subNodeVos);
        }
        return pathNodeVo;
    }

    public String getUserFilePath(String username){
        return filePath+ "/" + username;
    }

    /**
     * 下载文件
     *
     * @param response 响应
     * @param bytes    字节
     * @param filePath 文件路径
     * @throws IOException ioexception
     */
    public void downloadFile(HttpServletResponse response, byte[] bytes, String filePath) throws IOException {
        int len = 0;
        InputStream is = new FileInputStream(filePath);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Type", "application/octet-stream");
        File tempFile = new File(filePath.trim());
        String fileName = tempFile.getName();
        String contentDisposition = "attachment;filename=" + fileName;
        response.addHeader("Content-Disposition", new String(contentDisposition.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream output = response.getOutputStream();
        while ((len = is.read(bytes)) != -1) {
            output.write(bytes, 0, len);
        }
        output.flush();
        output.close();
        is.close();
    }

    /**
     * 获取文件二进制流
     *
     * @param file
     * @return {@link byte[]}* @throws IOException
     */
    public byte[] getFileBytes(String file) throws IOException {
        File f = new File(file);
        FileInputStream in = new FileInputStream(f);
        int length = (int) f.length();
        byte[] bytes = new byte[length];
        in.read(bytes);
        in.close();
        return bytes;
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新名字
     * @return boolean
     */
    public HashMap renameFile(String filePath, String newName){
        HashMap res = new HashMap();
        File oldFile = new File(filePath);
        String parent = oldFile.getParent();
        String newFilePath = parent + "\\" + newName;
        File newFile = new File(newFilePath);
        if (newFile.exists()){
            res.put("status",0);
            res.put("msg","文件已存在！");
            return  res;
        }
        if (oldFile.renameTo(newFile)){
            res.put("status",1);
        }
        else {
            res.put("status",0);
            res.put("msg","未知的错误");
        }

        return res;
    }

    public Boolean renameCheck(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            return false;
        }
        return true;
    }

}
