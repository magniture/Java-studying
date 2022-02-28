<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <el-button style="float:right" type="primary" @click="handleSearchList()" size="small">查询搜索</el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >重置</el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="资源名称：">
            <el-input v-model="listQuery.name" class="input-width" placeholder="资源名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="资源路径：">
            <el-input v-model="listQuery.url" class="input-width" placeholder="资源路径" clearable></el-input>
          </el-form-item>
          <el-form-item label="资源分类：">
            <el-select
              v-model="listQuery.categoryId"
              placeholder="全部"
              clearable
              class="input-width"
            >
              <el-option
                v-for="item in categoryOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
      <el-button size="mini" class="btn-add" @click="handleShowCategory()">资源分类</el-button>
    </el-card>
    <div class="table-container">
      <el-table
        ref="resourceTable"
        :data="list"
        style="width: 100%;"
        v-loading="listLoading"
        border
      >
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="资源名称" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="资源路径" align="center">
          <template slot-scope="scope">{{scope.row.url}}</template>
        </el-table-column>
        <el-table-column label="描述" align="center">
          <template slot-scope="scope">{{scope.row.description}}</template>
        </el-table-column>
        <el-table-column label="添加时间" width="180" align="center">
          <template slot-scope="scope">{{scope.row.createdTime | formatDateTime}}</template>
        </el-table-column>
        <el-table-column label="操作" width="140" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page="listQuery.currentPage"
        :page-sizes="[5,10, 20]"
        :page-size="listQuery.pageSize"
        :total="total"
      ></el-pagination>
    </div>

    <!-- 添加&修改表单 -->
    <el-dialog :title="isEdit?'编辑资源':'添加资源'" :visible.sync="dialogVisible" width="40%">
      <el-form :model="resource" ref="resourceForm" label-width="150px" size="small">
        <el-form-item label="资源名称：">
          <el-input v-model="resource.name" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="资源路径：">
          <el-input v-model="resource.url" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="资源分类：">
          <el-select v-model="resource.categoryId" placeholder="全部" clearable style="width: 250px">
            <el-option
              v-for="item in categoryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述：">
          <el-input v-model="resource.description" type="textarea" :rows="5" style="width: 250px"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleSave()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { formatDate } from "@/utils/date";
import { axios } from "../../utils";

//查询条件
const listQuery = {
  currentPage: 1,
  pageSize: 5,
  name: null,
  url: null,
  categoryId: null
};

//资源对象
const defaultResource = {
  id: null,
  name: null,
  url: null,
  categoryId: null,
  description: ""
};

export default {
  name: "resourceList",
  title: "资源管理",
  data() {
    return {
      listQuery, //查询条件
      total: 0,
      list: [], //资源数据
      cateList: [], //资源分类数据
      listLoading: false,
      dialogVisible: false,
      resource: Object.assign({}, defaultResource),
      isEdit: false,
      categoryOptions: [],
      defaultCategoryId: null
    };
  },
  //钩子函数
  created() {
    //获取资源数据
    this.getResourceList();

    //获取资源分类数据
    this.getResourceCateList();
  },
  methods: {
    //方法1: 获取资源数据
    getResourceList() {},

    //方法2: 获取资源分类数据
    getResourceCateList() {},

    //页码变化触发的函数
    handleCurrentPageChange(page) {
      this.listQuery.currentPage = page;
      this.getResourceList();
    },

    //每页显示条数变化触发的函数
    handlePageSizeChange(size) {
      this.listQuery.pageSize = size;
      this.getResourceList();
    },

    //查询按钮
    handleSearchList() {
      this.getResourceList();
    },

    //添加资源回显
    handleAdd() {},

    //添加&修改资源
    handleSave() {},

    //编辑资源 回显
    handleUpdate(row) {},

    //删除资源
    handleDelete(row) {},

    //资源分类管理
    handleShowCategory() {
      this.$router.push({ path: "/resourceCategory" });
    },

    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
    }
  },
  filters: {
    formatDateTime(time) {
      if (time == null || time === "") {
        return "N/A";
      }
      const date = new Date(time);
      return formatDate(date, "yyyy-MM-dd hh:mm:ss");
    }
  }
};
</script>
<style></style>
