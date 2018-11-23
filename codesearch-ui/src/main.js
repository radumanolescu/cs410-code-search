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
    backendStatus: {},
    searchResults: [],
    isSearchPending: false,
  },
  mutations: {
    setQuery (state, query) {
      state.query = query
    },
    setSearchResults (state, { result }) {
      state.searchResults = result
    },
    setBackendStatus (state, { backendStatus }) {
      state.backendStatus = backendStatus
    },
    setSearchPending (state, { isPending }) {
      state.isSearchPending = isPending
    },
  },
  actions: {
    async search ({ commit, state }) {
      commit('setSearchPending', { isPending: true })
      const result = await client.search(state.query)
      commit('setSearchResults', { result })
      commit('setSearchPending', { isPending: false })
    },

    async ping({ commit }) {
      const backendStatus = await client.ping()
      commit('setBackendStatus', { backendStatus })
    }
  }
})

new Vue({
  render: h => h(App),
  store,
  created: function() {
    this.pingBackend();
    this.timer = setInterval(this.pingBackend, 10000)
  },
  methods: {
    pingBackend: async function() {
      this.$store.dispatch('ping')
    }
  }
}).$mount('#app')
