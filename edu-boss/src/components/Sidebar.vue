<template>
  <el-aside class="sidebar" width="auto">
    <router-link class="logo" :class="{ collapse: sidebar.collapse }" :to="{ name: 'Home' }" title="Back to Home">
      <img src="../assets/logo.png" alt="LagouEdu">
      <h1>Edu Boss</h1>
    </router-link>
    <el-menu :collapse="sidebar.collapse" router>
      <template v-for="(item, i) in sidebar.menus">
        <el-submenu v-if="item.children" :key="i" :index="`${i}`">
          <template slot="title">
            <i v-if="item.icon" :class="'el-icon-' + item.icon"></i>
            <span>{{ item.text }}</span>
          </template>
          <el-menu-item  v-for="(sub, j) in item && item.children" :key="j" :index="`${i}-${j}`" :route="sub.name">
            <i v-if="sub.icon" :class="'el-icon-' + sub.icon"></i>
            <span>{{ sub.text }}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item v-else :key="i" :index="item.name" :route="item.name">
          <i :class="'el-icon-' + item.icon"></i>
          <span slot="title">{{ item.text }}</span>
        </el-menu-item>
      </template>
      <!-- <el-menu-item v-if="sidebar.collapse" index="collapse" @click="toggleCollapse">
        <i class="el-icon-s-unfold"></i>
        <span slot="title">展开菜单</span>
      </el-menu-item>
      <el-menu-item v-else index="collapse" @click="toggleCollapse">
        <i class="el-icon-s-fold"></i>
        <span slot="title">收起菜单</span>
      </el-menu-item> -->
    </el-menu>
  </el-aside>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'app-sidebar',

  data () {
    return {
    }
  },
  created () {
    // this.$store.dispatch('getUserPermissions')
  },

  computed: mapGetters({
    sidebar: 'sidebar'
  }),

  methods: mapActions({
    toggleCollapse: 'toggleSidebarCollapse'
  })
}
</script>

<style lang="scss">
@import '../theme';
.sidebar {
  position: sticky;
  top: 0;
  max-height: 100vh;
  background-color: $sidebar-bg;
  user-select: none;

  &::-webkit-scrollbar {
    width: 5px;
  }

  &::-webkit-scrollbar-track {
    border-radius: 2em;
  }

  &::-webkit-scrollbar-thumb {
    background-color:rgba(0, 0, 0, 0.1);
    border-radius: 2em;
  }

  .logo {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #495057;
    line-height: 50px;
    text-decoration: none;
    text-align: center;

    &:hover {
      color: #343a40;
      background-color: rgba(0, 0, 0, 0.05);
    }

    img {
      margin: 10px;
      width: 30px;
    }

    h1 {
      display: inline-block;
      margin: 0;
      width: 95px;
      overflow: hidden;
      font-size: 20px;
      white-space: nowrap;
      transition: width 0.3s;
    }

    &.collapse {
      h1 {
        width: 0;
      }
    }
  }

  .el-menu {
    border-right: 0 !important;
    background-color: transparent;

    &:not(.el-menu--collapse) {
      width: 200px;
    }

    .el-menu-item:hover,
    .el-menu-item:focus,
    .el-submenu__title:hover {
      background-color: $body-bg;
    }
  }
}
</style>
