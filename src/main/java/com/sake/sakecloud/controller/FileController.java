package com.sake.sakecloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.sake.sakecloud.service.FileService;
import com.sake.sakecloud.utils.FileUtil;
import com.sake.sakecloud.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     *
     * @param file 文件
     */
    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        if (file.isEmpty()){
            log.info("文件为空");
        }
        //文件名
        String filename = file.getOriginalFilename();
        //用户名
        String userName = (String)request.getSession().getAttribute("userName");

    }

    /**
     * 删除
     *
     * @param nodeId 节点id，即文件的绝对路径
     */
    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("nodeId") String nodeId,
                       @RequestParam("username") String username){
        System.out.println(nodeId);
        System.out.println(username);
        Boolean res = fileService.deleteFileBy(nodeId,username);
        if (res){
            log.info('"' + nodeId + '"' + "删除成功！");
            R.success("删除成功！");
        }
        else {
            log.info('"' + nodeId + '"' + "删除失败！");

        }

    }

    /**
     * 获取文件夹树
     *
     * @param userName 用户名
     * @return {@link Object}
     */
    @ResponseBody
    @GetMapping("/user")
    public Object getTreeOnlyDictionary(@RequestParam("userName") String userName) {
        return fileUtil.getTreeOnlyDictionary(userName);
    }

    /**
     * 获取指定文件夹下的文件树
     *
     * @param filePath
     * @return {@link Object}* @throws UnsupportedEncodingException
     */
    @ResponseBody
    @GetMapping("/details")
    public Object getTree(@RequestParam("filePath") String filePath) throws UnsupportedEncodingException {
        filePath = URLDecoder.decode(filePath, "UTF-8");
        log.info(filePath);
        return fileUtil.getTreeOnlyFile(filePath);
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {
        filePath = URLDecoder.decode(filePath,"UTF-8");
        byte[] fileBytes = fileUtil.getFileBytes(filePath);
        log.info("正在下载："+filePath);
        fileUtil.downloadFile(response,fileBytes,filePath);
        log.info("下载成功！");
    }

    @ResponseBody
    @PostMapping("/rename")
    public R renameFile(@RequestParam("filepath") String filepath,@RequestParam("newName") String newName) throws UnsupportedEncodingException {
        if (newName == null || newName.equals("")) return R.errorMessage("文件名不能为空！");
        filepath = URLDecoder.decode(filepath,"UTF-8");
        HashMap res = fileUtil.renameFile(filepath, newName);
        if ((Integer) res.get("status") == 0){
            String msg = (String) res.get("msg");
            log.info("重命名失败！原因："+msg);
            return R.errorMessage(msg);
        }
        log.info("重命名成功！");
        return R.success();
    }

}
