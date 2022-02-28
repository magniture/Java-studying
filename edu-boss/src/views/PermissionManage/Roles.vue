<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <span>筛选搜索</span>
        <el-button style="float:right" type="primary" @click="handleSearchList()" size="small">查询搜索</el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.name" class="input-width" placeholder="角色名称" clearable></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <!-- 添加角色 -->
    <el-card class="operate-container" shadow="never">
      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加角色</el-button>
    </el-card>

    <div class="table-container">
      <el-table ref="roleTable" :data="list" style="width: 100%;" v-loading="listLoading" border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="角色名称" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="描述" align="center">
          <template slot-scope="scope">{{scope.row.description}}</template>
        </el-table-column>
        <el-table-column label="添加时间" width="180" align="center">
          <template slot-scope="scope">{{scope.row.createdTime | formatDateTime}}</template>
        </el-table-column>

        <!-- 分配菜单&分配资源 -->
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-row>
              <el-button size="mini" type="text" @click="handleSelectMenu(scope.row)">分配菜单</el-button>
              <el-button
                size="mini"
                type="text"
                @click="handleSelectResource(scope.$index, scope.row)"
              >分配资源</el-button>
            </el-row>
            <el-row>
              <el-button size="mini" type="text" @click="handleUpdate( scope.row)">编辑</el-button>
              <el-button size="mini" type="text" @click="handleDelete(scope.row)">删除</el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 添加&修改 角色对话框 -->
    <el-dialog :title="isEdit?'编辑角色':'添加角色'" :visible.sync="dialogVisible" width="40%">
      <el-form :model="role" label-width="150px" size="small">
        <el-form-item label="角色名称：">
          <el-input v-model="role.name" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="角色编码：">
          <el-input v-model="role.code" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="描述：">
          <el-input v-model="role.description" type="textarea" :rows="5" style="width: 250px"></el-input>
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

const defaultRole = {
  id: null,
  name: null,
  description: null,
  adminCount: 0,
  status: 1
};
export default {
  name: "roleList",
  title: "角色管理",

  data() {
    return {
      listQuery: { name: "" },
      list: null,
      listLoading: false,
      dialogVisible: false,
      role: Object.assign({}, defaultRole),
      isEdit: false
    };
  },
  created() {
    //获取角色列表
    this.loadRoles();
  },
  methods: {
    //获取角色数据
    loadRoles() {},

    //条件查询
    handleSearchList() {
      this.loadRoles();
    },

    //添加角色弹窗
    handleAdd() {
      this.dialogVisible = true; //打开对话框
      this.isEdit = false; //false 修改操作
      this.role = Object.assign({}, defaultRole);
    },

    //修改角色弹窗
    handleUpdate(row) {
      this.dialogVisible = true;
      this.isEdit = true;
      //回显数据
      this.role = Object.assign({}, row);
    },

    //添加&修改角色
    handleSave() {},

    //删除角色
    handleDelete(row) {},

    //为角色分配菜单
    handleSelectMenu(row) {
      this.$router.push({ path: "/allocMenu", query: { roleId: row.id } });
    },

    //为角色分配资源
    handleSelectResource(index, row) {
      this.$router.push({ path: "/allocResource", query: { roleId: row.id } });
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
