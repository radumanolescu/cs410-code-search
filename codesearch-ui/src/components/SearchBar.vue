<template>
  <div class="ui center aligned basic segment">
    <sui-form>
      <sui-form-field>
        <sui-input
          placeholder="Input search term here.  Use $$...$$ for formula search."
          icon="search"
          v-on:keydown.prevent.enter="onSearch"
          :value="query"
          @input="updateQuery"
        />
        <sui-button-group
          class="latex-button-group"
          v-for="formula in predefinedFormulae"
          v-bind:key="formula"
        >
          <latex-helper-button :formula="formula" :handleFormulaClick="handleFormulaClick"/>
        </sui-button-group>
      </sui-form-field>
    </sui-form>
  </div>
</template>

<script>
import LatexHelperButton from "./LatexHelperButton.vue";
import { mapState } from "vuex";

export default {
  name: "search-bar",
  data: function() {
    return {
      predefinedFormulae: [
        "$$x^y$$",
        "$$\\sqrt[y]{x}$$",
        "$$\\binom{n}{k}$$",
        "$$\\frac{x}{y}$$",
        "$$\\int_{a}^{b}$$",
        "$$\\iint_V \\mu(u,v) \\,du\\,dv$$",
        "$$\\sum_{n=1}^{\\infty}$$",
        "$$\\alpha$$",
        "$$\\beta$$",
        "$$\\gamma$$",
      ]
    };
  },
  computed: {
    ...mapState({
      query: state => state.query
    })
  },
  components: {
    "latex-helper-button": LatexHelperButton
  },
  methods: {
    updateQuery(e) {
      this.$store.commit("setQuery", e);
    },
    onSearch: function(e) {
      this.$store.dispatch("search");
    },
    handleFormulaClick: function(formula) {
      let q = this.$store.state.query;
      q = `${q} ${formula}`
      this.updateQuery(q);
    }
  }
};
</script>

<style scoped>
.latex-button-group {
  margin: 2px;
  padding: 5px;
}
</style>
