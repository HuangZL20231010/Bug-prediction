package com.example.demo.controller;

import com.example.demo.service.UserTrainInfoService;
import com.example.demo.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import wniemiec.util.data.Pair;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private UserTrainInfoService userTrainInfoService;

    @RequestMapping(value = "/userDefinedPrediction2")
    public String userDefinedPrediction(@RequestParam("uploadFile") MultipartFile uploadFile,
                                        @RequestParam("username") String username) {
        // 得到文件的名字
        String fileName = Objects.requireNonNull(uploadFile.getOriginalFilename()).toLowerCase();
        // 将该csv文件存储到本地
        userTrainInfoService.storeFile(uploadFile);
        /* 处理该csv文件,得到训练后的csv文件路径 */
        String sourceFilePath = Global.resourcesPath + "uploadFiles/" + fileName;

        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(userTrainInfoService.userDefinedEvaluationLogistic(Global.resourcesPath + "uploadFiles/" + fileName, fileName, username));
    }

    @RequestMapping(value = "/userDefinedPrediction", method = RequestMethod.POST)
    @ResponseBody
    public Pair<String[], Pair<ArrayList<ArrayList<Double>>, Double>> userDefinedPrediction(
            @RequestParam("uploadFile") MultipartFile uploadFile,
            @RequestParam("epochNum") Integer epochNum,
            @RequestParam("batchSize") Integer batchSize,
            @RequestParam("learningrate") Double learningrate) {

        String fileName = Objects.requireNonNull(uploadFile.getOriginalFilename()).toLowerCase();

//        /* 如果文件为空,返回对应的错误信息 */
//        if (null == uploadFile) {
//            result.setMsg("Didn't get file!");
//            return result;
//        }
//
//        /* 如果文件不是csv格式,返回对应的错误信息 */
//        if (!fileName.endsWith(".csv")) {
//            result.setMsg("The file format is incorrect. Upload the CSV file!");
//            return result;
//        }
//
//        /* 将该csv文件存储到本地 */
//        if (!userTrainInfoService.storeFile(uploadFile)) {
//            result.setMsg("Failed to save the file locally!");
//            return result;
//        }


        // 将该csv文件存储到本地
        userTrainInfoService.storeFile(uploadFile);
        /* 处理该csv文件,得到训练后的csv文件路径 */
        String sourceFilePath = Global.resourcesPath + "uploadFiles/" + fileName;

        // 获得参数名列表和结果
        String[] words = userTrainInfoService.getFirstLineWord(sourceFilePath);
        Pair<ArrayList<ArrayList<Double>>, Double> result = userTrainInfoService.userDefinedTrainLogistic(sourceFilePath, epochNum, batchSize, learningrate);

        // 保留四位小数
        for (ArrayList<Double> doubles : result.getFirst()) {
            for (Double aDouble : doubles) {
                aDouble = (double)Math.round(aDouble * 10000.0) / 10000;
            }
        }


        return Pair.of(words, result);
    }

    /* 系统预测,接收前端传来的csv文件,返回训练文件 */
    @RequestMapping(value = "/systemPrediction", method = RequestMethod.POST)
    @ResponseBody
    public String systemPrediction(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("model") String model) {

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
        if (Objects.equals(model, "1"))
            return userTrainInfoService.systemPredictionLogistic(sourceFilePath, fileName);
        else if (Objects.equals(model, "2"))
            return userTrainInfoService.systemPredictionKNN(sourceFilePath, fileName);
        else
            return "Model Wrong!";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    @ResponseBody
    public void sendFile(HttpServletResponse response, @RequestParam("filePath") String filePath) {
        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            //获取文件存放的路径
            File file = new File(filePath);
            String fileName = file.getName();

            if (!file.exists()) {
                //如果文件不存在就跳出
                return;
            }

            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/getEvaluateFile", method = RequestMethod.GET)
    @ResponseBody
    public void getEvaluateFile(HttpServletResponse response) {
        String filePath = Global.resourcesPath + "EvaluateData.csv";
        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            //获取文件存放的路径
            File file = new File(filePath);
            String fileName = file.getName();

            if (!file.exists()) {
                //如果文件不存在就跳出
                return;
            }

            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
    }

}
