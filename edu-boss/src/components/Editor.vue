<template>
  <textarea ref="editor"></textarea>
</template>

<script>
import SimpleMDE from 'simplemde'

export default {
  name: 'Editor',
  props: {
    value: String
  },
  mounted () {
    this.simplemde = new SimpleMDE({ element: this.$refs.editor, spellChecker: false })
    this.simplemde.codemirror.on('change', this.handleInput)
    this.value && this.simplemde.value(this.value)
  },
  destroyed () {
    this.simplemde = null
  },
  methods: {
    handleInput () {
      this.$emit('input', this.simplemde.value())
    }
  },
  watch: {
    value (val) {
      val !== this.simplemde.value() && this.simplemde.value(val)
    }
  }
}
</script>

<style src="simplemde/dist/simplemde.min.css"/>
