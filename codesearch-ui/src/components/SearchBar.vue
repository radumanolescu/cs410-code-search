<template>
  <div class="ui center aligned basic segment">
    <sui-form>
      <sui-form-field>
        <div class="ui action input">
          <sui-input
            placeholder="Input search term here.  Use $$...$$ for formula search."
            v-on:keydown.prevent.enter="onSearch"
            :value="query"
            @input="updateQuery" />
          <sui-button icon="superscript" color="green" v-on:click.prevent="onSearch">Formula</sui-button>
          <sui-button icon="code" v-on:click.prevent="onCodeSearch">Code</sui-button>
        </div>
        Sample Formulae
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
        "$$\\sum_{n=1}^{\\infty}$$",
        "$$e^{i \\pi}+1=0$$",
        "$$\\vec p_1\\cdot \\vec p_2 = |\\vec p_1| \\cdot |\\vec p_2| \\cdot \\cos \\theta$$",
        "$$\\frac{1}{2}\\pi R^2$$",
        "$$\\frac{1}{2}L^2(\\alpha-\\sin\\alpha)+\\frac{1}{2}R^2(\\beta-\\sin\\beta)=R^2(\\frac{1}{2}(L/R)^2((\\pi-2\\theta)-\\sin(\\pi-2\\theta))+\\frac{1}{2}(4\\theta-\\sin(4\\theta)))$$",
        "$$\\pi+\\alpha\\cos\\alpha-\\sin\\alpha$$",
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
    onCodeSearch: function(e) {
      this.$store.dispatch("codesearch");
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
