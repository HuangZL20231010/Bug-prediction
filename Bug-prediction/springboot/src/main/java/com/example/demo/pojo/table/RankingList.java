package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("rankinglist")
@Data
public class RankingList {
    Integer userid;

    Integer trainnums;

    Integer rank;

    Integer maxscore;

    public Integer getRank() {
        return rank;
    }

    public Integer getMaxscore() {
        return maxscore;
    }

    public Integer getTrainnums() {
        return trainnums;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setMaxscore(Integer maxscore) {
        this.maxscore = maxscore;
    }

    public void setTrainnums(Integer trainnums) {
        this.trainnums = trainnums;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


}
