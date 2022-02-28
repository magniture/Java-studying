import { request } from './common'

export function policy () {
  return request({
    url: '/aliyun/oss/policy',
    method: 'get'
  })
}
