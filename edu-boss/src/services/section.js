/**
 * Contents service
 */

import { request, postRequest, bossBase } from './common'

export const getSectionAndLesson = async (courseId) => {
  return await request(bossBase + '/course/section/getSectionAndLesson', {
    params: { courseId }
  })
}

export const saveOrUpdateSection = async (params) => {
  // TODO: update or save logic
  const data = { ...params }
  return await postRequest(bossBase + '/course/section/saveOrUpdateSection', data)
}
