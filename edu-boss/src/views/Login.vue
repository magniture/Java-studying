<template>
  <section class="login">
    <header>
      <h1><router-link to="/" tabindex="-1">{{ name }}</router-link></h1>
      <el-alert v-if="error" type="warning" :description="error.message" show-icon/>
    </header>
    <el-form auto-complete="off" :model="model" :rules="rules" ref="login-form" label-position="top" :class="{ shaking: error }">
      <h2>登录</h2>
      <el-form-item label="手机号" prop="username">
        <el-input type="text" v-model="model.username" placeholder="请输入手机号"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="model.password" placeholder="请输入密码"/>
      </el-form-item>
      <el-button type="primary" :loading="loading" @click="submit('login-form')">{{ loading ? 'Loading...' : '登录' }}</el-button>
    </el-form>
    <footer>
      ← 回到 <a href="https://edufront.lagou.com">用户端</a>
    </footer>
  </section>
</template>

<script>
export default {
  name: 'Login',

  data () {
    // form model
    // TODO: remove default values
    const model = {
      username: '',
      password: ''
    }

    // form validate rules
    const rules = {
      username: [
        { required: true, message: '手机号必填' },
        {
          min: 2,
          max: 16,
          message: '请输入正确的手机号'
        }
      ],
      password: [
        { required: true, message: '密码必填' },
        {
          min: 4,
          max: 16,
          message: '请输入正确的密码'
        }
      ]
    }

    const name = process.env.VUE_APP_NAME

    return { name, model, rules, error: null, loading: false }
  },

  methods: {

    //提交登录表单
    submit (ref) {
      // form validate
      this.$refs[ref].validate(valid => {
        if (!valid) return false

        // validated
        this.error = null
        this.loading = true

        // create token from remote
        //发送登录请求
        this.$store
          .dispatch('createToken', this.model)
          .then(res => {
            if (res.state !== 1) {
              this.error = {
                title: 'Error occurred',
                message: 'Abnormal, please try again later!'
              }
              switch (res.state) {
                case 201:
                  this.error.message = '请输入正确的手机号'
                  break
                case 202:
                  this.error.message = '请输入密码'
                  break
                case 203:
                  this.error.message = '密码错误'
                  break
                case 204:
                  this.error.message = '验证码过期'
                  break
                case 205:
                  this.error.message = '帐号错误或密码错误'
                  break
                case 206:
                  this.error.message = '帐号错误或密码错误'
                  break
                case 207:
                  this.error.message = '验证码错误'
                  break
                case 500:
                  this.error.message =
                              'Internal server error, please try again later!'
                  break
              }
              this.loading = false
              return
            }
            this.$router.replace({ path: this.$route.query.redirect || '/' })
            this.loading = false
          })
          .catch(err => {
            console.error(err)
            this.error = {
              title: 'Error occurred',
              message: 'Abnormal, please try again later!'
            }
            switch (err.response && err.response.status) {
              case 401:
                this.error.message = 'Incorrect username or password!'
                break
              case 500:
                this.error.message =
                  'Internal server error, please try again later!'
                break
            }
            this.loading = false
          })
      })
    }
  }
}
</script>

<style lang="scss">
.login {
  width: 95%;
  max-width: 22rem;
  margin: 1rem auto;

  header {
    margin-bottom: 1rem;

    h1 {
      margin: 4.5rem 0 3.5rem;
      text-align: center;
      letter-spacing: .1em;

      a {
        margin: 0;
        color: rgba(0, 0, 0, 0.5);
        font-size: 3rem;
        font-weight: 300;
        text-decoration: none;
        transition: text-shadow 0.3s;

        &:hover {
          text-shadow: 0 0 0.5rem rgba(0, 0, 0, 0.2);
        }
      }
    }
  }

  .el-form {
    margin-bottom: 2.5rem;
    padding: 1.875rem 1.25rem;
    background: #fff;

    h2 {
      margin: 0 0 1rem;
      font-weight: 400;
      font-size: 1.5rem;
    }

    .el-button {
      margin-top: 0.5rem;
      width: 100%;
    }

    &.shaking {
      animation: shakeX 1s;
    }
  }

  footer {
    margin-bottom: 1rem;
    padding: .625rem;
    border: .0625rem solid rgba(0, 0, 0, 0.1);
    font-size: .75rem;
    text-align: center;
    color: rgba(0, 0, 0, 0.6);

    a {
      color: inherit;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }

  @keyframes shakeX {
    from,
    to {
      transform: translate3d(0, 0, 0);
    }

    10%,
    30%,
    50%,
    70%,
    90% {
      transform: translate3d(-10px, 0, 0);
    }

    20%,
    40%,
    60%,
    80% {
      transform: translate3d(10px, 0, 0);
    }
  }
}
</style>
