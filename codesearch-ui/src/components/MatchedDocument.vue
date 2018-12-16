<template>
  <div>
    <span v-for="segment in textSegments" v-bind:key="segment.id">
      <span v-if="segment.type === 'REGULAR'">{{ segment.text }}</span>
      <span v-if="segment.type === 'MATH'"><mathjax :formula="segment.text"></mathjax></span>
    </span>
  </div>
</template>

<script>
import MathJax from './MathJax.vue'

export default {
  name: "matched-document",
  props: ["text", "matchedPositions"],
  components: {
    'mathjax': MathJax
  },
  computed: {
    textSegments: function() {
      const latexRe = /\$\$(.+?)\$\$|\$(.+?)\$/
      const segments = []
      let remainingText = new String(this.text)
      let id = 0;
      while (remainingText.length > 0) {
        const match = latexRe.exec(remainingText)
        if (match) {
          console.log(match)
          if (match.index >= 0) {
            segments.push({
              id,
              text: remainingText.substring(0, match.index),
              type: 'REGULAR'
            })
            id++
          }
          segments.push({
            id,
            text: match[0],
            type: 'MATH'
          })
          id++
          remainingText = remainingText.substring(match.index + match[0].length)
        } else {
          segments.push({
            id,
            text: remainingText,
            type: 'REGULAR'
          })
          id++
          remainingText = ""
        }
      }
      return segments
    },
    renderedText: function() {
      const result = [];
      let matchedPositions = this.matchedPositions
      console.log(matchedPositions)
      let matched = [];
      for (let i = 0; i < this.text.length; i++) {
        if (matchedPositions.length === 0) {
          result.push(this.text[i])
          continue
        }
        const [start, end] = matchedPositions[0]
        if (i >= start && i <= end) {
          matched.push(this.text[i])
          continue
        }
        if (i > end && matched.length > 0) {
          result.push('<span class="highlighted">')
          result.push(matched.join(""))
          result.push('</span>')
          console.log(result)
          matched = []
          matchedPositions = matchedPositions.slice(1)
          result.push(this.text[i])
        } else {
          result.push(this.text[i])
        }
      }
      return result.join("")
    }
  }
};
</script>
<style>
.highlighted {
  background-color: gold
}
</style>
