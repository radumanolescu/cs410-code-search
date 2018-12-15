<template>
  <div class="ui container">
  <div v-if="isPending" class="ui segment">
    <div class="ui active dimmer inverted">
      <div class="ui large text loader">Loading</div>
    </div>
  </div>
  <sui-table celled padded v-if="searchResults && searchResults.results && searchResults.results.length > 0">
    <sui-table-header>
      <sui-table-row>
        <sui-table-header-cell>Relevance</sui-table-header-cell>
        <sui-table-header-cell>Document</sui-table-header-cell>
      </sui-table-row>
    </sui-table-header>

    <sui-table-body>
      <sui-table-row v-for="result in searchResults.results" :key="result.id">
        <sui-table-cell>
          <sui-rating icon="star" v-bind:rating="result.rating" :max-rating="5" />
        </sui-table-cell>
        <sui-table-cell>
          <matched-document v-bind:text="result.content" v-bind:matchedPositions="result.matchedPositions" />
        </sui-table-cell>
      </sui-table-row>
    </sui-table-body>
  </sui-table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import MatchedDocument from './MatchedDocument.vue'

export default {
  name: "search-result",
  components: { MatchedDocument },
  computed: {
    ...mapState({
      searchResults: state => state.searchResults,
      isPending: state => state.isSearchPending
    })
  },
};
</script>
