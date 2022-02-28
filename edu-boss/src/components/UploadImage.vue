<template>
  <div class="image-div">
    <el-upload
      class="upload-demo"
      :action="uploadAction"
      :multiple="false"
      :limit="1"
      :before-upload="beforeUpload"
      :on-success="uploadSuccess"
      :on-remove="removeSuccess"
      :on-exceed="exceedFile"
      :file-list="fileList"
      list-type="picture-card"
    >
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
    <el-tag type="info" v-if="tipInfo">{{tipInfo}}</el-tag>
  </div>
</template>
<script>
export default {
  name: "UploadImage",
  data() {
    return {
      //uploadUrl= /course/courseUpload
      uploadAction: process.env.VUE_APP_API_BASE + this.uploadUrl,
      fileList: [],
      data: {}
    };
  },

  /**
   * 组件传参
   *    content 图片地址
   *    get-urls 获取图片地址
   */
  props: ["content", "getUrls", "max", "tipInfo", "uploadUrl"],

  //监听图片变化
  watch: {
    content: {
      handler: function(val, oldval) {
        this.initImage(val);
      },
      deep: true // 对象内部的属性监听，也叫深度监听
    }
  },
  methods: {
    //对推按进行初始化
    initImage(url) {
      if (this.content && this.content.length > 0) {
        this.fileList = [
          {
            name: "image",
            url: this.content
          }
        ];
      } else {
        this.fileList = [];
      }
    },

    //上传之前执行的函数,判断图片大小与类型
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";

      if (!(isJPG || isPNG)) {
        this.$message.error("上传头像图片只能是 JPG 或 PNG 格式!");
        return false;
      }

      let Max = `${this.max}`;
      Max = Max.toUpperCase();
      if (Max && Max.indexOf("K") >= 0) {
        const isLt = file.size / 1024 / 1024 < 10;
        if (!isLt) {
          this.$message.error("上传图片大小不能超过 " + Max + "!");
          return false;
        }
      } else if (Max && Max.indexOf("M") >= 0) {
        const isLt = file.size / 1024 / 1024 < parseInt(Max.replace("M", ""));
        if (!isLt) {
          this.$message.error("上传图片大小不能超过 " + Max + "!");
          return false;
        }
      }
      return true;
    },

    //上传成功后的回调函数
    uploadSuccess(res, file) {
      this.data = [res.content];
      this.returnUrlArr();
    },

    //返回最新的图片URL
    returnUrlArr() {
      console.log(this.data);
      this.getUrls(this.data);
    },

    //移除图片
    removeSuccess(file) {
      this.data = [];
      this.returnUrlArr();
    },
    exceedFile() {
      this.$message.error("每个板块只能上传一张图片！请先移除图片再上传");
    }
  }
};
</script>
<style>
.image-div {
  .el-tag--info {
    margin-top: 10px;
  }
}
</style>
