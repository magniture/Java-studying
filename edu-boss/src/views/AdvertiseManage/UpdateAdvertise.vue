<template>
  <home-advertise-detail :isEdit="true"></home-advertise-detail>
</template>
<script>
import HomeAdvertiseDetail from './AdvertiseDetail'
export default {
  name: 'updateHomeAdvertise',
  title: '编辑广告',
  components: { HomeAdvertiseDetail }
}
</script>
<style></style>
