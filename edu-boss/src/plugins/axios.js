/**
 * Inject Axios into Vue component
 */

import { axios } from '../utils'

export default Vue => {
  // mount the axios to Vue
  Object.defineProperties(Vue, {
    axios: { get: () => axios }
  })

  // mount the axios to Vue component instance
  Object.defineProperties(Vue.prototype, {
    $axios: { get: () => axios }
  })
}
