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
                <el-dropdown-item>View</el-dropdown-item>
                <el-dropdown-item>Add</el-dropdown-item>
                <el-dropdown-item>Delete</el-dropdown-item>
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
<!--            <el-menu :default-openeds="['1', '2']">-->
<!--              <el-sub-menu index="1">-->
<!--                <template #title>-->
<!--                  Navigator One-->
<!--                </template>-->
<!--                <el-menu-item-group>-->
<!--                  <el-menu-item index="1-1">Option 1</el-menu-item>-->
<!--                  <el-menu-item index="1-2">Option 2</el-menu-item>-->
<!--                </el-menu-item-group>-->
<!--              </el-sub-menu>-->
<!--            </el-menu>-->
            <el-menu router :default-openeds="['0','1']">
              <el-sub-menu v-for="(item1,index1) in $router.options.routes" :index="index1+''">
                <template #title>
                  {{item1.name}}
                </template>
                <el-menu-item v-for="(item2,index2) in item1.children" :index="item2.path" :class="$route.path==item2.path?'is-active':''">
                  {{item2.name}}
                </el-menu-item>

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
  name: "index"
}
</script>


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