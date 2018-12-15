<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <div class="ui container">
      <p>Search source code and formulae in one place!<backend-status /></p>
      <search-bar />
      <sui-divider />
      <search-result />
    </div>
  </div>
</template>

<script>
import MathJax from './components/MathJax.vue'
import SearchBar from './components/SearchBar.vue'
import SearchResult from './components/SearchResult.vue'
import BackendStatus from './components/BackendStatus.vue'

export default {
  name: 'app',
  components: {
    MathJax,
    SearchBar,
    SearchResult,
    BackendStatus,
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

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