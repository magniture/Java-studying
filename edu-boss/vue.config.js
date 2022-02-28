/**
 * Override convention configuration
 * https://cli.vuejs.org/config/
 */

module.exports = {
  publicPath: process.env.NODE_ENV === "production" ? "/edu-boss-fed/" : "/",
  indexPath: "template/index.html",
  assetsDir: "assets",
  lintOnSave: process.env.NODE_ENV !== "production",
  productionSourceMap: false,
  devServer: {
    proxy: {
      "^/boss": {
        target: "http://eduboss.lagou.com",
        pathRewrite: { "^/api": "/" },
        changeOrigin: true
      },
      "^/front": {
        target: "http://eduboss.lagou.com",
        pathRewrite: { "^/api": "/" },
        changeOrigin: true
      }
    },
    open: true,
    port: 8081
  }
};
