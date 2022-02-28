<template>
  <div class="video-edit">
    <el-form :model="formData" :rules="rules" ref="audioForm" label-width="180px">
      <el-form-item label="课程" prop="courseName">
        <el-input type="text" v-model="formData.courseName" disabled/>
      </el-form-item>
      <el-form-item label="课时" prop="lessonName">
        <el-input type="text" v-model="formData.lessonName" disabled/>
      </el-form-item>
      <input type="file" style="display:none" ref="videoFile" @change="videoChanged" />
      <input type="file" style="display:none" ref="coverFile" @change="coverChanged" />
      <el-form-item label="视频上传">
        <el-button type="primary" @click="videoSelect">选择视频</el-button>
        <div>
          {{videoFile && videoFile.name}}
        </div>
        <div v-if="!videoFile">
          {{currentVideoName}}
        </div>
      </el-form-item>
      <el-form-item label="封面上传">
        <el-button type="primary" @click="coverSelect">选择封面</el-button>

        <div>
          {{coverFile && coverFile.name}}
        </div>
      </el-form-item>
      <el-form-item v-if="currentCoverUrl && !coverFile">
        <img :src="currentCoverUrl" style="width:200px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="uploadAll">开始上传</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
      <el-form-item>
        <div v-if="aliUploadInfo && aliUploadInfo.videoInfo">
          <h4><strong>视频信息</strong></h4>
          视频名称：{{aliUploadInfo.videoInfo.name }};&nbsp;<br/>
          阿里云上传进度：<strong style="color: #1e88e5">{{aliUploadInfo.progress + "%"}};&nbsp;</strong>
          阿里云上传结果：{{aliUploadInfo.isVideoUploadCancel ? '已取消' : aliUploadInfo.isVideoUploadSuccess ? '上传成功' : '上传中'}};&nbsp;<br/>
          <!--                    <div v-if="tcUploadInfo.failMessage">失败原因：{{tcUploadInfo.failMessage}}</div>-->
          阿里云转码状态：<span id="video-transcoding-tip" style="color: red">{{aliUploadInfo.isVideoUploadSuccess?aliUploadInfo.decodeStatus:''}};</span><br/>
          <a href="javascript:void(0);" class="cancel-upload" v-if="!aliUploadInfo.isVideoUploadSuccess && !aliUploadInfo.isVideoUploadCancel" @click="aliUploadInfo.cancel()">取消上传</a><br>
        </div>
      </el-form-item>
    </el-form>

    <ali-upload ref="aliUpload" :uploaderInfo="getAliUploadInfo" :lessonId="formData.lessonId"></ali-upload>

  </div>
</template>

<style scope>

  .margin-left-180 {
    margin-left: 180px;
  }

  .video-edit {
    width: 800px;
    margin: 0 auto;
  }
</style>
<script>
import AliUpload from '@/components/AliUpload.vue'

export default {
  name: 'VideoAdd',
  components: {
    AliUpload
  },
  data () {
    return {
      videoFile: null,
      coverFile: null,
      formData: {},
      // tcUploadInfo: {},
      aliUploadInfo: {},
      rules: {
        courseName: [
          { required: true, message: '请选择课程名称', trigger: 'blur' }
        ],
        lessonName: [
          { required: true, message: '请选择课时名称', trigger: 'blur' }
        ]
      },
      uploadAction: '/course/upload',
      currentVideoName: null,
      currentCoverUrl: null
    }
  },
  created () {
    this.formData = this.$route.params
    if (this.formData.lessonId) {
      this.getMedia()
    }
  },
  methods: {
    getMedia () {
      this.$services.video.getMedia(this.formData.lessonId).then(res => {
        res.coverImageUrl && (this.currentCoverUrl = res.coverImageUrl)
        res.fileName && (this.currentVideoName = res.fileName)
      })
    },
    getUploadInfo (info) {
      this.tcUploadInfo = info
    },
    getAliUploadInfo (info) {
      this.aliUploadInfo = info
    },
    updateFile () {

    },
    videoSelect () {
      this.$refs.videoFile.click()
    },
    coverSelect () {
      this.$refs.coverFile.click()
    },
    videoChanged () {
      this.videoFile = this.$refs.videoFile.files[0]
      console.log(this.videoFile)
    },
    coverChanged () {
      this.coverFile = this.$refs.coverFile.files[0]
    },
    uploadAll () {
      if (!this.videoFile || !this.coverFile) {
        this.$message.error('请选择要上传的视频与封面')
        return
      }

      // try {
      //     this.$refs.tcUpload.uploadAll(this.videoFile, this.coverFile);
      // } catch (error) {
      //     this.$message.error("腾讯云上传失败，失败原因：" + error)
      // }
      try {
        this.$refs.aliUpload.uploadAll(this.videoFile, this.coverFile)
      } catch (error) {
        this.$message.error('阿里云上传失败，失败原因：' + error)
      }
    },
    back () {
      this.$router.back()
    }
  }
}

</script>
