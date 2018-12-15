<template>
<div class="ui center aligned basic segment">
  <sui-form>
    <sui-form-field>
      <sui-input placeholder="Input search term here.  Use $$...$$ for formula search." icon="search" v-on:keydown.prevent.enter="onSearch" :value="query" @input="updateQuery" />
      <sui-button-group class="latex-button-group">
        <latex-helper-button
          formula="$$x^y$$"
          :handleFormulaClick="() => handleFormulaClick('$$x^y$$')" />
        <latex-helper-button formula="$$\sqrt{x}$$" :handleFormulaClick="handleFormulaClick" />
      </sui-button-group>
    </sui-form-field>
  </sui-form>
</div>
</template>

<script>
import LatexHelperButton from './LatexHelperButton.vue'
import { mapState } from 'vuex'

export default {
  name: 'search-bar',
  computed: {
    ...mapState({
      query: state => state.query
    })
  },
  components: {
    'latex-helper-button': LatexHelperButton
  },
  methods: {
    updateQuery (e) {
      this.$store.commit('setQuery', e)
    },
    onSearch: function(e) {
      this.$store.dispatch('search')
    },
    handleFormulaClick: function(x) {
      console.log(x);
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
