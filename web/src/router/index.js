import Vue from 'vue'
import Router from 'vue-router'

import HeadItem from '@/components/HeadItem'
import AsideItem from '@/components/AsideItem'
const LoginView = () => import('@/components/view/LoginView.vue')
const MyShareView = () => import('@/components/view/MyShareView.vue')
const RegisterView = () => import('@/components/view/RegisterView.vue')
const NotFound = () => import('@/components/view/NotFound.vue')
const MyFileView = () => import('@/components/view/MyFileView.vue')
const PubShareView = () => import('@/components/view/PubShareView.vue')

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'route-index',
     component: MyFileView
    },
    {
      path: '/myshare',
      name: 'route-my-share',
     component: MyShareView
    },
    {
      path: '/login',
      name: 'route-login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'route-register',
      component: RegisterView

    },
    {
      path: '/share/:code',
      name: 'route-pub-share',
      component: PubShareView
    },
    {
      path: '*',
      name: '404',
      component: NotFound
    }
  ]
})
