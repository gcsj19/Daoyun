<template>
  <view>
    <!--<cmd-nav-bar back title="信息设置"></cmd-nav-bar>-->
   <!-- <cmd-page-body type="top"> -->
      <cmd-transition name="fade-up">
        <view>
          <cmd-cel-item title="头像" slot-right arrow>
            <cmd-avatar src="https://avatar.bbs.miui.com/images/noavatar_small.gif"></cmd-avatar>
          </cmd-cel-item>
          <cmd-cel-item title="积分" :addon="score" arrow></cmd-cel-item>
          <cmd-cel-item title="昵称" :addon="nickName" arrow vi-if="userName"></cmd-cel-item>
          <cmd-cel-item title="联系方式" :addon="phone" arrow></cmd-cel-item>
          <cmd-cel-item title="Email" :addon="email" arrow></cmd-cel-item>
		  <cmd-cel-item title="专业" :addon="major" arrow></cmd-cel-item>
		  <cmd-cel-item title="角色" :addon="role" arrow></cmd-cel-item>
          <cmd-cel-item title="修改密码" @click="fnClick('modify')" arrow></cmd-cel-item>
          <button class="btn-logout" @tap="bindLogout">退出登录</button>
        </view>
      </cmd-transition>
    <!-- </cmd-page-body> -->
  </view>
</template>

<script>
  import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
  import cmdPageBody from "@/components/cmd-page-body/cmd-page-body.vue"
  import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
  import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"
  import cmdAvatar from "@/components/cmd-avatar/cmd-avatar.vue"
	import {
		mapState,
		mapMutations
	} from 'vuex'
  export default {
	// computed: mapState(['forcedLogin', 'hasLogin', 'userName','account']),
    components: {
      cmdNavBar,
      cmdPageBody,
      cmdTransition,
      cmdCelItem,
      cmdAvatar
    },

    data() {
      return {
		  image:'',
		  score:'0',
		  nickName: '未设置',
		  phone: '',
		  email:'',
		  major:'',
		  role:'',
		  token:''
		  	  
	  };
    },

    mounted() {},
	onLoad(){
		try {
		    const value = uni.getStorageSync('userInfo');				
			if(value.userName == null){
				this.nickName = "未设置"
			}				
			else{
				this.nickName = value.userName
			}				
			this.phone = value.id
			this.email = value.email==null? "未设置":value.email
			this.major = value.major
			this.role = value.userType
			const state = uni.getStorageSync('userState');
			this.loginState = state.hasLogin
			this.token = state.token
			
		} catch (e) {
		    // error
		}
			
	},
    
    methods:{
      /**
       * 点击触发
       * @param {Object} type 跳转页面名或者类型方式
       */
	  ...mapMutations(['logout']),
	  
      fnClick(type){
        if(type == 'modify'){
			console.log("修改密码");
          uni.navigateTo({
            url:'/pages/user/modify/modify'
          })
        }
      },
	  bindLogout() {
	  	
		const value = {
			
			id: this.phone,
			hasLogin: false,
			token: this.token
			
		}
		
		try {
		    uni.setStorageSync('userState', value)
			uni.removeStorageSync('userInfo');
			uni.removeStorageSync('courseInfo')
			uni.removeStorageSync('courseInfo2')
			uni.removeStorageSync('courseMemberInfo')
			
		} catch (e) {
		    // error
		}

	  	if (this.forcedLogin) {
	  		uni.reLaunch({
	  			url: '../login/login',
	  		});
	  	}
		uni.reLaunch({
			url: '../../me/me'
			
		})


	  }
	  
    }
  }
</script>

<style>
  .btn-logout {
    margin-top: 100upx;
    width: 80%;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: #3B7ED5;
  }

  .btn-logout-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }
</style>
