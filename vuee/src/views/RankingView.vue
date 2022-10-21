<template>
  <div style="background-color: #f2f3f5;height: 90vh;overflow: hidden">
    <div class="father">
      <div id="cup" @click="handleclick">

      </div>

      <div id="rank">
        <div id="header">
          <div class="number">
            排名
          </div>
          <div class="userName">
            用户名
          </div>
          <div class="modelChosen">
            训练次数
          </div>
          <div class="accuracy">
            准确率
          </div>
        </div>
        <div id="userCard" v-for="(item,index) in rankSet">
          <div class="number">
            {{index+1}}
          </div>
          <div class="userName">
            {{item.username}}
          </div>
          <div class="modelChosen">
            {{item.trainnums}}
          </div>
          <div class="accuracy">
            {{item.maxaccuracy}}
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios";
import anime from 'animejs/lib/anime.es.js';

export default {
  name: "rankingView",
  data() {
    return {
      rankSet:[]
    }
  },

  created() {
    axios({
      url:'http://localhost:9090/rank/orderByAccuracy',
      method:'get',
    }).then(res=>{
      console.log(res.data)
      this.rankSet=res.data;

    }).catch(err=>{
      console.log(err);
    })
  },
  mounted() {

    this.fatherAnimation=anime({
      targets:".father",

      translateY:'80vh',
      opacity:0,
      during:1500,
      direction:'reverse',
      easing:'easeInQuad',
    });

    this.headerAnimation=anime({
      targets: ['#rank'],
      keyframes:[
        {width:0, opacity:0},
        {width:'70%', opacity:1}
      ],
      duration: 1500,
      easing: 'easeInQuad'
    });


  },

  methods:{
    handleclick(){
      this.headerAnimation.play()
    }
  }
}
</script>

<style scoped>
.father{
  width: 80%;
  height: 80vh;
  margin-left: 10%;
  top: 15px;
  background-color: #ddf6fa;
  position: relative;

  border-radius: 30px;
  border:1px solid #eeeeee;
  box-shadow: darkgrey 0px 0px 20px 5px;
  display: flex;


}
#cup{
  /*background-color: #d91616;*/
  background-image: url('../assets/3.png');
  background-size: 100%;
  background-repeat: no-repeat;

  /*position: absolute;*/
  width: 20%;
  height: 50%;
  margin-top: 10%;
  margin-left: 12%;
}

#rank{

  /*background-color: #cb7878;*/
  /*position: absolute;*/
  width: 0;
  opacity:0;
  height: 100%;
}

#header{
  background-color: rgba(147, 209, 218, 0.73);
  position: relative;
  margin-top: 1.5vh;
  left: 17%;

  width: 66%;
  height: 8.5vh;
  line-height: 8.5vh;
  border-radius: 30px;
  display: flex;
  /*transition: All 0.4s ease-in-out;*/
  /*animation:myfirst 5s;*/
}


#userCard{
  background-color: #ffffff;
  position: relative;
  margin-top: 2px;
  left: 20%;

  width: 60%;
  height: 6.6vh;
  line-height: 6.6vh;
  border-radius: 25px;

  display: flex;
  box-shadow: darkgrey 0px 0px 10px 4px;

  transition: All 0.4s ease-in-out;
}

#userCard:hover{
  transform: scale(1.2) translate(0,-10px);
}


.number{
  /*background-color: #d91616;*/
  width: 20%;
  border-right: 1px dashed darkgray;


  font-family: 微软雅黑,serif;
}

.userName{
  /*background-color: #efa8a8;*/
  width: 30%;
  border-right: 1px dashed darkgray;
}

.modelChosen{
  /*background-color: #d91616;*/
  width: 30%;
  border-right: 1px dashed darkgray;
}
.accuracy{
  /*background-color: #b2f0fd;*/
  width: 20%;
}
.upLoadArea{
  /*background-color: #cb7878;*/
  position: relative;
  width: 80%;
  height: 70vh;
  margin-left: 10%;
  top: 10vh;

  border-radius: 30px;
  border:1px solid darkgray;
}


</style>


