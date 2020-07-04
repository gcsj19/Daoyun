import Vue from 'vue'
import App from './App'
import store from './store'
import {myRequest} from './util/api.js'
import action from "@/components/shufei-action/action.vue"

Vue.config.productionTip = false
Vue.prototype.$myRequest = myRequest
Vue.prototype.$store = store
Vue.component('popMenu',action)
App.mpType = 'app'

const app = new Vue({
	store,
    ...App
})
app.$mount()
