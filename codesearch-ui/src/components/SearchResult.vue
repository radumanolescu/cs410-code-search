<template>
  <div class="ui container">
  <sui-table celled padded v-if="searchResults.length > 0">
    <sui-table-header>
      <sui-table-row>
        <sui-table-header-cell>Relevance</sui-table-header-cell>
        <sui-table-header-cell>Document</sui-table-header-cell>
      </sui-table-row>
    </sui-table-header>

    <sui-table-body>
      <sui-table-row v-for="result in searchResults" :key="result.id">
        <sui-table-cell>
          <sui-rating icon="star" v-bind:rating="result.rating" :max-rating="5" />
        </sui-table-cell>
        <sui-table-cell>
          {{ result.content }}
        </sui-table-cell>
      </sui-table-row>
    </sui-table-body>
  </sui-table>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: "search-result",
  computed: {
    ...mapState({
      searchResults: state => state.searchResults
    })
  },
  methods: {}
};
</script>
