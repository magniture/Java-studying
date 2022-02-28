/**
 * Users service
 */

//
import { request, postRequest, bossBase } from './common'
//
// export const getUserInfo = async (courseId) => {
//   return await LoginRequest(`${process.env.VUE_APP_API_FAKE}/user/getInfo`)
// }

export const getCommentsList = async (params) => {
  return await postRequest(`${bossBase}/comment/getCourseCommentList`, params)
}

export const saveOrUpdateComment = async (params) => {
  let url = `${bossBase}/comment/addComment`
  if (params.id) {
    url = `${bossBase}/comment/updateComment`
  }
  return await postRequest(url, params)
}

export const getCommentById = async (commentId) => {
  return await request(`${bossBase}/comment/getCourseComment?b`, {
    params: { commentId }
  })
}
