<template>
<div class="ui">
  Backend Status:
  <sui-popup>
    <sui-icon name="circle" v-bind:color="color" slot="trigger" />
    {{ statusText }}
  </sui-popup>
</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'BackendStatus',
  computed: {
    ...mapState({
      backendStatus: state => state.backendStatus
    }),
    color: function() {
      console.log(this.backendStatus)
      if (this.backendStatus.status === 'READY') {
        return 'green'
      } else {
        return 'red'
      }
    },
    statusText: function() {
      if (this.backendStatus.status === 'READY') {
        return 'Backend is healthy'
      } else {
        return this.backendStatus.reason
      }
    }
  },
  methods: {
  }
}
</script>