import { createRouter, createWebHistory } from 'vue-router'
import IndexView from "../views/IndexView.vue";
import DownloadView from "@/views/DownloadView";
import modelTrainView from "@/views/ModelTrainView";
import historyDataView from "@/views/HistoryDataView";
import rankingView from "@/views/RankingView";
import loginRegister from "@/views/LoginRegister";

const routes = [
  {
    path:'/',
    name:"登录注册",
    component: loginRegister
  },
  {
    path: '/navigation1',
    name: '模型',
    component: IndexView,
    redirect:"/downloadView",
    children:[
      {
        path: '/downloadView',
        name: '下载',
        component: DownloadView,
      },
      {
        path: '/modelTrainView',
        name: '训练',
        component: modelTrainView,
      }
    ]
  },
  {
    path: '/navigation2',
    name: '数据',
    component: IndexView,
    children:[
      {
        path: '/historyDataView',
        name: '历史数据',
        component: historyDataView,
      },
      {
        path: '/rankingView',
        name: '排行榜',
        component: rankingView,
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
