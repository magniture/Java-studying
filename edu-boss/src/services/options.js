/**
 * Options service
 */

import ResourceUtil from './resourceUtil'

export default new ResourceUtil(process.env.VUE_APP_API_FAKE + '/options')
