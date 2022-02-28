<template>
  <el-header class="navbar" height="auto">
    <el-button class="hamburger" type="text" :icon="sidebar.collapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'" @click="toggleCollapse"></el-button>
    <el-breadcrumb separator="/" replace>
      <el-breadcrumb-item :to="{ name: 'Home' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item v-for="item in navbar.breadcrumbs" :key="item.name" :to="item">{{ item.text || item.name }}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-dropdown @command="handleCommand" v-if="session.user">
      <img class="avatar" :src="portrait" :alt="session.user.userName" :title="session.user.userName">
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="profile">{{ session.user.userName }}</el-dropdown-item>
        <el-dropdown-item command="logout" divided>登出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'Navbar',
  computed: {
    ...mapGetters({
      navbar: 'navbar',
      sidebar: 'sidebar',
      session: 'session'
    }),
    portrait: function () {
      if (this.session.user && this.session.user.portrait) {
        return this.session.user && this.session.user.portrait
      }
      return '//www.lgstatic.com/thumbnail_100x100/i/image2/M01/5E/65/CgotOVszSAOANi0LAAAse2IVWAE693.jpg'
    }
  },

  created () {
    this.$store.dispatch('getCurrentUser')
  },

  methods: {
    ...mapActions({
      toggleCollapse: 'toggleSidebarCollapse'
    }),
    async handleCommand (command) {
      switch (command) {
        case 'logout':
          await this.$store.dispatch('deleteToken')
          this.$router.replace({ name: 'Login' })
          break
      }
    }
  }
}
</script>

<style lang="scss">
@import '../theme';
.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  padding: 0 !important;
  background-color: $navbar-bg;

  .hamburger {
    margin-right: 10px;
    padding: 15px;
    font-size: 20px;
    border: 0;
    border-radius: 0;

    &:hover {
      background-color: rgba(0, 0, 0, 0.1);
    }
  }

  .el-dropdown {
    margin-left: auto;
  }

  .avatar {
    display: block;
    margin: 10px;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    box-sizing: border-box;
  }
}
</style>
