<template>
  <div class="common-layout">
    <el-container>

<!--      头部-->
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px"
            ><setting
            /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="gotoLogin">退出登录</el-dropdown-item>
                <el-dropdown-item>修改信息</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="font-size: 16px">霖</span>
        </div>
      </el-header>



<!--      导航栏-->
      <el-container  class="layout-container-demo">
        <el-aside width="200px">
          <el-scrollbar>
            <el-menu router :default-openeds="['1']">
<!--              <el-sub-menu v-for="(item1,index1) in $router.options.routes" :index="index1+''">-->
<!--                <template #title>{{item1.name}}</template>-->
<!--                <el-menu-item v-for="(item2,index2) in item1.children" :index="item2.path" :class="$route.path==item2.path?'is-active':''">-->
<!--                  {{item2.name}}-->
<!--                </el-menu-item>-->
<!--              </el-sub-menu>-->
              <el-sub-menu index="1">
                <template #title>模型</template>
                <el-menu-item index="/downloadView" class="$route.path==‘/downloadView’?'is-active':''">下载</el-menu-item>
                <el-menu-item index="/modelTrainView" class="$route.path==‘/modelTrainView’?'is-active':''">训练</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="2">
                <template #title>数据</template>
                <el-menu-item index="/historyDataView" class="$route.path==‘/historyDataView’?'is-active':''">历史数据</el-menu-item>
                <el-menu-item index="/rankingView" class="$route.path==‘/rankingView’?'is-active':''">排行榜</el-menu-item>
              </el-sub-menu>

            </el-menu>
          </el-scrollbar>
        </el-aside>



<!--        主体-->
        <el-container>
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>


      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "index",
    data(){
      return{

      }
    },
    methods:{
      gotoLogin(){
        this.$router.push('/');
      }
    }
  }
</script>

<!--<style>-->
<!--.el-header, .el-footer {-->
<!--  background-color: #B3C0D1;-->
<!--  color: #333;-->
<!--  text-align: center;-->
<!--  line-height: 60px;-->
<!--}-->

<!--.el-aside {-->
<!--  background-color: #D3DCE6;-->
<!--  color: #333;-->
<!--  text-align: center;-->
<!--  line-height: 200px;-->
<!--}-->

<!--.el-main {-->
<!--  background-color: #E9EEF3;-->
<!--  color: #333;-->
<!--  text-align: center;-->
<!--  line-height: 160px;-->
<!--}-->

<!--body > .el-container {-->
<!--  margin-bottom: 40px;-->
<!--}-->

<!--.el-container:nth-child(5) .el-aside,-->
<!--.el-container:nth-child(6) .el-aside {-->
<!--  line-height: 260px;-->
<!--}-->

<!--.el-container:nth-child(7) .el-aside {-->
<!--  line-height: 320px;-->
<!--}-->
<!--</style>-->

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>