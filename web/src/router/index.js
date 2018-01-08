import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import NotFound from '@/components/view/NotFound'
import LoginView from '@/components/view/LoginView'
import RegisterView from '@/components/view/RegisterView'
import MyFileView from '@/components/view/MyFileView'
import MyShareView from '@/components/view/MyShareView'
import HeadItem from '@/components/HeadItem'
import AsideItem from '@/components/AsideItem'

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
      path: '/test',
      name: 'route-test',
      component: HelloWorld

    },
    {
      path: '*',
      name: '404',
      component: NotFound
    }
  ]
})
