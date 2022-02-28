<template>
  <section class="course-item">
    <div class="header">
      <el-page-header @back="() => this.$router.back()" :content="pathTitle" />
      <el-button type="primary" @click="handleSave">保存</el-button>
    </div>

    <el-form ref="form" :model="course" :rules="rules" label-width="140px">
      <el-card shadow="never" v-loading="loading" element-loading-text="数据加载中...">
        <header slot="header">基本信息</header>
        <el-form-item label="名称" prop="courseName">
          <el-input v-model="course.courseName" type="text" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="简介" prop="brief">
          <el-input v-model="course.brief" type="text" maxlength="100" show-word-limit />
        </el-form-item>
        <el-form-item label="讲师姓名" prop="teacherName">
          <el-input v-model="course.teacherName" type="text" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="course.position" type="text" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="讲师简介" prop="description">
          <el-input v-model="course.description" type="text" maxlength="100" show-word-limit />
        </el-form-item>
        <el-form-item label="课程概述" prop="previewFirstField" class="form-control-summary">
          <el-input v-model="course.previewFirstField" type="text" maxlength="20" show-word-limit />
          <el-input
            v-model="course.previewSecondField"
            prop="previewSecondField"
            type="text"
            maxlength="20"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="课程封面" prop="courseImgUrl">
          <!-- 使用图片上传组件,完成图片上传 -->
          <upload-image
            :content="course.courseImgUrl && [course.courseImgUrl]"
            :get-urls="getCourseImgUrl"
            uploadUrl="/course/courseUpload"
            ref="courseCoverRef"
            max="10M"
            tipInfo="建议尺寸：230*300px，JPG、PNG格式，图片小于10M"
          ></upload-image>
        </el-form-item>
      </el-card>

      <el-card shadow="never" v-loading="loading" element-loading-text="数据加载中...">
        <header slot="header">销售信息</header>
        <el-form-item label="售卖价格" prop="discounts" type="number">
          <el-input v-model="course.discounts">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="商品原价">
          <el-input v-model="course.price" type="number">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="销量">
          <el-input v-model="course.sales" type="number">
            <template slot="append">单</template>
          </el-input>
        </el-form-item>
        <el-form-item label="活动标签" prop="discountsTag">
          <el-input v-model="course.discountsTag" type="text" maxlength="4" show-word-limit />
        </el-form-item>
      </el-card>

      <el-card shadow="never" v-loading="loading" element-loading-text="数据加载中...">
        <header slot="header">课程详情</header>
        <Editor
          ref="editorRef"
          uploadType="uploadFile"
          v-model="course.courseDescriptionMarkDown"
          :options="editorOptions"
          :visible="editorVisible"
          previewStyle="vertical"
          height="500px"
        />
      </el-card>

      <div class="actions">
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>
    </el-form>
  </section>
</template>

<script>
import Editor from "@/components/markdown-editor/Editor.vue";

import UploadImage from "@/components/UploadImage.vue";
import { axios } from "../../utils";

export default {
  name: "CourseItem",
  title: "营销信息",
  components: { Editor, UploadImage },
  data() {
    const rules = {
      courseName: [
        { required: true, message: "请输入课程名称", trigger: "blur" },
        { min: 3, max: 50, message: "长度在 3 到 50 个字符", trigger: "blur" }
      ],
      brief: [
        { required: true, message: "请输入课程简介", trigger: "blur" },
        { min: 3, max: 100, message: "长度在 3 到 100 个字符", trigger: "blur" }
      ],
      teacherDTO: {
        teacherName: [
          { required: true, message: "请输入讲师姓名", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        position: [
          { required: true, message: "请输入讲师职位", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        description: [
          { required: true, message: "请输入讲师简介", trigger: "blur" },
          { min: 3, max: 50, message: "长度在 3 到 50 个字符", trigger: "blur" }
        ]
      },
      previewFirstField: [
        { required: true, message: "请输入课程概述", trigger: "blur" },
        { min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "blur" }
      ],
      previewSecondField: [
        { required: true, message: "请输入课程概述", trigger: "blur" },
        { min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "blur" }
      ],
      sortNum: [{ required: true, message: "请输入课程排序", trigger: "blur" }],
      discounts: [
        { required: true, message: "请输入课程售价", trigger: "blur" }
      ],
      discountsTag: [
        { min: 2, max: 4, message: "长度在 2 到 4 个字符", trigger: "blur" }
      ]
    };

    return {
      pathTitle: "",
      rules,
      //课程对象
      course: {
        brief: "",
        courseDescriptionMarkDown: "",
        courseImgUrl: "",
        courseListImg: "",
        courseName: "",
        discounts: "",
        discountsTag: "",
        id: "",
        previewFirstField: "",
        previewSecondField: "",
        price: "",
        sales: "",
        sortNum: "",
        status: "",
        teacherName: "",
        position: "",
        description: "",
        teacherDTO: {},
        activityCourse: false,
        activityCourseDTO: {}
      },
      editorOptions: {},
      editorHtml: "",
      editorVisible: true,
      loading: false
    };
  },

  //钩子函数
  created() {
    //获取课程id
    const id = this.$route.params.courseId;
    if (!id) return this.redirectToError();

    //判断是新建还是修改
    if (id === "new") {
      this.pathTitle = "新增课程";
      
      this.$breadcrumbs = [
        { name: "Courses", text: "课程管理" },
        { text: "新增课程" }
      ];
    } else {
      this.$breadcrumbs = [
        { name: "Courses", text: "课程管理" },
        { text: "营销信息" }
      ];

      //修改操作回显课程
      this.loadCourse(id);
    }
  },
  methods: {
    //回显课程信息
    loadCourse(id) {
      this.loading = true;
      return axios
        .get("/course/findCourseById?id=" + id)
        .then(resp => {
          console.log(resp);
          this.pathTitle = resp.data.content.courseName;
          this.course = Object.assign(this.course, resp.data.content);
          this.course.id = id;
          this.loading = false;
        })
        .catch(error => {
          this.$message.error("回显数据失败! !");
        });
    },

    //保存修改课程信息
    handleSave() {

      //校验是否符合规则
      this.$refs.form.validate(valid => {
        if (!valid) return false;
        axios
          .post("/course/saveOrUpdateCourse", this.course)
          .then(res => {
            //退回到上个页面
            this.$router.back();
          })
          .catch(error => {
            this.$message.error("保存课程信息失败! ! !");
          });
      });
    },

    redirectToError() {
      // TODO: Error components
      this.$router.replace({ path: "/not-found" });
    },

    //获取图片路径,进行回显
    getCourseImgUrl(urls) {
      debugger;
      if (urls.length > 0) {
        this.course.courseImgUrl = urls && urls[0].filePath;
      }
    }
  }
};
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

      &:before,
      &:after {
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
