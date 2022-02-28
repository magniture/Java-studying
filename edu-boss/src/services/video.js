/**
 * Video service
 */

import { request, postRequest, bossBase } from './common'

export const getMedia = async (lessonId) => {
  return await request(`${bossBase}/course/upload/getMediaByLessonId.json`, {
    params: {
      lessonId
    }
  })
}

export const getImgAuth = async () => {
  return await request(`${bossBase}/course/upload/aliyunImagUploadAddressAdnAuth.json`)
}

export const getVideoAuth = async ({
  fileName,
  imageUrl,
  videoId
}) => {
  if (videoId) {
    return await request(`${bossBase}/course/upload/refreshAliyunVideoUploadAddressAdnAuth.json`, {
      params: {
        fileName,
        imageUrl,
        videoId
      }
    })
  } else {
    return await request(`${bossBase}/course/upload/aliyunVideoUploadAddressAdnAuth.json`, {
      params: {
        fileName,
        imageUrl
      }
    })
  }
}

export const transCode = async (lessonId, vid, coverUrl, fileName) => {
  return await postRequest(`${bossBase}/course/upload/aliyunTransCode.json`, {
    lessonId,
    fileId: vid,
    coverImageUrl: coverUrl,
    fileName: fileName
  }
  )
}

export const transCodeResult = async (lessonId) => {
  return await request(`${bossBase}/course/upload/aliyunTransCodePercent.json`, {
    params: {
      lessonId
    }
  })
}
