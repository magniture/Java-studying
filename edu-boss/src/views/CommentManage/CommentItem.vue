<template>
  <section class="course-item">
    <div class="header">
      <el-page-header @back="() => this.$router.back()" :content="pathTitle"/>
      <el-button type="primary" @click="handleSave">保存</el-button>
    </div>
    <el-form ref="form" :model="comment" :rules="rules" label-width="140px">
      <el-card shadow="never" v-loading="loading" element-loading-text="数据加载中...">
        <header slot="header">基本信息</header>
        <el-form-item label="课程id" prop="courseId">
          <el-input v-model="comment.courseId" type="text" maxlength="50" show-word-limit/>
        </el-form-item>
        <el-form-item label="留言" prop="comment">
          <el-input v-model="comment.comment" type="text" maxlength="50" show-word-limit/>
        </el-form-item>
      </el-card>
      <div class="actions">
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>
    </el-form>
  </section>
</template>

<script>

export default {
  name: 'CommentItem',
  title: '留言信息',
  data () {
    const rules = {
      comment: [
        { required: true, message: '请输入内容', trigger: 'blur' },
        { min: 3, max: 50, message: '长度在 3 到 20 个字符', trigger: 'blur' }
      ]
    }

    return {
      pathTitle: '',
      rules,
      comment: {
        id: '',
        courseId: '',
        comment: ''
      },
      editorVisible: true,
      loading: false
    }
  },
  created () {
    const id = this.$route.params.commentId
    if (!id) return this.redirectToError()
    if (id === 'new') {
      this.pathTitle = '新增留言'
      this.$breadcrumbs = [{ name: 'Comments', text: '留言管理' }, { text: '新增留言' }]
    } else {
      this.$breadcrumbs = [{ name: 'Comments', text: '留言管理' }, { text: '编辑留言' }]
      this.loadComment(id)
    }
  },
  methods: {
    loadComment (id) {
      this.loading = true
      this.$services.comments.getCommentById(id)
        .then(data => {
          this.pathTitle = '留言'
          this.comment = Object.assign(this.comment, data)
          this.loading = false
        })
        .catch(() => {
          this.loading = false
          this.redirectToError()
        })
    },
    handleSave () {
      this.$refs.form.validate(valid => {
        if (!valid) return false
        this.$services.comments.saveOrUpdateComment(this.comment)
          .then(data => {
            this.$router.back()
          })
          .catch(err => {
            this.$message({
              type: 'error',
              message: err.message
            })
          })
      })
    },
    redirectToError () {
      // TODO: Error components
      this.$router.replace({ path: '/not-found' })
    }
  }
}
</script>

<style lang="scss">
  .course-item {
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .el-card {
      margin-top: 20px;
    }

    .form-control-summary {
      .el-form-item__content {
        display: flex;
        justify-content: space-between;

        .el-input {
          width: 49%;
        }

        &:before, &:after {
          display: none;
        }
      }
    }

    .actions {
      display: flex;
      justify-content: center;

      .el-button {
        margin: 40px 20px;
      }
    }
  }
</style>
