/**
 * Change title & breadcrumbs when route
 * TODO: title case
 */
import router from '../router'
import store from '../store'

const property = 'title'
const separator = ' - '

export default Vue => {
  router.afterEach(route => {
    const current = route.matched[route.matched.length - 1].components.default
    const title = current[property] || current.name
    const items = [process.env.VUE_APP_TITLE]
    title && items.unshift(title)

    // change title
    store.dispatch('changeTitle', items.join(separator))

    // change breadcrumbs
    store.dispatch('changeBreadcrumbs', [{ text: title }])

    // console.log(route.matched)
    // const items = route.matched
    //   .map(({ components }) => {
    //     const title = (components.default[property] || components.default.name)
    //     return title && title.toUpperCase()
    //   })
    //   .filter(t => t && t.trim())
    //   .reverse()
    // document.title = [...items, process.env.VUE_APP_TITLE].join(separator)
  })

  Object.defineProperties(Vue.prototype, {
    $title: {
      get: () => {
        return store.getters.title
      },
      set: value => {
        store.dispatch('changeTitle', value.toUpperCase())
      }
    },
    $breadcrumbs: {
      get: () => {
        return store.getters.navbar.breadcrumbs
      },
      set: value => {
        store.dispatch('changeBreadcrumbs', value)
      }
    }
  })
}
