<template>
  <div style="background-color: #f2f3f5;height: 380vh">
    <div class="father">
      <div class="upLoadArea" v-if="!isAlreadUpload">
        <div class="modelChoose" v-if="!isAlreadUpload" >
          <div id="select">
            <el-select v-model="modelName"
                       clearable size="large"
                       placeholder="请选择模型"
                       @change="handleChange"
                       >
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

          <div id="LogicalCaoCan">
            <el-form-item label="轮次数">
              <el-input-number v-model="epochNum" :max="100" :min="0"  :disabled="!chooseLogical"/>
            </el-form-item>
            <el-form-item label="批量数">
              <el-input-number v-model="batchSize"  :step="1" :max="100" :min="0"  :disabled="!chooseLogical"/>
            </el-form-item>
            <el-form-item label="学习率"  >
              <el-input-number v-model="alpha" :precision="3" :step="0.1" :max="10" :min="0" :disabled="!chooseLogical"/>
            </el-form-item>
          </div>

          <div id="KCaoCan">
            <el-form-item label="K 值">
              <el-input-number v-model="KValue" :max="100" :min="0" :disabled="!chooseK"/>
            </el-form-item>
            <el-form-item label="权重">
              <el-input-number v-model="wise"  :precision="3" :step="0.1" :max="10" :min="0" :disabled="!chooseK"/>
            </el-form-item>
          </div>

<!--          <div style="margin-top: 10px">超参选择表单  </div>-->
        </div>
        <el-upload
            class="upload-demo"
            ref="upload"
            action="doUpload"
            :limit="1"
            :file-list="fileList"
            :before-upload="beforeUpload_train"
        >
          <div class="buttondiv" style="top:40%;width: auto">
            <el-button type="primary" style="height: 200px;width: 200px;border-radius: 50%;" slot="trigger">
              <el-icon style="vertical-align: middle"><Search /></el-icon>
              <span style="vertical-align: middle;font-size: 20px"> 上传文件 </span>
            </el-button>
          </div>
        </el-upload>
        <div slot="tip" class="tip">只能上传csv文件</div>
      </div>

      <div class="dowloadArea" v-if="!isAlreadDowload">
        <div class="buttondiv">
          <el-button type="primary"
                     style="height: 200px;width: 200px;border-radius: 50%;margin-right: 5vw"
                     @click="downLoadTestSet" >
            <el-icon style="vertical-align: middle"><Search /></el-icon>
            <span style="vertical-align: middle;font-size: 20px"> 下载测试集 </span>
          </el-button>



          <el-button type="primary"
                     style="height: 200px;width: 200px;border-radius: 50%;margin-left: 5vw"
                     @click="upLoadTestSet" >
            <el-icon style="vertical-align: middle"><Search /></el-icon>
            <span style="vertical-align: middle;font-size: 20px"> 上传测试集 </span>
          </el-button>
        </div>
        <div>
          <div slot="tip" class="tip" style="top: 80%">注释</div>
        </div>
      </div>

    </div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "selfTrainView",
  data() {
    return {
      username:'',
      options: [{
        value: '逻辑回归',
        label: '逻辑回归'
      }, {
        value: 'K近邻',
        label: 'K近邻'
      }],

      modelName:'',
      files:'',
      fileName:'',
      filePath:'',
      isAlreadUpload:false,
      epochNum:'5',
      batchSize:'1',
      alpha:'',
      KValue:'',
      wise:'',

      chooseLogical:false,
      chooseK:false,
    }
  },

  created() {
    // console.log(this.GLOBAL.token)

    console.log("username:"+sessionStorage.getItem('username'));
    console.log(sessionStorage.getItem('222'));
  },

  methods:{
    handleChange(val){
      console.log(val);
      if(val=='逻辑回归'){
        this.chooseLogical=true;
        this.chooseK=false;
      }else if(val=='K近邻'){
        this.chooseLogical=false;
        this.chooseK=true;
      }else{
        this.chooseLogical=false;
        this.chooseK=false;
      }
    },

    beforeUpload_train(file){
      this.files = file;
      const extension3 = file.name.split('.')[1] === 'csv'
      if (!extension3) {
        this.$message.warning('上传模板只能是csv格式!')
        return
      }
      this.fileName = file.name;
      console.log(this.fileName)
      this.submitUpload();
    },


    submitUpload() {
      console.log('正在上传'+this.fileName)
      if(this.fileName === ""){
        this.$message.warning('请选择要上传的文件！')
        return false
      }

      let fileFormData = new FormData();
      fileFormData.append('uploadFile', this.files);//uploadFile是键，files是值，就是要传的文件，test.zip是要传的文件名
      // fileFormData.append('uploadFile', this.files, this.fileName,);//filename是键，file是值，就是要传的文件，test.zip是要传的文件名
      let modelNO='';
      if(this.modelName==='选项1')modelNO='1';
      else modelNO='2';
      fileFormData.append('model',modelNO);
      fileFormData.append('epochNum',this.epochNum);
      fileFormData.append('batchSize',this.batchSize);
      fileFormData.append('learningrate',this.alpha);
      fileFormData.append('username',this.username);

      const _this=this
      console.log("正在axios")
      axios.post('http://localhost:9090/prediction/systemPrediction', fileFormData).then((res) => {
        // console.log(res)
        if (res.data) {
          console.log(res.data);
          if(res.data!=0){
            _this.filePath=res.data;
            _this.isAlreadUpload=true;
          }

          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
          })
        } else {
          console.log("fk")
          this.$message.error(res.data.msg)
        }
      })
    },

    downLoadTestSet(){
      axios({
        url: 'http://localhost:9090/prediction/getEvaluateFile',
        method: 'get',
        responseType: 'arraybuffer'
      }).then(res => {
        var fileName = "TestSet"
        const blob = new Blob([res.data]);
        let a = document.createElement("a"); //创建一个<a></a>标签
        a.href = URL.createObjectURL(blob); // 将流文件写入a标签的href属性值
        a.download = fileName + ".csv"; //设置文件名
        a.style.display = "none"; // 障眼法藏起来a标签
        document.body.appendChild(a); // 将a标签追加到文档对象中
        a.click(); // 模拟点击了a标签，会触发a标签的href的读取，浏览器就会自动下载了
        a.remove(); // 一次性的，用完就删除a标签
      })
    },


  }
}
</script>

<style scoped>
.father{
  width: 80%;
  height: 250vh;
  margin-left: 10%;
  margin-top: 15px;
  background-color: #c9ebf6;
  position: absolute;

  border-radius: 30px;
  border:1px solid #eeeeee;
  box-shadow: darkgrey 0px 0px 20px 5px;
}

.dowloadArea{
  /*background-color: #ee0e0e;*/
  position: relative;
  width: 80%;
  height: 50vh;
  margin-left: 10%;
  top: 20vh;

  border-radius: 30px;
  border:2px dashed darkgray;
}


.buttondiv{
  /*background: aquamarine;*/
  width: 100%;
  height: auto;

  position: absolute;
  top: 15%;
}




.upLoadArea{
  /*background-color: #cb7878;*/
  position: relative;
  width: 80%;
  height: 70vh;
  margin-left: 10%;
  top: 10vh;

  border-radius: 30px;
  border:2px dashed darkgray;
}

.modelChoose{
  /*background-color: #409dfd;*/
  position: absolute;
  width: 100%;
  height: 22vh;
  margin-top: 3%;
  display: flex;
  justify-content: center;
}

.tip{
  /*background: #409dfd;*/
  width: 100%;
  height: auto;
  position: absolute;
  top: 80%;

  font-size: 12px;
  color: #5e6673;
}

#select{
  /*background-color: #d91616;*/
  margin-top: 6vh;
  margin-right: 3vw;
}

#LogicalCaoCan{
  /*background: #861bbe;*/
  margin-right: 2vw;
}

#KCaoCan{
  /*background-color: #861bbe;*/
  margin-top: 3vh;
  /*display: flex; !**!*/
  /*justify-content: center; !*水平居中*!*/
  align-items: Center; /*垂直居中*/
}

.file-name{
  /*font-size: 15px;*/
  position: absolute;
  width: 100%;
  top:80%;

}
.successArea{
  /*background-color: red;*/
  position: absolute;
  width: 100%;
  top:15%;
}
</style>


