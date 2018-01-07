<template>

  <div class="asideView">
    
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-active="activeIndex" :router="true">

          <el-menu-item index="1" :route="{name: 'route-index'}">我的文件</el-menu-item>
          <el-menu-item index="2" :route="{name: 'route-my-share'}">我的分享</el-menu-item>

        </el-menu>
      </el-aside>
  

  </div>

</template>


<script>
export default {
  name: 'aside-item',
  props:['activeIndex'],
  data(){
      return {
          
      }
  }
}
</script>

<style scoped>

</style>

