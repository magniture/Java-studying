<template>
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <el-button size="mini" class="btn-add" @click="handleAdd()">添加广告位</el-button>
    </el-card>

    <div class="table-container">
      <el-table
        ref="homeAdvertiseTable"
        :data="list"
        style="width: 100%;"
        v-loading="listLoading"
        border
      >
        <el-table-column label="spaceKey" width="120" align="center">
          <template slot-scope="scope">{{scope.row.spaceKey}}</template>
        </el-table-column>
        <el-table-column label="广告位名称" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" :formatter="dateFormatter"></el-table-column>
        <el-table-column label="更新时间" align="center" prop="updateTime" :formatter="dateFormatter"></el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { axios } from "../../utils";
import moment from "moment";

export default {
  name: "homeAdvertiseList",
  title: "广告位管理",
  data() {
    return {
      list: null,
      listLoading: false
    };
  },
  created() {
    //加载广告位数据
    this.loadPromotionSpace();
  },
  methods: {
    //方法1: 加载广告位信息
    loadPromotionSpace() {
     
    },

    //添加广告位跳转
    handleAdd() {
      this.$router.push({ path: "/addAdvertiseSpace" });
    },

    //修改广告位跳转
    handleUpdate(index, row) {
      this.$router.push({
        path: "/updateAdvertiseSpace",
        query: { id: row.id }
      });
    },

    dateFormatter(row, column, value, index) {
      return moment(value).format("YYYY-MM-DD HH:mm:ss");
    }
  }
};
</script>
<style scoped>
.input-width {
  width: 203px;
}
</style>
