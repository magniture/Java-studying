import Vue from 'vue'
import { AxiosInstance } from 'axios'
import { NProgress } from 'nprogress'
import { CourseService, TokenService, UserService, CommentsService, VideoService, SectionService, LessonService } from './services'

interface Services {
  course: typeof CourseService
  section: typeof SectionService
  users: typeof UserService
  comments: typeof CommentsService
  lesson: typeof LessonService
}

interface Breadcrumb extends Location {
  text: string
}

declare module 'vue/types/vue' {
  interface Vue {
    $axios: AxiosInstance
    $nprogress: NProgress
    $services: Services
    $title: string
    $breadcrumbs: Breadcrumb[]
  }
}
