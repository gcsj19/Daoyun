import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		/**
		 ** 是否需要强制登录
		 */
		forcedLogin: false,
		hasLogin: false,
		id:"",
		userName: "",
		email:"",
		major:"",
		token:""
	},
	mutations: {
		login(state, account) {
			state.id = account || '新用户';
			state.hasLogin = true;
		},
		logout(state) {
			state.account = "";
			state.hasLogin = false;
		},
		initUserInfo(state,userInfo){
			state.id = userInfo.id;
			state.userName = userInfo.userName;
			state.email = userInfo.email;	
			state.major = userInfo.major;

				
		},
		getToken(state,token){
			state.token = token;
			
		}
		
	}
})

export default store
