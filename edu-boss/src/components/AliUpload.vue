<template>
  <div></div>
</template>
<script>
export default {
  name: 'AliUpload',
  data () {
    return {
      cover: '',
      video: '',
      imageType: 'image',
      videoType: 'video',
      uploader: null,
      uploadProgressInfo: null,
      userData: '{"Vod":{}}',
      retryCount: 0
    }
  },
  props: ['uploaderInfo', 'lessonId'],
  watch: {
    uploadProgressInfo () {
      this.uploaderInfo(this.uploadProgressInfo)
    }
  },
  created () {
    if (this.uploader) {
      this.uploader.stopUpload()
      this.authProgress = 0
      this.statusText = ''
    }
    this.uploader = this.createUploader()
    this.initUploadInfo()
  },
  methods: {
    initUploadInfo () {
      this.uploadProgressInfo = {
        videoInfo: this.video,
        coverInfo: this.cover,
        isVideoUploadSuccess: false,
        isVideoUploadCancel: false,
        isCoverUploadSuccess: false,
        progress: 0,
        coverProgress: 0,
        fileId: '',
        videoUrl: '',
        coverUrl: '',
        decodeStatus: '请勿刷新此页面，正在转码，请稍等...',
        cancel: () => {
          this.uploadProgressInfo.isVideoUploadCancel = true
          this.uploader.stopUpload()
        }
      }
    },
    uploadAll (video, cover) {
      if (video) {
        this.video = video
      }
      if (cover) {
        this.cover = cover
        this.type = this.imageType
        this.uploader.addFile(cover, null, null, null, this.userData)
        this.uploader.addFile(video, null, null, null, this.userData)
        this.uploadDisabled = false
        this.pauseDisabled = true
        this.resumeDisabled = true
        this.authUpload()
      }
    },
    uploadVideo (video) {
      video && (this.video = video)
      this.type = this.videoType
      this.uploader.addFile(this.video, null, null, null, this.userData)
      this.uploadDisabled = false
      this.pauseDisabled = true
      this.resumeDisabled = true
      this.authUpload()
    },
    authUpload () {
      // 然后调用 startUpload 方法, 开始上传
      if (this.uploader !== null) {
        this.uploader.startUpload()
        this.uploadDisabled = true
        this.pauseDisabled = false
      }
    },
    // 暂停上传
    pauseUpload () {
      if (this.uploader !== null) {
        this.uploader.stopUpload()
        this.resumeDisabled = false
        this.pauseDisabled = true
      }
    },
    // 恢复上传
    resumeUpload () {
      if (this.uploader !== null) {
        this.uploader.startUpload()
        this.resumeDisabled = true
        this.pauseDisabled = false
      }
    },
    createUploader () {
      const self = this

      const uploader = new window.AliyunUpload.Vod({
        timeout: 60000,
        partSize: 1048576,
        parallel: 1,
        retryCount: 3,
        retryDuration: 2,
        region: self.region,
        userId: 1618139964448548,
        // 添加文件成功
        addFileSuccess: function (uploadInfo) {
          uploadInfo && uploadInfo.file && uploadInfo.file.type && (uploadInfo.file.type.indexOf('image') >= 0) ? (self.uploadProgressInfo.coverInfo = uploadInfo.file)
            : (self.uploadProgressInfo.videoInfo = uploadInfo.file)
          console.log('addFileSuccess: ')
        },
        // 开始上传
        onUploadstarted: function (uploadInfo) {
          if (uploadInfo.file.type.indexOf(self.imageType) >= 0) {
            // 图片直接上传
            self.$services.video.getImgAuth().then(data => {
              self.imageUrl = data.imageURL
              self.uploadProgressInfo.fileId = data.imageId
              self.uploadProgressInfo.coverUrl = data.imageURL
              uploader.setUploadAuthAndAddress(uploadInfo, data.uploadAuth, data.uploadAddress, data.imageId)
            })
          } else {
            self.$services.video.getVideoAuth({
              fileName: self.video && self.video.name,
              imageUrl: self.imageUrl || null,
              videoId: uploadInfo.videoId
            }).then(data => {
              self.uploadProgressInfo.videoUrl = data.videoURL
              self.uploadProgressInfo.videoId = data.videoId
              uploader.setUploadAuthAndAddress(uploadInfo, data.uploadAuth, data.uploadAddress, data.videoId)
            })
          }
        },
        // 文件上传成功
        onUploadSucceed: function (uploadInfo) {
          // console.log('uploadInfo=====', uploadInfo)
          // console.log("onUploadSucceed: " + uploadInfo.file.name + ", endpoint:" + uploadInfo.endpoint + ", bucket:" + uploadInfo.bucket + ", object:" + uploadInfo.object)
          console.log('文件上传成功！')
          if (uploadInfo.file.type.indexOf('image') >= 0) {
            self.uploadProgressInfo.isCoverUploadSuccess = true
          } else {
            self.uploadProgressInfo.isVideoUploadSuccess = true
            self.decodeVideo(uploadInfo.videoId, self.video.name)
          }
        },
        // 文件上传失败
        onUploadFailed: function (uploadInfo, code, message) {
          // console.log("onUploadFailed: file:" + uploadInfo.file.name + ",code:" + code + ", message:" + message)
          console.log('文件上传失败!失败原因：' + message)
          self.uploadProgressInfo.isVideoUploadSuccess = false
          self.uploadProgressInfo.failMessage = message
        },
        // 取消文件上传
        onUploadCanceled: function (uploadInfo, code, message) {
          // console.log("Canceled file: " + uploadInfo.file.name + ", code: " + code + ", message:" + message)
          console.log('文件已暂停上传')
          self.uploadProgressInfo.isVideoUploadCancel = true
        },
        // 文件上传进度，单位：字节, 可以在这个函数中拿到上传进度并显示在页面上
        onUploadProgress: function (uploadInfo, totalSize, progress) {
          // console.log("onUploadProgress:file:" + uploadInfo.file.name + ", fileSize:" + totalSize + ", percent:" + Math.ceil(progress * 100) + "%")
          const progressPercent = Math.ceil(progress * 100)
          self.uploadProgressInfo.progress = progressPercent
        },
        // 上传凭证超时
        onUploadTokenExpired: function (uploadInfo) {
          // 上传大文件超时, 如果是上传方式一即根据 UploadAuth 上传时
          // 需要根据 uploadInfo.videoId 调用刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)重新获取 UploadAuth
          // 然后调用 resumeUploadWithAuth 方法, 这里是测试接口, 所以我直接获取了 UploadAuth
          if (self.retryCount < this.retryCount) {
            self.retryCount++
            self.$services.video.getVideoAuth({
              fileName: self.video && self.video.name,
              imageUrl: self.imageUrl,
              videoId: uploadInfo.videoId
            }).then(data => {
              self.uploader.resumeUploadWithAuth(data.uploadAuth)
            })
          }
          console.log('文件超时...')
        },
        // 全部文件上传结束
        onUploadEnd: function (uploadInfo) {
          console.log('onUploadEnd: uploaded all the files')
          console.log('文件上传完毕')
          self.uploadProgressInfo.isVideoUploadSuccess = true
        }
      })
      return uploader
    },
    decodeVideo (videoId, name) {
      this.$services.video.transCode(this.lessonId, videoId, this.uploadProgressInfo.coverUrl || this.imageUrl, name).then(res => {
        this.queryDecodeResult()
      })
    },
    queryDecodeResult () {
      this.$services.video.transCodeResult(this.lessonId).then(res => {
        if (res < 100) {
          this.uploadProgressInfo.decodeStatus = '请勿刷新页面，正在转码，请稍后..'
          setTimeout(() => {
            this.queryDecodeResult()
          }, 1000)
        } else {
          this.video = null
          this.cover = null
          this.uploadProgressInfo.decodeStatus = '成功'
        }
      })
    }
  }
}

</script>
