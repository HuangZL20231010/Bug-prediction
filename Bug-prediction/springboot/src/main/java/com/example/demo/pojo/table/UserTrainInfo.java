package com.example.demo.pojo.table;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.sql.Date;

@TableName("usertraininfo")
@Data
public class UserTrainInfo {
    Integer trainid;

    Integer userid;

    String model;

    Date time;

    Double score;

    String detail;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getTime() {
        return time;
    }

    public Double getScore() {
        return score;
    }

    public Integer getTrainid() {
        return trainid;
    }

    public String getDetail() {
        return detail;
    }

    public String getModel() {
        return model;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTrainid(Integer trainid) {
        this.trainid = trainid;
    }

}
