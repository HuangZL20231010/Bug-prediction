package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.UserTrainInfoService;
import com.example.demo.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
//        Result<String> result = new Result<>();
//        result.setCode("0");
//        result.setData(null);
        String result;

        /* 如果文件为空,返回对应的错误信息 */
        if (null == uploadFile) {
//            result.setMsg("Didn't get file!");
            result = "Didn't get file!";
            return result;
        }

        /* 如果文件不是csv格式,返回对应的错误信息 */
        String fileName = Objects.requireNonNull(uploadFile.getOriginalFilename()).toLowerCase();
        if (!fileName.endsWith(".csv")) {
            // result.setMsg("The file format is incorrect. Upload the CSV file!");
            result = "The file format is incorrect. Upload the CSV file!";
            return result;
        }

        /* 将该csv文件存储到本地 */
        if (!userTrainInfoService.storeFile(uploadFile)) {
            // result.setMsg("Failed to save the file locally!");
            result = "Failed to save the file locally!";
            return result;
        }

        /* 处理该csv文件,得到训练后的csv文件路径 */
        String sourceFilePath = Global.resourcesPath + "uploadFiles/" + fileName;
        String destinationFilePath = userTrainInfoService.systemPrediction(sourceFilePath, fileName);
        return destinationFilePath;
    }
}
