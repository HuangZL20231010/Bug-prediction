<template>
  <!--    头部-->
  <div  style="height: 80px;line-height: 50px;border-bottom: 1px solid #ccc;display:flex">
    <div style="width: 250px;padding-left: 30px;padding-top:10px;font-size:22px;font-weight: bold;color: cornflowerblue">软件缺陷测试系统</div>
    <div style="flex: 1"> </div>
    <div style="width: 480px;padding-top: 10px; ">
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          style="height: 70px;font-size:20px;font-weight: bolder"
          router
      >
<!--        <el-menu-item index="/downloadView" class="$route.path==‘/downloadView’?'is-active':''">数据集下载</el-menu-item>-->
        <el-menu-item index="/modelTrainView" class="$route.path==‘/modelTrainView’?'is-active':''">指定参数预测</el-menu-item>
        <el-menu-item index="/selfTrainView" class="$route.path==‘/selfTrainView’?'is-active':''">自定义参数预测</el-menu-item>
        <el-menu-item index="/rankingView" class="$route.path==‘/rankingView’?'is-active':''">排行榜</el-menu-item>
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px"><setting/></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="gotoLogin">退出登录</el-dropdown-item>
<!--                <el-dropdown-item>修改信息</el-dropdown-item>-->
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="font-size: 16px">霖</span>
        </div>
      </el-menu>



    </div>
  </div>


<!--  主体-->
  <div>
    <router-view></router-view>
  </div>
</template>

<!--<template>-->
<!--  <el-container>-->
<!--    <el-header style="text-align: right; font-size: 12px">-->
<!--      <div class="toolbar">-->
<!--        <el-dropdown>-->
<!--          <el-icon style="margin-right: 8px; margin-top: 1px"-->
<!--          ><setting-->
<!--          /></el-icon>-->
<!--          <template #dropdown>-->
<!--            <el-dropdown-menu>-->
<!--              <el-dropdown-item @click="gotoLogin">退出登录</el-dropdown-item>-->
<!--              <el-dropdown-item>修改信息</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </template>-->
<!--        </el-dropdown>-->
<!--        <span style="font-size: 16px">霖</span>-->
<!--      </div>-->
<!--    </el-header>-->


<!--    <el-container>-->

<!--      <el-aside width="200px">-->
<!--        <el-scrollbar>-->
<!--          <el-menu router :default-openeds="['1']">-->
<!--            <el-sub-menu index="1">-->
<!--              <template #title>模型</template>-->
<!--              <el-menu-item index="/downloadView" class="$route.path==‘/downloadView’?'is-active':''">下载</el-menu-item>-->
<!--              <el-menu-item index="/modelTrainView" class="$route.path==‘/modelTrainView’?'is-active':''">训练</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--            <el-sub-menu index="2">-->
<!--              <template #title>数据</template>-->
<!--              <el-menu-item index="/historyDataView" class="$route.path==‘/historyDataView’?'is-active':''">历史数据</el-menu-item>-->
<!--              <el-menu-item index="/rankingView" class="$route.path==‘/rankingView’?'is-active':''">排行榜</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--          </el-menu>-->
<!--        </el-scrollbar>-->
<!--      </el-aside>-->



<!--      <el-main>-->
<!--        <router-view></router-view>-->
<!--      </el-main>-->

<!--    </el-container>-->
<!--  </el-container>-->

<!--</template>-->




<!--<template>-->
<!--  <div class="common-layout">-->
<!--    <el-container  class="layout-container-demo" style="height: 500px">-->

<!--&lt;!&ndash;      头部&ndash;&gt;-->
<!--      <el-header style="text-align: right; font-size: 12px">-->
<!--        <div class="toolbar">-->
<!--          <el-dropdown>-->
<!--            <el-icon style="margin-right: 8px; margin-top: 1px"-->
<!--            ><setting-->
<!--            /></el-icon>-->
<!--            <template #dropdown>-->
<!--              <el-dropdown-menu>-->
<!--                <el-dropdown-item @click="gotoLogin">退出登录</el-dropdown-item>-->
<!--                <el-dropdown-item>修改信息</el-dropdown-item>-->
<!--              </el-dropdown-menu>-->
<!--            </template>-->
<!--          </el-dropdown>-->
<!--          <span style="font-size: 16px">霖</span>-->
<!--        </div>-->
<!--      </el-header>-->



<!--&lt;!&ndash;      导航栏&ndash;&gt;-->
<!--      <el-container class="layout-container-demo" style="height: 500px">-->
<!--        <el-aside width="200px">-->
<!--          <el-scrollbar>-->
<!--            <el-menu router :default-openeds="['1']">-->
<!--&lt;!&ndash;              <el-sub-menu v-for="(item1,index1) in $router.options.routes" :index="index1+''">&ndash;&gt;-->
<!--&lt;!&ndash;                <template #title>{{item1.name}}</template>&ndash;&gt;-->
<!--&lt;!&ndash;                <el-menu-item v-for="(item2,index2) in item1.children" :index="item2.path" :class="$route.path==item2.path?'is-active':''">&ndash;&gt;-->
<!--&lt;!&ndash;                  {{item2.name}}&ndash;&gt;-->
<!--&lt;!&ndash;                </el-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;              </el-sub-menu>&ndash;&gt;-->
<!--              <el-sub-menu index="1">-->
<!--                <template #title>模型</template>-->
<!--                <el-menu-item index="/downloadView" class="$route.path==‘/downloadView’?'is-active':''">下载</el-menu-item>-->
<!--                <el-menu-item index="/modelTrainView" class="$route.path==‘/modelTrainView’?'is-active':''">训练</el-menu-item>-->
<!--              </el-sub-menu>-->
<!--              <el-sub-menu index="2">-->
<!--                <template #title>数据</template>-->
<!--                <el-menu-item index="/historyDataView" class="$route.path==‘/historyDataView’?'is-active':''">历史数据</el-menu-item>-->
<!--                <el-menu-item index="/rankingView" class="$route.path==‘/rankingView’?'is-active':''">排行榜</el-menu-item>-->
<!--              </el-sub-menu>-->

<!--            </el-menu>-->
<!--          </el-scrollbar>-->
<!--        </el-aside>-->



<!--&lt;!&ndash;        主体&ndash;&gt;-->
<!--          <el-main>-->
<!--            <router-view></router-view>-->
<!--          </el-main>-->
<!--      </el-container>-->
<!--    </el-container>-->
<!--  </div>-->
<!--</template>-->

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

<style>
.el-header{
  background-color: #c9ebf6;
  color: #000000;
  line-height: 60px;
}

.el-aside {
  background-color: #ffffff;
  color: #d91616;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #ffffff;
  color: #ce1616;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.toolbar {
  /*background-color: #ce1616;*/
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

</style>

<!--<style scoped>-->
<!--.layout-container-demo .el-header {-->
<!--  position: relative;-->
<!--  background-color: var(&#45;&#45;el-color-primary-light-7);-->
<!--  color: var(&#45;&#45;el-text-color-primary);-->
<!--}-->
<!--.layout-container-demo .el-aside {-->
<!--  color: var(&#45;&#45;el-text-color-primary);-->
<!--  background: var(&#45;&#45;el-color-primary-light-8);-->
<!--}-->
<!--.layout-container-demo .el-menu {-->
<!--  border-right: none;-->
<!--}-->
<!--.layout-container-demo .el-main {-->
<!--  padding: 0;-->
<!--}-->
<!--.layout-container-demo .toolbar {-->
<!--  display: inline-flex;-->
<!--  align-items: center;-->
<!--  justify-content: center;-->
<!--  height: 100%;-->
<!--  right: 20px;-->
<!--}-->
<!--</style>-->