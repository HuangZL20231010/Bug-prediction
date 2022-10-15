<template>
  <div style="background-color: #f2f3f5;height: 90vh">
    <div class="father">
      <div class="modelChoose" v-if="!isAlreadUpload">
        <el-select v-model="modelName"
                   clearable
                   size="large"
                   style="margin-top: 10px"
                   placeholder="请选择模型">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </div>

      <div class="upLoadArea" v-if="!isAlreadUpload">
        <el-upload
            class="upload-demo"
            ref="upload"
            :action="doUpload"
            :limit="1"
            :before-upload="beforeUpload"
        >
          <div class="buttondiv">
            <el-button type="primary" style="height: 200px;width: 200px;border-radius: 50%;" slot="trigger">
              <el-icon style="vertical-align: middle"><Search /></el-icon>
              <span style="vertical-align: middle;font-size: 20px"> 上传文件 </span>
            </el-button>
          </div>
        </el-upload>
        <div slot="tip" class="tip">只能上传csv文件</div>
      </div>

      <div class="successArea" v-if="isAlreadUpload">
        <!--          <div slot="tip" class="file-name">fileName</div>-->
        <el-result
            icon="success"
            title="上传成功"
            sub-title="点击下载结果"
        >
          <template #extra>
            <el-button type="primary" @click="importTemplate">获取结果</el-button>
          </template>
        </el-result>
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "modelTrainView",
  data() {
    return {
      options: [{
        value: '选项1',
        label: '逻辑回归'
      }, {
        value: '选项2',
        label: 'K近邻'
      }],

      modelName:'',
      files:'',
      fileName:'',
      filePath:'',
      isAlreadUpload:false,

    }
  },

  methods:{
    beforeUpload(file){
      if(this.modelName===''){
        alert('请先选择模型，再上传文件！');
        return;
      }
      this.files = file;
      const extension = file.name.split('.')[1] === 'csv'
      if (!extension) {
        this.$message.warning('上传模板只能是csv格式!')
        return
      }
      this.fileName = file.name;
      // console.log(this.fileName)
      this.submitUpload();
    },

    doUpload(){

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
      // let requestConfig = {
      //   headers: {
      //     'Content-Type': 'multipart/form-data'
      //   },
      // }
      let modelNO='';
      if(this.modelName==='选项1')modelNO='1';
      else modelNO='2';
      fileFormData.append('model',modelNO)

      const _this=this
      console.log("正在axios")
      axios.post('http://localhost:9090/prediction/systemPrediction', fileFormData )
      // axios({
      //   method:'post',
      //   url:'http://localhost:9090/prediction/systemPrediction',
      //
      //   params:{fileFormData}
      //
      // })
          .then((res) => {
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


    importTemplate() {
      axios({
        url: 'http://localhost:9090/prediction/getFile',
        method: 'get',
        params: {filePath: this.filePath},
        responseType: 'arraybuffer'
      }).then(res => {
        var fileName = "result"
        const blob = new Blob([res.data]);
        let a = document.createElement("a"); //创建一个<a></a>标签
        a.href = URL.createObjectURL(blob); // 将流文件写入a标签的href属性值
        a.download = fileName + ".csv"; //设置文件名
        a.style.display = "none"; // 障眼法藏起来a标签
        document.body.appendChild(a); // 将a标签追加到文档对象中
        a.click(); // 模拟点击了a标签，会触发a标签的href的读取，浏览器就会自动下载了
        a.remove(); // 一次性的，用完就删除a标签
      })

    }
  }
}
</script>

<style scoped>
.father{
  width: 80%;
  height: 650px;
  margin-left: 10%;
  margin-top: 15px;
  background-color: #c9ebf6;
  position: absolute;

  border-radius: 30px;
  border:1px solid #eeeeee;
  box-shadow: darkgrey 0px 0px 20px 5px;
}

.modelChoose{
  /*background-color: #409dfd;*/
  position: absolute;
  width: 100%;
  height: 100px;
  margin-top: 5%;
}

.upLoadArea{
  /*background-color: #ee0e0e;*/
  position: relative;
  width: 80%;
  height: 65%;
  margin-left: 10%;
  margin-top: 180px;

  border-radius: 30px;
  border:2px dashed darkgray;
}

.buttondiv{
  /*background: aquamarine;*/
  width: auto;
  height: auto;

  position: absolute;
  top: 15%;
}


.tip{
  /*background: #409dfd;*/
  width: 100%;
  height: auto;
  position: absolute;
  top: 70%;

  font-size: 12px;
  color: #5e6673;
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