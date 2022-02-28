<template>
  <home-advertise-detail :isEdit="false"></home-advertise-detail>
</template>
<script>
import HomeAdvertiseDetail from './AdvertiseDetail'
export default {
  name: 'addHomeAdvertise',
  title: '添加广告',
  components: { HomeAdvertiseDetail }
}
</script>
<style></style>
