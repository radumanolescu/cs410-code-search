<template>
  <span v-html="renderedText">
  </span>
</template>

<script>
export default {
  name: "matched-document",
  props: ["text", "matchedPositions"],
  computed: {
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
