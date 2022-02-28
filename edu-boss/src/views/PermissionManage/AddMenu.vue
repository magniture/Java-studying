<template>
  <menu-detail :is-edit='false'></menu-detail>
</template>
<script>
import MenuDetail from './MenuDetail'
export default {
  name: 'addMenu',
  title: '添加菜单',
  components: { MenuDetail }
}
</script>
<style>
</style>
