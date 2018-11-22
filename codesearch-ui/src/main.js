import Vue from 'vue'
import SuiVue from 'semantic-ui-vue'
import Vuex from 'vuex'
import client from './CodeSearchClient'
import App from './App.vue'

Vue.config.productionTip = false
Vue.use(SuiVue)
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    query: "",
    searchResults: []
  },
  mutations: {
    setQuery (state, query) {
      state.query = query
    },
    setSearchResults (state, result) {
      state.searchResults = result
    }
  },
  actions: {
    async search ({ commit, state }) {
      const result = await client.search(state.query)
      commit('setSearchResults', { result })
    }
  }
})

new Vue({
  render: h => h(App),
  store,
}).$mount('#app')
