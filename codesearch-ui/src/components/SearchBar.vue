<template>
<div class="ui segment">
  <sui-form>
    <sui-form-field>
      <sui-input placeholder="Search..." icon="search" v-on:keydown.prevent.enter="onSearch" :value="query" @input="updateQuery" />
    </sui-form-field>
  </sui-form>
</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'search-bar',
  computed: {
    ...mapState({
      query: state => state.query
    })
  },
  methods: {
    updateQuery (e) {
      console.log(e)
      this.$store.commit('setQuery', e)
    },
    onSearch: function(event) {
      this.$store.dispatch('search')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
