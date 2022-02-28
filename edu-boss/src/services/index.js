/**
 * Services
 * http://www.ruanyifeng.com/blog/2014/05/restful_api.html
 */

// ECMAScript 2021 Draft
// export * as CourseService from './courses'
import * as CourseService from './course'
import * as VideoService from './video'
import * as SectionService from './section'
import * as LessonService from './lesson'
import * as TokenService from './tokens'
import * as UserService from './users'
import * as CommentsService from './comments'
export { default as OptionService } from './options'

export { CourseService, TokenService, UserService, CommentsService, VideoService, SectionService, LessonService }
