<template>
	<view class="content">
		<view class="header">
			<image src="../../static/shilu-login/logo.png"></image>
		</view>
		
		<view class="list">
			<view class="list-call">
				<image class="img" src="/static/shilu-login/1.png"></image>
				<input class="biaoti" v-model="id" type="number" maxlength="11" placeholder="输入手机号" />
			</view>
			<view class="list-call">
				<image class="img" src="/static/shilu-login/2.png"></image>
				<input class="biaoti" v-model="userPassword" type="text" maxlength="32" placeholder="输入密码" password="true" />
			</view>
			
		</view>
		
		<view class="dlbutton" hover-class="dlbutton-hover" @tap="bindLogin()">
			<text>登录</text>
		</view>
		
		<view class="xieyi">
			<navigator url="forget" open-type="navigate">忘记密码</navigator>
			<text>|</text>
			<navigator url="reg" open-type="navigate">注册账户</navigator>
			
		</view>
	</view>
</template>

<script>
	var tha;
	import {	
	mapState,
	mapMutations
	} from 'vuex';
	export default {
		onLoad(){
			tha = this;
		},
		data() {
			return {
				id:'',
				userPassword:'',
				hasLogin: false,
				token:'',
				isFinish:false
			};
		},
		methods: {
			...mapMutations(['initUserInfo','getToken']),
				
		    bindLogin() {
				
				if (this.id.length != 11) {
				     uni.showToast({
				        icon: 'none',
				        title: '手机号不正确'
				    });
				    return;
				}
		        if (this.userPassword.length < 6) {
		            uni.showToast({
		                icon: 'none',
		                title: '密码不正确'
		            });
		            return;
		        }

				uni.request({
				    url: 'http://123.56.164.172:8081/userLogin',
				    data: {
						id:this.id,
						userPassword:this.userPassword
					},
					method: 'GET',
					//dataType:'json',
				    success: (res) => {																																													
						if(res.data){	
							uni.setStorage({
								key: 'userState',
								data:{											
									id:this.id,
									hasLogin:true,
									token:res.data
		
									
								}
							})							
							this.token = res.data
							this.hasLogin = true
							this.getToken(this.token)
							this.getUserInfo(this.token)
							this.getCourseInfo(this.token)	
							this.getCourseInfo2(this.token)
							uni.$emit('login', 1);	
							setTimeout(()=>{
								uni.showToast({
									icon:'loading'
								})								
							},200)
							setTimeout(()=>{								
								uni.reLaunch({
									url: '../main/main'
								})
							},2000)
																																																													
						}
						else{	
							
							uni.showToast({title:"用户名或者密码不正确",icon:'none'});
						}
					}
																														    
				});
								
		    },
			 getUserInfo(token){
				
				 uni.request({
					url:'http://123.56.164.172:8081/userInfo',
					data:{
						id:this.id,
						token:token
						
					},
					method:'GET',				
					success: (res) => {						
						const userInfo = res.data
						uni.setStorage({
							key:'userInfo',
							data:{
								id: res.data.phone,
								userName: res.data.userName,
								email: res.data.email,
								major: res.data.major,
								userType:res.data.userType
								
							}
						})
						
						 // this.initUserInfo(userInfo)																		
					}
					
				})
				
			},
			getCourseInfo(token){
				uni.request({
					url:'http://123.56.164.172:8081/courseInfo',
					data:{
						id:this.id,
						token:token
					},
					method:'GET',
					success: (res) => {
						
						uni.setStorage({
							key:'courseInfo',
							data:res.data
						})
												
					}
				})
								
			},
			getCourseInfo2(token){
				uni.request({
					url:'http://123.56.164.172:8081/courseInfo2',
					data:{
						id: this.id,
						token: token
						
					},
					method:'GET',
					success: (res) => {
						
						uni.setStorage({
							key:'courseInfo2',
							data:res.data
						})
						
					}
				})
				
				
			}
			
		}

	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		justify-content:center;
	}
	.header {
		width:161upx;
		height:161upx;
		background:rgba(63,205,235,1);
		box-shadow:0upx 12upx 13upx 0upx rgba(63,205,235,0.47);
		border-radius:50%;
		margin-top: 30upx;
		margin-left: auto;
		margin-right: auto;
	}
	.header image{
		width:161upx;
		height:161upx;
		border-radius:50%;
	}
	
	.list {
		display: flex;
		flex-direction: column;
		padding-top: 50upx;
		padding-left: 70upx;
		padding-right: 70upx;
	}
	.list-call{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 100upx;
		color: #333333;
		border-bottom: 1upx solid rgba(230,230,230,1);
	}
	.list-call .img{
		width: 40upx;
		height: 40upx;
	}
	.list-call .biaoti{
		flex: 1;
		text-align: left;
		font-size: 32upx;
		/* line-height: 100upx; */
		margin-left: 16upx;
	}

	.dlbutton {
		color: #FFFFFF;
		font-size: 34upx;
		width:470upx;
		height:100upx;
		/* background:linear-gradient(-90deg,rgba(63,205,235,1),rgba(188,226,158,1)); */
		background:linear-gradient(-90deg,rgba(63,205,235,1),rgba(188,226,158,1));
		box-shadow:0upx 0upx 13upx 0upx rgba(164,217,228,0.2);
		border-radius:50upx;
		line-height: 100upx;
		text-align: center;
		margin-left: auto;
		margin-right: auto;
		margin-top: 100upx;
	}
	.dlbutton-hover {
		background:linear-gradient(-90deg,rgba(63,205,235,0.9),rgba(188,226,158,0.9));
	}
	.xieyi{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 30upx;
		margin-top: 80upx;
		color: #FFA800;
		text-align: center;
		height: 40upx;
		line-height: 40upx;
	}
	.xieyi text{
		font-size: 24upx;
		margin-left: 15upx;
		margin-right: 15upx;
	}
</style>
