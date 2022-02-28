<template>
  <home-advertise-detail :isEdit="true"></home-advertise-detail>
</template>
<script>
import HomeAdvertiseDetail from './AdvertiseSpaceDetail'
export default {
  name: 'updateHomeAdvertise',
  title: '编辑广告位',
  components: { HomeAdvertiseDetail }
}
</script>
<style></style>
