<template>
  <div class="ui container">
    <h1>Welcome to Code Search!</h1>
    <p>
      Search source code and formulae in one place!
    </p>
    <div class="ui segment">
    <sui-form>
      <sui-form-field>
        <sui-input placeholder="Search..." icon="search" />
      </sui-form-field>
    </sui-form>
    </div>
    <div class="ui divider"></div>
  </div>
</template>

<script>
export default {
  name: 'Main'
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
