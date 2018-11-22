<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <div class="ui container">
      <h1>Welcome to Code Search!</h1>
      <p>
        Search source code and formulae in one place!
      </p>
      <search-bar />
      <div class="ui divider"></div>
      <search-result />
    </div>
  </div>
</template>

<script>
import SearchBar from './components/SearchBar.vue'
import SearchResult from './components/SearchResult.vue'

export default {
  name: 'app',
  components: {
    SearchBar,
    SearchResult,
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
</style>
