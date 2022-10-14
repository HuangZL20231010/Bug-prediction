package com.example.demo.pojo.table;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    String username;

    String password;

    String email;

    Integer trainnums;

    Double maxaccuracy;

    String maxaccuracymodel;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTrainnums() {
        return trainnums;
    }

    public void setTrainnums(Integer trainnums) {
        this.trainnums = trainnums;
    }

    public Double getMaxaccuracy() {
        return maxaccuracy;
    }

    public String getMaxaccuracymodel() {
        return maxaccuracymodel;
    }

    public void setMaxaccuracy(Double maxaccuracy) {
        this.maxaccuracy = maxaccuracy;
    }

    public void setMaxaccuracymodel(String maxaccuracymodel) {
        this.maxaccuracymodel = maxaccuracymodel;
    }
}
