<template>

  <div class="headView">
    
    <el-header style="text-align: right; font-size: 12px">

      <template v-if="isLogin">
 
          <span id="span-username">{{username}}</span>
          <el-button :loading="logoutLoading" size="small" @click="requestLogout">注销</el-button>

      </template>
      <template v-else>

          <router-link id="route-tologin" :to="{name: 'route-login'}">请登录</router-link>

      </template>

    </el-header>

  </div>

</template>


<script>
export default {
  name: 'head-item',
  data(){
      return {
          username: this.$user.account(),
          logoutLoading: false
      }
  },
  computed:{
      isLogin: function () {
        return this.$user.isLogin();;
      }
  },
  methods:{
    requestLogout(){
      this.logoutLoading = true;
      var thiz = this;

        this.$http.get('/api/usr/logout').then(function (response) {

          if (response.data.code === 200) {
            var onComplete = function () {
              thiz.logoutLoading = false;
              thiz.$user.clean();
              thiz.showSuccessMsg('注销成功');
            }

            thiz.$router.push({
              name: 'route-login',
              params: {
                account: ''
              }
            }, onComplete);

          } else {
            thiz.showErrorMsg(response.data.msg);
          }

        });
    }

  }
}
//color: #8590a6; background-color: #fcfbfb;
</script>

<style scoped>
.el-header {
    background-color: #fcfbfb;
    color: rgb(12, 9, 9);
    line-height: 60px;
  }

#route-tologin {
  text-decoration: none;  
  color: #20A0FF;
}

#span-username{
    font-size: 15px;
    line-height: 30px;
    color: #303133;
}

</style>

