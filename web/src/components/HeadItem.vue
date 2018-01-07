<template>

  <div class="headView">
    
    <el-header style="text-align: right; font-size: 12px">

      <template v-if="isLogin">
 
          <span>{{username}}</span>

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
          username: this.$user.account()
      }
  },
  computed:{
      isLogin: function () {
        return this.$user.isLogin();;
      }
  }
}
</script>

<style scoped>
.el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

#route-tologin {
  text-decoration: none;  
  color: #20A0FF;
}

</style>

