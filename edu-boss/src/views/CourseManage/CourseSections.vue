<template>
  <section class="course-sections">
    <div class="header">
      <el-page-header @back="() => this.$router.back()" :content="addSectionForm.courseName" />
      <el-button type="primary" icon="el-icon-plus" @click="handleShowAddSection">添加章节</el-button>
    </div>
    <el-tree
      :data="sections"
      :props="treeProps"
      draggable
      :allow-drop="allowDrop"
      v-loading="loading"
      element-loading-text="数据加载中..."
    >
      <div class="inner" slot-scope="{ data, node }">
        <span>{{ node.label }}</span>
        <!-- section -->
        <span class="actions" v-if="data.sectionName">
          <el-button size="small" @click.stop="handleEditSection(data)">编辑</el-button>
          <el-button size="small" type="primary" @click.stop="handleShowAddLesson(data)">添加课时</el-button>
          <el-button
            size="small"
            @click.stop="handleShowToggleStatus(data)"
          >{{ statusMapping[data.status] }}</el-button>
        </span>
        <!-- lesson -->
        <span class="actions" v-else>
          <el-button size="small" @click.stop="handleEditLesson(data, node.parent.data)">编辑</el-button>
          <!-- TODO: 上传视频 -->
          <!-- <el-button size="small" type="success" @click="uploadVedio(data)">上传视频</el-button> -->
          <el-button
            size="small"
            @click.stop="handleShowToggleStatus(data)"
          >{{ statusMapping[data.status] }}</el-button>
        </span>
      </div>
    </el-tree>

    <!-- 章节状态 -->
    <el-dialog class="toggle-dialog" title="提示" :visible.sync="showToggleStatusForm" width="30%">
      <header class="toggle-header">
        <i class="el-icon-info"></i>
        <span>当前状态：{{ toggleStatusForm.data && statusMapping[toggleStatusForm.data.status] }}</span>
      </header>
      <el-form label-position="right" label-width="110px" :model="toggleStatusForm">
        <el-form-item label="状态变更为：">
          <el-select v-model="toggleStatusForm.status" style="width: 100%">
            <el-option
              v-for="item in Object.keys(statusMapping)"
              :key="item"
              :label="statusMapping[item]"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showToggleStatusForm = false">取 消</el-button>
        <el-button type="primary" @click="handleToggleStatus">确 定</el-button>
      </span>
    </el-dialog>


    <el-dialog class="add-dialog" title="章节信息" :visible.sync="showAddSection">
      <el-form
        label-position="right"
        label-width="80px"
        ref="addSectionForm"
        :model="addSectionForm"
        :rules="addSectionFormRules"
      >
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="addSectionForm.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="章节名称" prop="sectionName">
          <el-input v-model="addSectionForm.sectionName"></el-input>
        </el-form-item>
        <el-form-item label="章节描述" prop="description">
          <el-input type="textarea" v-model="addSectionForm.description"></el-input>
        </el-form-item>
        <el-form-item label="章节排序" prop="orderNum">
          <el-input v-model="addSectionForm.orderNum" type="number">
            <template slot="append">数字控制排序，数字越大越靠后</template>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showAddSection = false">取 消</el-button>
        <el-button type="primary" @click="handleAddSection">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加课时表单 -->
    <el-dialog class="add-dialog" title="课时基本信息" :visible.sync="showAddLesson">
      <el-form
        label-position="right"
        label-width="110px"
        ref="addLessonForm"
        :model="addLessonForm"
        :rules="addLessonFormRules"
      >
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="addLessonForm.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="章节名称" prop="sectionName">
          <el-input v-model="addLessonForm.sectionName" disabled></el-input>
        </el-form-item>
        <el-form-item label="课时名称" prop="theme">
          <el-input v-model="addLessonForm.theme"></el-input>
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-input v-model="addLessonForm.duration" type="number">
            <template slot="append">分钟</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否开放试听" prop="isFree">
          <!-- active-value switch 打开时的值	, inactive-value switch 关闭时的值	 -->
          <el-switch v-model="addLessonForm.isFree" :active-value="0" :inactive-value="1"></el-switch>
          <!-- <el-switch v-model="addLessonForm.isFree"></el-switch> -->
        </el-form-item>
        <el-form-item label="课时排序" prop="orderNum">
          <el-input v-model="addLessonForm.orderNum" type="number">
            <template slot="append">数字控制排序，数字越大越靠后</template>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showAddLesson = false">取 消</el-button>
        <el-button type="primary" @click="handleAddLesson">确 定</el-button>
      </span>
    </el-dialog>
  </section>
</template>

<script>
import { axios } from "../../utils";
export default {
  inject: ["reload"],
  name: "CourseSections",
  title: "课程结构",
  data() {
    //定义节点名称和子节点的内容
    const treeProps = {
      label: item => {
        return item.sectionName || item.theme;
      },
      children: "lessonList"
    };

    const allowDrop = (drag, drop, type) => {
      return drag.data.sectionId === drop.data.sectionId && type !== "inner";
    };

    //状态信息
    const statusMapping = {
      0: "已隐藏",
      1: "待更新",
      2: "已更新"
    };

    const toggleStatusForm = {
      status: 0
    };

    const addSectionForm = {
      courseId: undefined,
      courseName: "",
      sectionName: "",
      description: "",
      orderNum: 0
    };

    const addSectionFormRules = {
      courseName: [
        { required: true, message: "请输入课程名称", trigger: "blur" },
        { min: 3, max: 50, message: "长度在 3 到 50 个字符", trigger: "blur" }
      ],
      sectionName: [
        { required: true, message: "请输入章节名称", trigger: "blur" },
        { min: 3, max: 40, message: "长度在 3 到 40 个字符", trigger: "blur" }
      ],
      description: [
        { min: 3, max: 100, message: "长度在 3 到 100 个字符", trigger: "blur" }
      ],
      orderNum: []
    };

    const addLessonForm = {
      courseId: undefined,
      courseName: "",
      sectionId: undefined,
      sectionName: "",
      theme: "",
      duration: undefined,
      isFree: 1,
      orderNum: 0
    };

    const addLessonFormRules = {
      courseName: [
        { required: true, message: "请输入课程名称", trigger: "blur" },
        { min: 3, max: 50, message: "长度在 3 到 50 个字符", trigger: "blur" }
      ],
      sectionName: [
        { required: true, message: "请输入章节名称", trigger: "blur" },
        { min: 3, max: 40, message: "长度在 3 到 40 个字符", trigger: "blur" }
      ],
      theme: [
        { required: true, message: "请输入课时名称", trigger: "blur" },
        { min: 3, max: 100, message: "长度在 3 到 100 个字符", trigger: "blur" }
      ],
      duration: [
        { required: true, message: "请输入课时时长", trigger: "blur" }
      ],
      //isFree: [{ required: true, trigger: "blur" }],
      orderNum: []
    };

    return {
      course: {},
      sections: [],
      treeProps,
      allowDrop,
      statusMapping,
      toggleStatusForm,
      showToggleStatusForm: false,
      addSectionForm,
      addSectionFormRules,
      showAddSection: false,
      addLessonForm,
      addLessonFormRules,
      showAddLesson: false,
      loading: false
    };
  },
  created() {
    //1.显示当前页面在网站中的位置
    this.$breadcrumbs = [
      { name: "Courses", text: "课程管理" },
      { text: "课程结构" }
    ];

    //2.从路由中获取传递的参数 课程id
    const id = this.$route.params.courseId;
    if (!id) return this.redirectToError();

    this.loading = true;

    //3.加载课程信息
    this.loadCourse(id);

    //4.加载课程内容
    this.loadSections(id);
  },
  methods: {
    //加载课程信息
    loadCourse(id) {
      axios
        .get("/courseContent/findCourseByCourseId?courseId=" + id)
        .then(res => {
          const course = res.data.content;

          //将数据保存到章节表单对象中
          this.addSectionForm.courseId = course.id;
          this.addSectionForm.courseName = course.courseName;

          //将数据保存到课时表单对象中
          this.addLessonForm.courseId = course.id;
          this.addLessonForm.courseName = course.courseName;
        })
        .catch(error => {
          this.$message.error("数据获取失败! ! !");
        });
    },

    //加载课程内容(树形结构)
    loadSections(courseId) {
      this.loading = true;
      axios
        .get("/courseContent/findSectionAndLesson?courseId=" + courseId)
        .then(res => {
          this.sections = res.data.content;
          console.log(res.data.content);
          this.loading = false;
        })
        .catch(error => {
          this.$message.error("数据获取失败! ! !");
        });
    },

    //显示新增章节表单
    handleShowAddSection() {
      this.addSectionForm = {
        courseId: this.addSectionForm.courseId,
        courseName: this.addSectionForm.courseName
      };
      this.showAddSection = true;
    },

    //添加&修改章节
    handleAddSection() {
      debugger;
      console.log(this.addSectionForm);
      axios
        .post("/courseContent/saveOrUpdateSection", this.addSectionForm)
        .then(res => {
          this.showAddSection = false;
          //重新加载列表
          return this.loadSections(this.addSectionForm.courseId);
        })
        .then(() => {
          //重置表单内容
          this.addSectionForm.sectionName = "";
          this.addSectionForm.description = "";
          this.addSectionForm.orderNum = 0;
          this.reload();
        })
        .catch(error => {
          this.showAddSection = false;
          this.$message.error("操作执行失败! ! !");
        });
    },

    //编辑章节(回显)
    handleEditSection(section) {
      this.addSectionForm = Object.assign(this.addSectionForm, section);
      this.showAddSection = true;
    },

    //回显章节状态
    handleShowToggleStatus(data) {
      //保存状态表单数据
      this.toggleStatusForm.id = data.id;
      this.toggleStatusForm.status = data.status.toString();
      this.toggleStatusForm.data = data;
      this.showToggleStatusForm = true;
    },

//修改章节状态
handleToggleStatus() {
  //判断要修改的状态
  if (this.toggleStatusForm.data.sectionName) {
    //修改章节状态
    axios
      .get("/courseContent/updateSectionStatus", {
        params: {
          id: this.toggleStatusForm.id,
          status: this.toggleStatusForm.status
        }
      })
      .then(resp => {
        this.toggleStatusForm.data.status = this.toggleStatusForm.status;
        this.toggleStatusForm = {};
        this.showStatusForm = false;
        this.reload();
      })
      .catch(error => {
        this.showStatusForm = false;
        this.$message.error("修改状态失败! ! !");
      });
  } else {
    //修改课时状态
  }
},

    //添加课时回显
    handleShowAddLesson(section) {
      this.addLessonForm.sectionId = section.id;
      this.addLessonForm.sectionName = section.sectionName;
      this.showAddLesson = true;
    },

    //添加&修改课时
    handleAddLesson() {
      this.$refs.addLessonForm.validate(valid => {
        if (!valid) return false;

        axios
          .post("/courseContent/saveOrUpdateLesson", this.addLessonForm)
          .then(res => {
            this.showAddLesson = false;
            return this.loadSections(this.addLessonForm.courseId);
          })
          .then(() => {
            //重置表单
            this.addLessonForm.theme = "";
            this.addLessonForm.duration = undefined;
            this.addLessonForm.isFree = 0;
            this.addLessonForm.order_num = 0;
            this.reload();
          })
          .catch(error => {
            this.$message.error("操作失败! ! !");
          });
      });
    },

    //编辑课时回显
    handleEditLesson(lesson, section) {
      this.addLessonForm = Object.assign(this.addLessonForm, lesson);
      this.addLessonForm.sectionName = section.sectionName;
      this.showAddLesson = true;
    },

    uploadVedio(data) {
      this.$services.course.getCourseById(data.courseId).then(course => {
        this.$router.push({
          name: "VideoOptions",
          params: {
            courseId: data.courseId,
            courseName: course.courseName,
            lessonId: data.id,
            lessonName: data.theme
          }
        });
      });
    },

    redirectToError() {
      // TODO: Error components
      this.$router.replace({ path: "/not-found" });
    }
  }
};
</script>

<style lang="scss">
@import "../../theme";

.course-sections {
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .el-tree {
    margin-top: 20px;
  }

  .el-tree,
  .el-tree__empty-block {
    min-height: 80vh;
  }

  .el-tree-node__content {
    height: auto;
  }

  .el-tree-node__expand-icon {
    padding: 15px;
    font-size: 20px;
  }

  .inner {
    display: flex;
    flex: 1 0 0;
    align-items: center;
    box-sizing: border-box;
    padding: 9px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  }

  .el-tree-node__children {
    background-color: lighten($body-bg, 5);

    .el-tree-node__expand-icon {
      padding: 12px;
    }

    .inner {
      padding-top: 6px;
      padding-bottom: 6px;
    }
  }

  .actions {
    margin-left: auto;
  }

  .add-dialog {
    .el-form {
      width: 90%;
      max-width: 660px;
      margin: 0 auto;
    }
  }

  .toggle-header {
    margin-bottom: 20px;
    text-align: center;

    i {
      display: block;
      margin-bottom: 20px;
      font-size: 50px;
    }

    span {
      display: block;
      font-size: 20px;
    }
  }
}
</style>
