package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.table.User;
import com.example.demo.service.KNN;
import com.example.demo.service.UserTrainInfoService;
import com.example.demo.utils.Global;
import com.example.demo.utils.MatrixOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
//import wniemiec.util.data.Pair;

@Service
public class UserTrainInfoServiceImpl implements UserTrainInfoService {

    private LogisticRegressionImpl user_logisticRegression;
    @Autowired
    private KNN knn;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        File dest = new File(Global.resourcesPath + "uploadFiles/" + fileName);

        if (!dest.exists()) {
            try {
                if (!dest.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            file.transferTo(dest); // 保存文件
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] getFirstLineWord(String filePath) {
        File file = new File(filePath);
        FileReader fileReader = null;
        String[] words;

        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            words = line.split(",");

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return words;
    }

    @Override
    public String writeTwoArrayFile(String destinationPath, ArrayList<ArrayList<Double>> result) {
        File file = new File(destinationPath);

        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return "Error";
                }
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            int i, j;
            for(i = 0; i < result.size(); i++){
                for(j = 0; j < result.get(i).size(); j++){
                    bw.write(String.valueOf(result.get(i).get(j)));
                    bw.write(",");
                }
                System.out.println(j);
                bw.write('\n');
            }
            bw.close();
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return destinationPath;
    }

    @Override
    public String systemPredictionLogistic(String filePath, String fileName) {

        /* 使用系统逻辑回归模型预测数据 */
        LogisticRegressionImpl logisticRegression=new LogisticRegressionImpl(false,61);
        ArrayList<ArrayList<Double>> origin_data= FileProcessImpl.read_csv(filePath,true);
        ArrayList<ArrayList<Double>> all_features= MatrixOperation.converse(
                MatrixOperation.iloc(origin_data,0,0,origin_data.size()-1,origin_data.get(0).size()-2),
                true);
        ArrayList<ArrayList<Double>> result=logisticRegression.predict(all_features);

        /* 将训练结果存到本地 */
        String destinationPath = Global.resourcesPath + "predictionFiles/SystemLogistic_" + fileName;

        return writeTwoArrayFile(destinationPath, result);
    }

    @Override
    public String systemPredictionKNN(String filePath, String fileName) {
        /* 使用系统逻辑回归模型预测数据 */
        ArrayList<ArrayList<Double>> result = knn.systemPrediction(filePath);

        /* 将训练结果存到本地 */
        String destinationPath = Global.resourcesPath + "predictionFiles/SystemKNN_" + fileName;

        return writeTwoArrayFile(destinationPath, result);
    }

    @Override
    public Pair<ArrayList<ArrayList<Double>>, Double> userDefinedTrainLogistic(String filePath, Integer epochNum, Integer batchSize,
                                                                               Double learningrate){
        //根据用户上传的训练集训练出一个模型
        ArrayList<ArrayList<Double>> user_origin_data= FileProcessImpl.read_csv(filePath,true);
        ArrayList<ArrayList<Double>> user_all_features= MatrixOperation.converse(MatrixOperation.iloc(user_origin_data,0,0,user_origin_data.size()-1,user_origin_data.get(0).size()-2), false);
        ArrayList<ArrayList<Double>> user_all_labels=MatrixOperation.iloc(user_origin_data,0,user_origin_data.get(0).size()-1,user_origin_data.size()-1,user_origin_data.get(0).size()-1);
        user_logisticRegression=new LogisticRegressionImpl(false,user_all_features.get(0).size());
        user_logisticRegression.train(user_all_features,user_all_labels,epochNum,batchSize,learningrate);
        Pair<ArrayList<ArrayList<Double>>, Double> result = Pair.of(user_logisticRegression.weights,user_logisticRegression.getBias());


        return result;
    }

    @Override
    public Double userDefinedEvaluationLogistic(String filePath, String fileName, String userName) {

        //系统用来评估此模型的EvaluateData.csv数据集，
        //下面两行为系统读取EvaluateData.csv的真实标签
        ArrayList<ArrayList<Double>> evaluate_data=FileProcessImpl.read_csv(filePath,true);
        ArrayList<ArrayList<Double>> evaluate_label=MatrixOperation.iloc(evaluate_data,0,evaluate_data.get(0).size()-1,evaluate_data.size()-1,evaluate_data.get(0).size()-1);

        //用户需下载没有标签的EvaluateData_nolabel.csv并数据处理返给系统，系统将用自己之前训练的模型预测结果，并将其与真实标签evaluate_label做比较
        ArrayList<ArrayList<Double>> user_predict_data=FileProcessImpl.read_csv(filePath,true);
        ArrayList<ArrayList<Double>> user_predict_features=MatrixOperation.converse(MatrixOperation.iloc(user_predict_data,0,0,user_predict_data.size()-1,user_predict_data.get(0).size()-1),false);
        ArrayList<ArrayList<Double>> user_predict_result=user_logisticRegression.forward(user_predict_features);
        Double acc = user_logisticRegression.evaluate(user_predict_result,evaluate_label);

        // 更新数据库内容
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        User user = userMapper.selectOne(queryWrapper);
        user.setTrainnums(user.getTrainnums() + 1);

        if (acc > user.getMaxaccuracy()) {
            // 如果本次训练超过了以往训练效果,则更新对应字段
            user.setMaxaccuracy(acc);
        }

        userMapper.update(user, queryWrapper);

        // 保存训练文件
        String destinationPath = Global.resourcesPath + "predictionFiles/UserDefinedLogistic_" + fileName;

        return acc;
    }

}