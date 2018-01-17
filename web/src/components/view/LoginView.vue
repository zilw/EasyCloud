<template>

  <div class="login">

    <div class="hd">
        <h2>EasyCloud</h2>
    </div>

    <div class="bd">
      
      <el-form :model="loginForm" :rules="loginRule" ref="loginForm">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="账号" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="btn" v-on:click="onSubmit('loginForm')" :loading="loginLoading">登录</el-button>
        </el-form-item>
      </el-form>

    </div>

    <div class="ft">
      <router-link to="/register">没有账号？马上注册</router-link>
    </div>

  </div>


</template>


<script>
export default {
  name: "login-view",
  data() {
    return {
      loginLoading: false,
      loginForm: {
        username: '',
        password: ''
      },
      loginRule: {
        username: [{
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          },
          {
            min: 4,
            max: 12,
            message: '长度在 4 到 12 个字符',
            trigger: 'blur'
          },
          {
            pattern: /^[a-zA-Z0-9_]{4,12}$/,
            message: '账号由数字、字母和下划线组成'
          }
        ],
        password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          },
         {
            min: 6,
            max: 16,
            message: '长度在 6 到 16 个字符',
            trigger: 'blur'
          },
          {
            pattern: /^[a-zA-Z0-9_]{6,16}$/,
            message: '密码由数字、字母和下划线组成'
          }
        ]
      }

    }
  },
  created: function () {
    this.loginForm.username = this.$route.params.account;
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {

        if (!valid) {
          return false;
        }

        var mydata = {
          'account': this.loginForm.username,
          'password': this.loginForm.password
        };

        this.loginLoading = true;
        var thiz = this;
        this.$http.post('/api/pub/login', mydata).then(function (response) {
          thiz.loginLoading = false;

          if (response.data.code === 200) {
            
            window.localStorage["isLogin"] = true;
            window.localStorage["account"] = response.data.data.account;
            window.localStorage["userId"] = response.data.data.userId;
            //window.localStorage["token"] = response.data.data.token;
            window.localStorage["createTime"] = response.data.data.createTime;
            window.localStorage["lastTime"] = response.data.data.lastTime;

            //console.log(thiz.$user);

            var onComplete = function () {
              thiz.$message({
                showClose: true,
                duration: 4000,
                message: '登录成功',
                type: 'success'
              });
            }

            thiz.$router.push({
              name: 'route-index'
            }, onComplete);

            return;
          } 
          thiz.showErrorMsg(response.data.msg);
          
        });

      });


    }
  }
}



</script>

<style scoped>

.login {
  margin-top: 5%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.login .hd {
  width: 300px;
}


.login .bd {
  width: 300px;
}

.btn {
  width: 100%;
}


.login .ft {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 300px;
}

.login .ft a {
  text-decoration: none;
  color: #20A0FF;
}




</style>

