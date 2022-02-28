<template>
  <section class="comments">
    <el-form class="actions" :model="filter" label-position="top" inline>
      <!-- <el-form-item label="ID">
        <el-input v-model="filter.id" placeholder="请输入用户 ID"/>
      </el-form-item> -->
      <el-form-item label="课程id">
        <el-input v-model="filter.courseId" placeholder="请输入课程id"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleFilter">查询</el-button>
      </el-form-item>
      <el-form-item class="btn-add">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加留言</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="comments" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="ID" width="230"></el-table-column>
      <el-table-column prop="courseId" label="课程id" ></el-table-column>
      <el-table-column prop="comment" label="留言内容" width="180" ></el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button size="mini"  @click="handleAdd(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="comments.length" layout="total, sizes, prev, pager, next, jumper" @size-change="handlePageSizeChange" @current-change="handleCurrentPageChange" :current-page="page" :page-sizes="[20, 30, 50]" :page-size="size" :total="total"/>
  </section>
</template>

<script>
import moment from 'moment'
export default {
  name: 'Comments',
  title: '留言管理',
  data () {
    const filter = {
      id: '',
      courseId: ''
    }

    return {
      filter,
      comments: [],
      total: 0,
      size: 20,
      page: 1,
      loading: false
    }
  },
  created () {
    // initial data
    this.loadComments()
  },
  methods: {
    loadComments () {
      // toggle loading
      this.loading = true

      // paginate
      const params = { currentPage: this.page, pageSize: this.size }

      // filter
      if (this.filter.id) params.id = this.filter.id
      if (this.filter.courseId) params.courseId = this.filter.courseId

      // request data
      return this.$services.comments.getCommentsList(params)
        .then(res => {
          // response
          this.comments = res.recordList
          this.total = res.pageCount
          // toggle loading
          this.loading = false
        })
        .catch(err => {
          // handle error
          console.error(err)
          this.loading = false
        })
    },

    handleCurrentPageChange (page) {
      this.page = page
      this.loadComments()
    },

    handlePageSizeChange (size) {
      this.size = size
      this.loadComments()
    },

    handleFilter () {
      this.loadComments()
    },

    handleAdd (item) {
      const commentId = item && item.id
      this.$router.push({ name: 'CommentItem', params: { commentId: commentId || 'new' } })
    },

    dateFormatter (row, column, value, index) {
      return moment(value).format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>

<style lang="scss">
.comments {
  .actions {
    display: flex;
    align-items: flex-end;

    label {
      line-height: 1;
    }
  }

  .btn-add {
    margin-left: auto;
    margin-right: 0;
  }

  .avatar {
    margin-right: 10px;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    box-sizing: border-box;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    line-height: 30px;
    vertical-align: middle;
  }

  .el-pagination {
    margin-top: 30px;
    text-align: right;
  }
}
</style>
