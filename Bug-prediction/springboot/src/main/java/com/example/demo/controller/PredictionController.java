package com.example.demo.controller;

import com.example.demo.service.UserTrainInfoService;
import com.example.demo.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private UserTrainInfoService userTrainInfoService;

    /* 系统预测,接收前端传来的csv文件,返回训练文件 */
    @RequestMapping(value = "/systemPrediction", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("uploadFile")MultipartFile uploadFile) {

        String result;

        /* 如果文件为空,返回对应的错误信息 */
        if (null == uploadFile) {
            result = "Didn't get file!";
            return result;
        }

        /* 如果文件不是csv格式,返回对应的错误信息 */
        String fileName = Objects.requireNonNull(uploadFile.getOriginalFilename()).toLowerCase();
        if (!fileName.endsWith(".csv")) {
            result = "The file format is incorrect. Upload the CSV file!";
            return result;
        }

        /* 将该csv文件存储到本地 */
        if (!userTrainInfoService.storeFile(uploadFile)) {
            result = "Failed to save the file locally!";
            return result;
        }

        /* 处理该csv文件,得到训练后的csv文件路径 */
        String sourceFilePath = Global.resourcesPath + "uploadFiles/" + fileName;
        return userTrainInfoService.systemPrediction(sourceFilePath, fileName);
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    @ResponseBody
    public void sendFile(HttpServletResponse response, @RequestParam("filePath") String filePath) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            //获取文件存放的路径
            File file = new File(filePath);
            String fileName = file.getName();

            if(!file.exists()) {
                //如果文件不存在就跳出
                return;
            }

            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(StandardCharsets.UTF_8),"ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
    }
//    response.setCharacterEncoding("UTF-8");
//        // attachment是以附件的形式下载，inline是浏览器打开
//        response.setHeader("Content-Disposition", "inline;filename=" + filePath);
//        response.setContentType("text/plain;UTF-8");
//        // 把二进制流放入到响应体中
//        ServletOutputStream os = response.getOutputStream();
//        File file = new File(filePath);
//        FileReader reader = new FileReader(file);
//
//        os.write(reader.read());
//        os.flush();
//        os.close();

}
