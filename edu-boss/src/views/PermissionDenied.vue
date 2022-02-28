<template>
  <section class="error">
    <h1>无权限</h1>
    <h2>无权限操作，请联系管理员申请权限</h2>
    <footer>← Back to <a href="//edufront.lagou.com">去学习课程</a></footer>
  </section>
</template>

<script>
export default {
  name: 'Error',
  title: 'Not Found',
  data () {
    return {
      name: process.env.VUE_APP_NAME
    }
  }
}
</script>

<style lang="scss" scoped>
.error {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

h1,
h2 {
  margin: 0;
  font-size: 2.5rem;
  font-weight: 300;
  letter-spacing: .125rem;
}

h1 {
  font-size: 15rem;
}

footer {
  margin: 5rem;
  padding: .625rem;
  border: .0625rem solid rgba(0, 0, 0, 0.1);
  width: 22rem;
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
</style>
