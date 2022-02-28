/**
 * Contents service
 */

import { request, postRequest, bossBase } from './common'

export const getSectionAndLesson = async (lessonId) => {
  return await request(bossBase + '/course/lesson/getById', {
    params: { lessonId }
  })
}

export const saveOrUpdate = async (params) => {
  // TODO: update or save logic
  const data = { ...params }
  return await postRequest(bossBase + '/course/lesson/saveOrUpdate', data)
}
