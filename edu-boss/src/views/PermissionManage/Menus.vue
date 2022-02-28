<template>
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <el-button class="btn-add" @click="handleAddMenu()" size="mini">添加菜单</el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="menuTable" style="width: 100%" :data="list" v-loading="listLoading" border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="菜单名称" align="center" min-width="100">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="菜单级数" min-width="100" align="center">
          <template slot-scope="scope">{{scope.row.level | levelFilter}}</template>
        </el-table-column>
        <el-table-column label="前端图标" min-width="100" align="center">
          <template slot-scope="scope">{{scope.row.icon}}</template>
        </el-table-column>
        <el-table-column label="排序" min-width="100" align="center">
          <template slot-scope="scope">{{scope.row.orderNum }}</template>
        </el-table-column>
        <el-table-column label="操作" min-width="200" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page="page"
        :page-sizes="[5,10, 20]"
        :page-size="size"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import { axios } from "../../utils";

export default {
  name: "menuList",
  title: "菜单管理",
  data() {
    return {
      total: 0, //总条数
      size: 10, //每页显示条数
      page: 1, //当前页
      list: [], //广告数据
      listLoading: false
    };
  },
  created() {
    //获取菜单列表
    this.loadMenuList();
  },

  methods: {
    //方法1: 加载菜单列表数据
    loadMenuList() {},

    //页码变化触发的函数
    handleCurrentPageChange(page) {
      this.page = page;
      this.loadMenuList();
    },

    //每页显示条数变化触发的函数
    handlePageSizeChange(size) {
      this.size = size;
      this.loadMenuList();
    },

    //新增菜单跳转
    handleAddMenu() {
      this.$router.push("/addMenu");
    },

    //修改菜单跳转
    handleUpdate(index, row) {
      this.$router.push({ path: "/updateMenu", query: { id: row.id } });
    },

    //删除功能
    handleDelete(index, row) {}
  },
  filters: {
    levelFilter(value) {
      if (value === 0) {
        return "一级";
      } else if (value === 1) {
        return "二级";
      }
    },
    disableNextLevel(value) {
      if (value === 0) {
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style scoped>
</style>
