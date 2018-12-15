<template>
    <sui-button compact v-on:click.prevent="() => handleFormulaClick(formula)"><mathjax :formula="formula"></mathjax></sui-button>
</template>

<script>
import MathJax from './MathJax.vue'

export default {
    name: 'latex-helper-button',
    props: ['formula', 'handleFormulaClick'],
    components: {
        'mathjax': MathJax,
    },
}
</script>