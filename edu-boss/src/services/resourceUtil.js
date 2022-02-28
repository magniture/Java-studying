/**
 * Resource class
 */

import { axios } from '../utils'

export default class ResourceUtil {
  constructor (endpoint) {
    this.endpoint = endpoint
    this.axios = axios
  }

  get (id, options) {
    let url = this.endpoint
    if (typeof id === 'object') {
      options = id
    } else if (id !== undefined) {
      url += `/${id}`
    }
    return this.axios.get(url, options)
  }

  post (options) {
    const url = this.endpoint
    return this.axios.post(url, options)
  }

  put (id, options) {
    const url = `${this.endpoint}/${id}`
    return this.axios.put(url, options)
  }

  patch (id, options) {
    const url = `${this.endpoint}/${id}`
    return this.axios.patch(url, options)
  }

  delete (id, options) {
    const url = `${this.endpoint}/${id}`
    return this.axios.delete(url, options)
  }
}
