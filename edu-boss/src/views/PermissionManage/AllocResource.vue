<template>
  <el-card class="form-container" shadow="never">
    <div
      v-for="(cate,index) in allResourceCate"
      :class="index===0?'top-line':null"
      :key="'cate'+cate.id"
    >
      <!-- 资源分类名称 -->
      <el-row class="table-layout" style="background: #F2F6FC;">
        <el-checkbox
          v-model="cate.selected"
          :indeterminate="isIndeterminate(cate.id)"
          @change="handleCheckAllChange(cate)"
        >{{cate.name}}</el-checkbox>
      </el-row>

      <!-- 资源分类下的资源信息 -->
      <el-row class="table-layout">
        <el-col
          :span="8"
          v-for="resource in getResourceByCate(cate.id)"
          :key="resource.id"
          style="padding: 4px 0"
        >
          <el-checkbox
            v-model="resource.selected"
            @change="handleCheckChange(resource)"
          >{{resource.name}}</el-checkbox>
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 20px" align="center">
      <el-button type="primary" @click="handleSave()">保存</el-button>
      <el-button @click="handleClear()">清空</el-button>
    </div>
  </el-card>
</template>

<script>
import { fetchAllResourceList } from "@/services/resource";
import { listAllCate } from "@/services/resourceCategory";
import { allocResource, listResourceByRole } from "@/services/role";

import { axios } from "../../utils";

export default {
  name: "allocResource",
  title: "角色资源管理",
  data() {
    return {
      roleId: null,
      allResource: null,
      allResourceCate: null,
      listQuery: { currentPage: 1, pageSize: 200 }
    };
  },

  created() {
    //获取当前角色ID
    this.roleId = this.$route.query.roleId;

    //获取资源数据
    //this.getAllResourceCateList();
    this.getCateList();
  },
  methods: {
    //方法1: 获取资源分类信息
    getCateList() {
      axios
        .get("/ResourceCategory/findAllResourceCategory")
        .then(res => {
          this.allResourceCate = res.data.content;
          for (let i = 0; i < this.allResourceCate.length; i++) {
            this.allResourceCate[i].selected = false;
          }

          //获取资源数据
          this.getResourceList();
        })
        .catch(err => {
          this.$message.error("数据获取失败! ! !");
        });
    },

    //方法2: 获取资源数据
    getResourceList() {
      axios
        .post("/resource/findAllResource", this.listQuery)
        .then(res => {
          this.allResource = res.data.content.list;
          //console.log(res.data.content.list);
          for (let i = 0; i < this.allResource.length; i++) {
            this.allResource[i].selected = false;
          }

          //获取当前角色拥有的资源列表
          this.getResourceByRoleId(this.roleId);
        })
        .catch(err => {
          this.$message.error("数据获取失败! ! !");
        });
    },

    //方法3: 获取当前角色拥有的资源列表
    getResourceByRoleId(roleId) {
      axios
        .get("/role/findResourceListByRoleId?roleId=" + roleId)
        .then(res => {
          //获取数据,当前用户拥有的资源列表
          const allocResource = res.data.content;
          console.log(allocResource);

          //遍历allResource,选中角色已拥有的资源
          this.allResource.forEach(item => {
            item.selected = this.getResourceChecked(item.id, allocResource);
          });

          //遍历allResourceCate,选中角色拥有的资源分类
          this.allResourceCate.forEach(item => {
            item.selected = this.isAllChecked(item.id);
          });

          this.$forceUpdate();
        })
        .catch(err => {
          this.$message.error("数据获取失败! ! !");
        });
    },

    //方法4: 勾选当前角色拥有的资源
    getResourceChecked(resourceId, allocResource) {
      if (allocResource == null || allocResource.length === 0) return false;
      let result = false;

      for (let i = 0; i < allocResource.length; i++) {
        allocResource[i].resourceList.forEach(item => {
          if (resourceId == item.id) {
            result = true;
          }
        });
      }
      return result;
    },

    //方法5: 勾选当前角色拥有的资源分类
    isAllChecked(categoryId) {
      //根据分类ID 获取该分类下的资源
      const cateResources = this.getResourceByCate(categoryId);
      if (cateResources == null) return false;
      let checkedCount = 0;

      //遍历判断是否有被选中的资源
      for (let i = 0; i < cateResources.length; i++) {
        if (cateResources[i].selected === true) {
          checkedCount++;
        }
      }

      //判断没有返回 false
      if (checkedCount === 0) {
        return false;
      }

      //如果全都选中, 就返回true让分类的选项勾选
      return checkedCount === cateResources.length;
    },

    //方法6: 根据分类ID 获取该分类下的资源
    getResourceByCate(categoryId) {
      const cateResource = [];
      if (this.allResource == null) return null;
      for (let i = 0; i < this.allResource.length; i++) {
        const resource = this.allResource[i];
        if (resource.categoryId === categoryId) {
          cateResource.push(resource);
        }
      }
      return cateResource;
    },

    //方法7: 全选按钮被触发时执行(全选全不选)
    isIndeterminate(categoryId) {
      const cateResources = this.getResourceByCate(categoryId);
      if (cateResources == null) return false;
      let checkedCount = 0;
      for (let i = 0; i < cateResources.length; i++) {
        if (cateResources[i].selected === true) {
          checkedCount++;
        }
      }
      return !(checkedCount === 0 || checkedCount === cateResources.length);
    },

    //方法8: 为角色分配资源
    handleSave() {
      this.$confirm("是否分配资源？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        const checkedResourceIds = [];

        //遍历获取到被选中的资源,保存到 checkedResourceIds
        if (this.allResource != null && this.allResource.length > 0) {
          this.allResource.forEach(item => {
            if (item.selected) {
              checkedResourceIds.push(item.id);
            }
          });
        }

        //定义请求参数
        const params = {
          roleId: this.roleId, //角色id
          resourceIdList: checkedResourceIds //资源id集合
        };

        //发送请求
        axios
          .post("/role/roleContextResource", params)
          .then(res => {
            this.$router.back();
          })
          .catch(err => {
            this.$message.error("权限分配失败! ! !");
          });
      });
    },

    handleClear() {
      this.allResourceCate.forEach(item => {
        item.selected = false;
      });
      this.allResource.forEach(item => {
        item.selected = false;
      });
      this.$forceUpdate();
    },

    handleCheckAllChange(cate) {
      const cateResources = this.getResourceByCate(cate.id);
      for (let i = 0; i < cateResources.length; i++) {
        cateResources[i].selected = cate.selected;
      }
      this.$forceUpdate();
    },

    handleCheckChange(resource) {
      this.allResourceCate.forEach(item => {
        if (item.id === resource.categoryId) {
          item.selected = this.isAllChecked(resource.categoryId);
        }
      });
      this.$forceUpdate();
    }
  }
};
</script>

<style scoped>
.table-layout {
  padding: 20px;
  border-left: 1px solid #dcdfe6;
  border-right: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
}

.top-line {
  border-top: 1px solid #dcdfe6;
}
</style>
