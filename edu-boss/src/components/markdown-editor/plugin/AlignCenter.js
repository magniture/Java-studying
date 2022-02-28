// const colorSyntaxRx = /\{color:(.+?)}(.*?)\{color}/g
// const colorHtmlRx = /<span (?:class="colour" )?style="text-align:center;color:(.+?)"(?: class="colour")?>(.*?)/g
// const colorHtmlCompleteRx = /<span (?:class="colour" )?style="text-align:center;color:(.+?)"(?: class="colour")?>(.*?)<\/span>/g
// const decimalColorRx = /rgb\((\d+)[, ]+(\d+)[, ]+(\d+)\)/g

const RESET_COLOR = '#181818'

// const lastScrollTop = 0

/**
 * Set style color
 * @param {SquireExt} sq - squire ext instance
 * @param {string} color - color sting value
 * @ignore
 */
export function setStyleColor (sq, color) {
  if (!sq.hasFormat('PRE')) {
    if (color === RESET_COLOR) {
      sq.changeFormat(null, {
        class: 'colour',
        tag: 'span'
      })
    } else {
      sq.setTextColour(color)
    }
  }
}

/**
 * Get scrollTop of squire
 * @param {SquireExt} sq - squire ext instance
 * @ignore
 */
export function getScrollTopForReFocus (sq) {
  return sq.getRoot().parentNode.scrollTop
}

/**
 * Create button element for applying color
 * @param {string} text - button's text
 * @returns {HTMLElement} button element
 * @ignore
 */
export function createApplyButton (text) {
  const button = document.createElement('button')

  button.className = 'te-apply-button'
  button.innerHTML = text

  return button
}

/**
 * Initialize UI
 * @param {object} editor - Editor instance
 * @param {Array.<string>} preset - Preset for color palette
 * @ignore
 */
export function initUI (editor, preset) {
  const name = 'colorSyntax'
  const className = 'tui-color'
  const toolbar = editor.getUI().getToolbar()

  editor.eventManager.addEventType('alignClicked')

  toolbar.insertItem(3, {
    type: 'button',
    options: {
      name,
      className,
      event: 'alignClicked'
    }
  })

  editor.eventManager.listen('alignClicked', () => {
    setTimeout(() => {
      const content = editor.getHtml()
      const reContent = content.replace(
        /<span style="color:\s?#000000[^>]*>(.*)<\/span>/g,
        '$1'
      )
      editor.setMarkdown(reContent, true)
    }, 100)
  })
}

/**
 * Make custom color syntax
 * @param {string} text - Text content
 * @param {string} color - Color value
 * @returns {object} - wrapped text and range(from, to)
 * @ignore
 */
export function makeCustomColorSyntaxAndTextRange (text, color) {
  return wrapTextAndGetRange(`{color:${color}}`, text, '{color}')
}

/**
 * Make HTML color syntax by given text content and color value
 * @param {string} text Text - content
 * @param {string} color Color - value
 * @returns {object} - wrapped text and range(from, to)
 * @ignore
 */
export function makeHTMLColorSyntaxAndTextRange (text, color) {
  return wrapTextAndGetRange(
    `<span style="text-align:center;color:${color}">`,
    text,
    '</span>'
  )
}

/**
 * Wrap text with pre & post and return with text range
 * @param {string} pre - text pre
 * @param {string} text - text
 * @param {string} post - text post
 * @returns {object} - wrapped text and range(from, to)
 * @ignore
 */
export function wrapTextAndGetRange (pre, text, post) {
  return {
    result: `${pre}${text}${post}`,
    from: pre.length,
    to: pre.length + text.length
  }
}

export default function colorSyntaxPlugin (editor, options = {}) {
  const { preset } = options
  initUI(editor, preset)
}
