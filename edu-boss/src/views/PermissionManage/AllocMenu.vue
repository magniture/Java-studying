<template>
  <el-card class="form-container" shadow="never">
    <el-tree
      :data="menuTreeList"
      show-checkbox
      default-expand-all
      node-key="id"
      ref="tree"
      highlight-current
      :props="defaultProps"
    ></el-tree>
    <div style="margin-top: 20px" align="center">
      <el-button type="primary" @click="handleSave()">保存</el-button>
      <el-button @click="handleClear()">清空</el-button>
    </div>
  </el-card>
</template>

<script>
import { axios } from "../../utils";

export default {
  name: "allocMenu",
  title: "角色菜单管理",
  data() {
    return {
      menuTreeList: [], //菜单数据
      checkedMenuId: [], //被选中的菜单

      //树形结构子节点设置
      defaultProps: {
        children: "subMenuList",
        label: "name"
      },
      roleId: null
    };
  },
  //钩子函数
  created() {
    //获取路由携带的id
    this.roleId = this.$route.query.roleId;

    //获取菜单列表
    this.treeList();

    //获取角色所拥有的菜单信息
    this.getRoleMenu(this.roleId);
  },
  methods: {
    //方法1: 获取菜单列表,使用树形控件展示
    treeList() {},

    //方法2: 获取当前角色所拥有菜单列表id
    getRoleMenu(roleId) {},

    //方法3: 修改角色所拥有的菜单列表
    handleSave() {},

    //清空选择
    handleClear() {
      this.$refs.tree.setCheckedKeys([]);
    }
  }
};
</script>

<style scoped>
</style>
