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
          <el-input v-model="registerForm.password" placeholder="密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="btn" v-on:click="onSubmit('registerForm')">立即注册</el-button>
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
    return {
      registerForm: {
        username: '',
        password: ''
      },
      registerRule: {
        username: [{
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          },
          {
            min: 3,
            max: 8,
            message: '长度在 3 到 8 个字符',
            trigger: 'blur'
          }
        ],
        password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          },
          {
            min: 3,
            max: 20,
            message: '长度在 3 到 20 个字符',
            trigger: 'blur'
          }
        ]
      }

    }
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {

        if (!valid) {
          return false;
        }

        var mydata = {
          'account': this.registerForm.username,
          'password': this.registerForm.password
        };

        var thiz = this;
        this.$http.post('/api/pub/register', mydata).then(function (response) {
          console.log(response);

          if (response.data.code === 200) {
              var onComplete = function(){
               thiz.showSuccessMsg(mydata.account + '注册成功，请登录');
              }
            
            thiz.$router.push({path: '/login'},onComplete);

          } else {
            thiz.showErrorMsg(response.data.msg);
          }

        }).catch(function (error) {
          console.log(error);
        });

      });

    }
  }
}



</script>

<style scoped>

.register {
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

