<template>
  <el-card class="form-container" shadow="never">
    <!-- 新增&修改广告表单 -->
    <el-form :model="homeAdvertise" :rules="rules" ref="form" label-width="150px" size="small">
      <el-form-item label="广告名称：" prop="name">
        <el-input v-model="homeAdvertise.name" class="input-width"></el-input>
      </el-form-item>

      <!-- 广告位下拉列表
            typeOptions 广告位数据
            label option的内容
            value 广告位的id
      -->
      <el-form-item label="广告位置：">
        <el-select v-model="homeAdvertise.spaceId">
          <el-option
            v-for="type in typeOptions"
            :key="type.value"
            :label="type.label"
            :value="type.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker type="datetime" placeholder="选择日期" v-model="homeAdvertise.startTime"></el-date-picker>
      </el-form-item>
      <el-form-item label="到期时间：" prop="endTime">
        <el-date-picker type="datetime" placeholder="选择日期" v-model="homeAdvertise.endTime"></el-date-picker>
      </el-form-item>

      <!-- 上线下线 -->
      <el-form-item label="上线/下线：">
        <el-radio-group v-model="homeAdvertise.status">
          <el-radio :label="0">下线</el-radio>
          <el-radio :label="1">上线</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 广告图片 -->
      <el-form-item label="广告图片：">
        <upload-image
          :content="homeAdvertise.img && [homeAdvertise.img]"
          :get-urls="getADImgUrl"
          uploadUrl="/PromotionAd/PromotionAdUpload"
          ref="courseCoverRef"
          max="10M"
          tipInfo="建议尺寸：230*300px，JPG、PNG格式，图片小于150K"
        />
      </el-form-item>

      <el-form-item label="广告链接：" prop="link">
        <el-input v-model="homeAdvertise.link" class="input-width"></el-input>
      </el-form-item>
      <el-form-item label="广告备注：">
        <el-input
          class="input-width"
          type="textarea"
          :rows="5"
          placeholder="请输入内容"
          v-model="homeAdvertise.text"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import UploadImage from "@/components/UploadImage.vue";
import { axios } from "../../utils";

//广告对象 defaultHomeAdvertise
const homeAdvertise = {
  id: undefined,
  name: null,
  spaceId: "",
  img: null,
  startTime: null,
  endTime: null,
  status: 0,
  link: null,
  text: null,
  sort: 0
};
const rules = {
  name: [
    { required: true, message: "请输入广告名称", trigger: "blur" },
    {
      min: 2,
      max: 140,
      message: "长度在 2 到 140 个字符",
      trigger: "blur"
    }
  ],
  link: [{ required: true, message: "请输入广告链接", trigger: "blur" }],
  startTime: [{ required: true, message: "请选择开始时间", trigger: "blur" }],
  endTime: [{ required: true, message: "请选择到期时间", trigger: "blur" }],
  img: [{ required: true, message: "请选择广告图片", trigger: "blur" }]
};

export default {
  name: "HomeAdvertiseDetail",
  components: { UploadImage },

  //组件传参
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },

  //数据部分
  data() {
    return {
      homeAdvertise, //广告表单对象
      typeOptions: [], //广告位下拉列表
      rules
    };
  },

  //钩子函数
  created() {
    //判断是新增还是修改
    if (this.isEdit) {
      //修改
      const id = this.$route.query.id;
      this.loadPromotion(id);
    } else {
      //新增
      this.homeAdvertise = {};
    }

    //获取广告位数据
    this.loadPromotionSpace();
  },
  methods: {
    //方法1: 获取广告位置数据
    loadPromotionSpace() {},

    //方法2: 保存广告信息
    handleSave() {},

    //方法3: 修改回显广告信息
    loadPromotion(id) {},

    //获取图片路径,进行回显
    getADImgUrl(urls) {
      if (urls.length > 0) {
        this.homeAdvertise.img = urls && urls[0].filePath;
      }
    }
  }
};
</script>
<style scoped>
.input-width {
  width: 70%;
}
</style>
