import { TokenService, OptionService, CourseService, SectionService, CommentsService, LessonService, UserService, VideoService } from '../services'

export default Vue => {
  // alias
  const services = {
    token: TokenService,
    option: OptionService,
    course: CourseService,
    section: SectionService,
    lesson: LessonService,
    user: UserService,
    video: VideoService,
    comments: CommentsService
  }

  // mount the services to Vue
  Object.defineProperties(Vue, {
    services: { get: () => services }
  })

  // mount the services to Vue component instance
  Object.defineProperties(Vue.prototype, {
    $services: { get: () => services }
  })
}
