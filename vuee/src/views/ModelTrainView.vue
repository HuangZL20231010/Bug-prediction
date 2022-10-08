<template>
  <div>
<!--    <el-button color="#626aef" type="primary" :icon="Edit" circle @click="loadup" />-->
    <el-upload
        class="upload-demo"
        ref="upload"
        action="doUpload"
        :limit="1"
        :file-list="fileList"
        :before-upload="beforeUpload">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
<!--      <a href="./static/moban.xlsx" rel="external nofollow" download="模板"><el-button size="small" type="success">下载模板</el-button></a>-->
      <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
      <div slot="tip" class="el-upload__tip">只能上传csv、excel文件</div>
      <div slot="tip" class="el-upload-list__item-name">{{fileName}}</div>
    </el-upload>


    <span slot="footer" class="dialog-footer">
     <el-button @click="visible = false">取消</el-button>
     <el-button type="primary" @click="submitUpload()">确定</el-button>
    </span>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "ModelTrainView",
  data(){
    return{
      files:{},
      filename:''

    }
},
  methods:{
    beforeUpload(file){
      this.files = file;
      const extension = file.name.split('.')[1] === 'xls'
      const extension2 = file.name.split('.')[1] === 'xlsx'
      const extension3 = file.name.split('.')[1] === 'csv'
      if (!extension && !extension2 && !extension3) {
        this.$message.warning('上传模板只能是 xls、xlsx、csv格式!')
        return
      }
      this.fileName = file.name;
      return false // 返回false不会自动上传
    },


  submitUpload() {
    console.log('上传'+this.files.name)
    if(this.fileName == ""){
      this.$message.warning('请选择要上传的文件！')
      return false
    }


    let fileFormData = new FormData();
    // fileFormData.append('uploadFile', $('#this.files')[0].files[0])
    fileFormData.append('uploadFile', this.files);//uploadFile是键，files是值，就是要传的文件，test.zip是要传的文件名
     // fileFormData.append('uploadFile', this.files, this.fileName,);//filename是键，file是值，就是要传的文件，test.zip是要传的文件名
    let requestConfig = {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
    }


    axios.post('http://localhost:9090/prediction/systemPrediction', fileFormData).then((res) => {
      console.log('连接到后端了')
      if (res.data && res.data.code === 0) {
        this.$message({
          message: '操作成功',
          type: 'success',
          duration: 1500,
          // onClose: () => {
          //   this.visible = false
          //   this.$emit('refreshDataList')
          // }
        })
      } else {
        console.log("fk")
        this.$message.error(res.data.msg)
      }
    })
  }


  }
}
</script>





<style scoped>

</style>
