<template>

  <div class="register">

    <div class="hd">
        <h2>EasyCloud</h2>
    </div>

    <div class="bd">
      
      <el-form :model="registerForm" :rules="registerRule" ref="registerForm">
        <el-form-item prop="username">
          <el-input v-model="registerForm.username" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="passwordConfirm">
          <el-input v-model="registerForm.passwordConfirm" placeholder="请再次输入密码" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="btn" v-on:click="onSubmit('registerForm')"  :loading="regLoading">立即注册</el-button>
        </el-form-item>
      </el-form>

    </div>

    <div class="ft">
      <router-link to="/login">已有账号，直接登录</router-link>
    </div>

  </div>


</template>


<script>
export default {
  name: "register-view",
  data() {
    var passwordRule = [{
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
        message: '密码只能包含数字、字母和下划线'
      }
    ];
    var usernameRule = [{
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
        message: '账号只能包含数字、字母和下划线'
      }
    ];
    return {
      regLoading: false,
      registerForm: {
        username: '',
        password: '',
        passwordConfirm: ''
      },
      registerRule: {
        username: usernameRule,
        password: passwordRule,
        passwordConfirm: passwordRule
      }

    }
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {

        if (!valid) {
          return false;
        }

        if (this.registerForm.password !== this.registerForm.passwordConfirm) {
          this.showErrorMsg('两次密码输入不一致！');
          return false;
        }

        var mydata = {
          'account': this.registerForm.username.trim(),
          'password': this.registerForm.password.trim()
        };

        this.regLoading = true;
        var thiz = this;
        this.$http.post('/api/pub/register', mydata).then(function (response) {

          this.regLoading = false;

          if (response.data.code === 200) {
            var onComplete = function () {
              thiz.showSuccessMsg(mydata.account + ' 注册成功，请登录');
            }

            thiz.$router.push({
              name: 'route-login',
              params: {
                account: mydata.account
              }
            }, onComplete);

          } else {
            thiz.showErrorMsg(response.data.msg);
          }

        });

      });

    }
  }
}

</script>

<style scoped>

.register {
  margin-top: 5%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.register .hd {
  width: 300px;
}


.register .bd {
  width: 300px;
}

.btn {
  width: 100%;
}


.register .ft {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 300px;
}

.register .ft a {
  text-decoration: none;
  color: #20A0FF;
}


</style>

