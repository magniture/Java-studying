<template>
  <div
    ref="tuiEditor"
    class="my-editor-tui"
    :class="{
      'CodeMirror-fullscreen': isFullScreen
    }"
  ></div>
</template>
<script>
import Editor from '@toast-ui/editor'

import '@toast-ui/editor/dist/toastui-editor.css'
import 'tui-editor/dist/tui-editor.css'
import 'codemirror/lib/codemirror.css'

import codeSyntaxHightlight from '@toast-ui/editor-plugin-code-syntax-highlight'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'

import 'tui-color-picker/dist/tui-color-picker.css'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'

import tableMergedCell from '@toast-ui/editor-plugin-table-merged-cell'

import 'tui-chart/dist/tui-chart.css'
import chart from '@toast-ui/editor-plugin-chart'

import TextCenterPlugin from './plugin/TextCenter'
// import AlignCenterPlugin from './plugin/AlignCenter'

import editorEvents from './editorEvents'
import valueUpdateMethod from './valueUpdateMethod'
import { axios } from '../../utils'

const API_DATA_ROOT = process.env === 'development' ? '/boss' : '/boss'

const fileImgUpload = (url, form) => {
  return new Promise((resolve, reject) => {
    axios({
      url: url,
      type: 'post',
      data: form,
      processData: false,
      contentType: false,
      success: function (res) {
        resolve(res && res.content)
      }
    })
  })
}

const uploadMap = {
  mdUploadFile: `${API_DATA_ROOT}/kaiwu/uploadImageAddWatermark.json`,
  uploadFile: `${API_DATA_ROOT}/course/upload`
}

export default {
  name: 'TuiEditor',
  props: {
    uploadType: {
      type: String,
      default: 'mdUploadFile'
    },
    previewStyle: {
      type: String
    },
    height: {
      type: String
    },
    value: {
      type: String
    },
    mode: {
      type: String
    },
    options: {
      type: Object
    },
    html: {
      type: String
    },
    isMarkDown: {
      type: Boolean,
      default: false
    },
    visible: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      editor: null,
      isFullScreen: false
    }
  },
  computed: {
    editorOptions () {
      const options = Object.assign({}, this.options)
      options.initialValue = this.value
      options.initialEditType = this.mode
      options.height = this.height
      options.previewStyle = this.previewStyle

      return options
    }
  },
  watch: {
    previewStyle (newValue) {
      this.editor.changePreviewStyle(newValue)
    },
    value (newValue, preValue) {
      if (newValue !== preValue && newValue !== this.editor.getHtml()) {
        if (this.isMarkDown) {
          this.editor.setMarkdown(newValue)
          return
        }
        this.editor.setHtml(newValue)
      }
    },
    height (newValue) {
      this.editor.height(newValue)
    },
    mode (newValue) {
      this.editor.changeMode(newValue)
    },
    html (newValue) {
      this.editor.setHtml(newValue)
      this.$emit('input', this.editor.getHtml())
    },
    visible (newValue) {
      if (newValue) {
        this.editor.show()
      } else {
        this.editor.hide()
      }
    }
  },
  mounted () {
    const eventOption = {}
    const me = this

    window.onkeydown = function (event) {
      if (event.keyCode === 27) {
        me.isFullScreen = false
      }
    }

    editorEvents.forEach(event => {
      eventOption[event] = (...args) => {
        this.$emit(event, ...args)
      }
    })

    const options = Object.assign(this.editorOptions, {
      el: this.$refs.tuiEditor,
      previewStyle: 'vertical',
      initialEditType: 'markdown',
      events: eventOption,
      hideModeSwitch: true,
      toolbarItems: [
        'heading',
        'bold',
        'italic',
        'strike',
        'divider',
        'hr',
        'quote',
        'divider',
        'ul',
        'ol',
        'task',
        'indent',
        'outdent',
        'divider',
        'table',
        'image',
        'link',
        'divider',
        'code',
        'codeblock'
      ],
      hooks: {
        addImageBlobHook: function (file, callback) {
          var form = new FormData()
          form.append('file', file)
          console.log('me.uploadType :>> ', me.uploadType)
          fileImgUpload(uploadMap[me.uploadType], form).then((data) => {
            callback(data, file.name)
          })
        }
      },
      plugins: [
        TextCenterPlugin,
        // AlignCenterPlugin,
        [codeSyntaxHightlight, { hljs }],
        colorSyntax,
        tableMergedCell,
        chart
      ]
    })

    this.editor = new Editor(options)
    this.editor.eventManager.addEventType('clickCustomButton')
    this.editor.eventManager.listen('clickCustomButton', function () {
      me.isFullScreen = !me.isFullScreen
    })
    const toolbar = this.editor.getUI().getToolbar()
    function createLastButton () {
      const button = document.createElement('span')

      button.className = 'editor-full-screen'
      button.innerHTML = ''

      return button
    }

    toolbar.insertItem(30, {
      type: 'button',
      options: {
        el: createLastButton(),
        event: 'clickCustomButton',
        tooltip: '全屏展示',
        text: ' ',
        style: ''
      }
    })
    if (this.$listeners.input) {
      this.editor.on('change', () => {
        this.$emit('input', this.editor.getHtml())
      })
    }
  },
  destroyed () {
    editorEvents.forEach(event => {
      this.editor.off(event)
    })
    this.editor.remove()
  },
  methods: {
    invoke (methodName, ...args) {
      let result = null
      if (this.editor[methodName]) {
        result = this.editor[methodName](...args)
        if (valueUpdateMethod.indexOf(methodName) > -1) {
          this.$emit('input', this.editor.getHtml())
        }
      }

      return result
    }
  }
}
</script>
<style>
.tui-md-code,
.tui-md-code-block {
  color: #a50;
}
.CodeMirror pre.CodeMirror-line,
.CodeMirror pre.CodeMirror-line-like {
  font-family: 'Microsoft YaHei';
}
.tui-md-code-block.CodeMirror-linebackground {
  left: 0;
  right: 10px;
}
.tui-editor-defaultUI-toolbar {
  padding-left: 5px;
  font-size: 18px;
}
.editor-full-screen {
  vertical-align: middle;
  box-sizing: border-box;
  display: inline-block;
  border: 0.1em solid currentColor;
  width: 1em;
  height: 1em;
  position: absolute;
  top: 5px;
  right: 10px;
  cursor: pointer;
}
.editor-full-screen:before,
.editor-full-screen:after {
  content: '';
  background: #fff;
  position: absolute;
}
.editor-full-screen:before {
  width: 0.333em;
  height: 1em;
  left: 0.233em;
  top: -0.1em;
}
.editor-full-screen:after {
  width: 1em;
  height: 0.333em;
  top: 0.233em;
  left: -0.1em;
}
.te-preview {
  background: white;
}
.CodeMirror-fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  height: auto !important;
  z-index: 10000;
}
.tui-md-heading {
  color: #a0a;
}
.tui-md-link.tui-md-link-desc.tui-md-marked-text,
.tui-md-list-item-odd.tui-md-list-item-bullet {
  color: #00c;
}
.tui-md-link-url {
  color: #a11 !important;
}
.tui-md-block-quote {
  color: #090;
}
.tui-md-list-item {
  color: #05a;
}
</style>
