<template>
  <section class="users">
    <!-- 查询条件表单 -->
    <el-form class="actions" :model="filter" label-position="top" inline>
      <el-form-item label="手机号">
        <el-input v-model="filter.username" placeholder="请输入手机号" />
      </el-form-item>

      <el-form-item label="注册时间">
        <el-date-picker
          v-model="filter.resTime"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          range-separator="至"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleFilter">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table :data="users" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="用户ID" width="100"></el-table-column>
      <el-table-column label="头像" min-width="50">
        <template slot-scope="scope">
          <img
            class="avatar"
            :src="
              scope.row.portrait ||
                '//www.lgstatic.com/thumbnail_100x100/i/image2/M01/5E/65/CgotOVszSAOANi0LAAAse2IVWAE693.jpg'
            "
            :alt="scope.row.name"
          />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="用户名" min-width="150"></el-table-column>
      <el-table-column prop="phone" label="手机号" min-width="150"></el-table-column>

      <el-table-column prop="createTime" label="注册时间" min-width="180" :formatter="dateFormatter"></el-table-column>

      <!-- 状态显示 -->
      <el-table-column prop="status" label="状态" align="center" min-width="120">
        <template slot-scope="scope">
          <i class="status status-success" title="正常" v-if="scope.row.status === 'ENABLE'"></i>
          <i class="status status-danger" title="禁用" v-else-if="scope.row.status === 'DISABLE'"></i>
        </template>
      </el-table-column>

      <!-- 禁用按钮  v-if="scope.row.status == 'ENABLE'"-->
      <el-table-column label="操作" align="center" min-width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleToggleStatus(scope.row)"
          >{{ scope.row.status == "ENABLE" ? "禁用" : "启用" }}</el-button>
          <el-button size="mini" type="text" @click="handleSelectRole(scope.$index, scope.row)">分配角色</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-if="users.length"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handlePageSizeChange"
      @current-change="handleCurrentPageChange"
      :current-page="page"
      :page-sizes="[10, 20, 30]"
      :page-size="size"
      :total="total"
    />

    <el-dialog title="分配角色" :visible.sync="allocDialogVisible" width="30%">
      <el-select v-model="allocRoleIds" multiple placeholder="请选择" size="small" style="width: 80%">
        <el-option v-for="item in allRoleList" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allocDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleAllocDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </section>
</template>

<script>
import { axios } from "../utils";
import { getRoleByAdmin, getRolesById, allocRole } from "@/services/login";
import moment from "moment";

export default {
  name: "Users",
  title: "用户管理",
  data() {
    //日期选择器 选项
    const pickerOptions = {
      shortcuts: [
        {
          text: "最近一周",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit("pick", [start, end]);
          }
        },
        {
          text: "最近一个月",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit("pick", [start, end]);
          }
        },
        {
          text: "最近三个月",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit("pick", [start, end]);
          }
        }
      ]
    };

    //过滤信息
    const filter = {
      username: "",
      resTime: null
    };

    //数据部分
    return {
      pickerOptions, //日期选择器选项设置
      total: 0, //总条数
      size: 10, //每页显示条数
      page: 1, //当前页
      filter,
      users: [], //用户数据
      loading: false,
      allocAdminId: "",
      allocDialogVisible: false,
      allocRoleIds: [],
      allRoleList: []
    };
  },
  created() {
    //初始化用户数据
    this.loadUsers();
  },
  methods: {
    //方法1: 加载用户数据
    loadUsers() {
      //设置日期参数
      if (this.filter.resTime) {
        params.startCreateTime = this.filter.resTime[0];
        params.startCreateTime.setHours(0);
        params.startCreateTime.setMinutes(0);
        params.startCreateTime.setSeconds(0);

        params.endCreateTime = this.filter.resTime[1];
        params.endCreateTime.setHours(23);
        params.endCreateTime.setMinutes(59);
        params.endCreateTime.setSeconds(59);
      }
    },

    //方法2: 修改用户状态
    handleToggleStatus(item) {},

    //条件查询
    handleFilter() {
      this.loadUsers();
    },

    //页码变化触发的函数
    handleCurrentPageChange(page) {
      this.page = page;
      this.loadUsers();
    },

    //每页显示条数变化触发的函数
    handlePageSizeChange(size) {
      this.size = size;
      this.loadUsers();
    },

    //日期格式化
    dateFormatter(row, column, value, index) {
      return moment(value).format("YYYY-MM-DD HH:mm:ss");
    },

    //分配角色
    handleSelectRole(index, row) {
      this.allocAdminId = row.id;
      this.allocDialogVisible = true;
    }
  }
};
</script>

<style lang="scss">
.users {
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
