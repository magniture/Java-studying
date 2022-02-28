<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="homeAdvertise" :rules="rules" ref="form" label-width="150px" size="small">
      <el-form-item label="广告位名称：" prop="name">
        <el-input v-model="homeAdvertise.name" class="input-width"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { axios } from "../../utils";

export default {
  name: "HomeAdvertiseDetail",
  //组件传参
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      homeAdvertise: { name: null },
      rules: {
        name: [
          { required: true, message: "请输入广告名称", trigger: "blur" },
          {
            min: 2,
            max: 140,
            message: "长度在 2 到 140 个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },

  //钩子函数
  created() {},

  methods: {
    //方法1: 保存广告位信息
    handleSave() {},

    //方法2: 回显广告位信息
    loadPromotionSpace(id) {}
  }
};
</script>
<style scoped>
.input-width {
  width: 70%;
}
</style>
