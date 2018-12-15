<template>
<div class="ui center aligned basic segment">
  <sui-form>
    <sui-form-field>
      <sui-input placeholder="Input search term here.  Use $$...$$ for formula search." icon="search" v-on:keydown.prevent.enter="onSearch" :value="query" @input="updateQuery" />
      <sui-button-group class="latex-button-group">
        <sui-button compact><mathjax formula="$$x^y$$">abc</mathjax></sui-button>
        <sui-button compact><mathjax formula="$$\sqrt{x}$$">abc</mathjax></sui-button>
      </sui-button-group>
    </sui-form-field>
  </sui-form>
</div>
</template>

<script>
import MathJax from './MathJax.vue'
import { mapState } from 'vuex'

export default {
  name: 'search-bar',
  computed: {
    ...mapState({
      query: state => state.query
    })
  },
  components: {
    'mathjax': MathJax
  },
  methods: {
    updateQuery (e) {
      this.$store.commit('setQuery', e)
    },
    onSearch: function(event) {
      this.$store.dispatch('search')
    }
  }
}
</script>

<style scoped>
.latex-button-group {
  margin: 2px;
  padding: 5px;
}
</style>
