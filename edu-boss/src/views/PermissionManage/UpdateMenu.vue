<template>
  <menu-detail :is-edit='true'></menu-detail>
</template>
<script>
import MenuDetail from './MenuDetail'
export default {
  name: 'updateMenu',
  title: '编辑菜单',
  components: { MenuDetail }
}
</script>
<style>
</style>
