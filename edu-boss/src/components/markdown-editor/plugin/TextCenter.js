import Editor from '@toast-ui/editor'

function renderTargetText (wrapperId, text) {
  const el = document.querySelector(`#${wrapperId}`)
  //! [promise.all.png](https://www.lgstatic.com/i/image/M00/1D/68/Ciqc1F7h7FyAW6OqAAMzPvP8ZeI951.png)
  var re = /(?:!\[(.*?)\]\((.*?)\))/g
  var reSingleUrl = /!\[.*?\]\((.*?)\)/
  var content = text
  if (re.test(text)) {
    const imgUrlList = text.match(re)
    const list = imgUrlList.map(item => {
      content = content.replace(item, '')
      const url = item.match(reSingleUrl)[1]
      return `<img src="${url}"/>`
    })
    if (content) {
      el.innerHTML = `<div style="text-align: center;flex-direction:column;display: flex;align-items: center;justify-content: center;">
                    <span>${content}</span>
                    ${list.join('')}
                    </div>`
    } else {
      el.innerHTML = `<div style="text-align: center;flex-direction:column;display: flex;align-items: center;justify-content: center;">
                    ${list.join('')}
                    </div>`
    }
    return
  }
  el.innerHTML = `<div style="text-align: center;flex-direction:column;display: flex;align-items: center;justify-content: center;">
                    <p>${text}</p>
                    </div>`
}
function TextCenterPlugin () {
  Editor.codeBlockManager.setReplacer('center', function (text) {
    // Indentify multiple code blocks
    const wrapperId = `yt${Math.random()
      .toString(36)
      .substr(2, 10)}`

    // Avoid sanitizing iframe tag
    setTimeout(renderTargetText.bind(null, wrapperId, text), 0)

    return `<div id="${wrapperId}"></div>`
  })
}

export default TextCenterPlugin
