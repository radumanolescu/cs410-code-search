<template>
<div class="ui center aligned basic segment">
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