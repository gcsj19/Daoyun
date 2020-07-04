<template>
	<view class="content">
		<view class="header">
			<image src="../../static/shilu-login/logo.png"></image>
		</view>
		
		<view class="list">
			<view class="list-call">
				<image class="img" src="/static/shilu-login/1.png"></image>
				<input class="biaoti" v-model="id" type="number" maxlength="11" placeholder="手机号" />
			</view>
			<view class="list-call">
				<image class="img" src="/static/shilu-login/2.png"></image>
				<input class="biaoti" v-model="userPassword" type="text" maxlength="32" placeholder="登录密码" :password="!showPassword" />
				<image class="img" :src="showPassword?'/static/shilu-login/op.png':'/static/shilu-login/cl.png'" @tap="display"></image>
			</view>
			<view class="list-call">
				<image class="img" src="/static/shilu-login/3.png"></image>
				<input class="biaoti" v-model="code" maxlength="4" placeholder="验证码" />
				<view class="yzm" :class="{ yzms: second>0 }" @tap="getcode">{{yanzhengma}}</view>
				<image class="bigImage" :src="verifycode"> </image>
			</view>
			<view class="list-call">
				<image class="img" src="/static/shilu-login/4.png"></image>
				<input class="biaoti" v-model="role" type="text" maxlength="12" placeholder="老师/学生" />
			</view>
			
		</view>
		
		<view class="dlbutton" hover-class="dlbutton-hover" @tap="bindLogin">
			<text>注册</text>
		</view>
		
		<view class="xieyi">
			<image @tap="xieyitong" :src="xieyi==true?'/static/shilu-login/ty1.png':'/static/shilu-login/ty0.png'"></image>
			<text @tap="xieyitong"> 同意</text>
			<navigator url="blog?id=1" open-type="navigate">《软件用户协议》</navigator>
		</view>
	</view>
</template>

<script>
	var tha,js;
	export default {
		onLoad(){
			tha = this;						
		},
		onUnload(){
			clearInterval(js)
			this.second = 0;
		},
		onPullDownRefresh(){
									
			this.verifycode = ""
			setTimeout(()=>{
				uni.stopPullDownRefresh()		
			},2000)
			uni.request({
				url: 'http://123.56.164.172:8081/verifyCode',
				method: 'GET',
				success:(res)=>{
					
					this.verifycode = 'http://123.56.164.172:8081/verifyCode'
			    }				
				
			})
			
			
		},
		data() {
			return {
				id:'',
				userPassword:'',
				code:'',
				role:'',
				xieyi:true,
				showPassword:false,
				second:0,
				verifycode:'http://123.56.164.172:8081/verifyCode',
				iscodeAlive: false
				
			};
		},
		computed:{
			yanzhengma(){
				if(this.second==0){
					return '重新获取';
				}else{
					if(this.second<10){
						return '重新获取0'+this.second;
					}else{
						return '重新获取'+this.second;
					}
				}
			}
		},
		methods: {
			display() {
			    this.showPassword = !this.showPassword
			},
			xieyitong(){
				this.xieyi = !this.xieyi;
			},
			reload(){
				
				 this.verifycode = ""
				uni.request({
					url: 'http://123.56.164.172:8081/verifyCode',
					
					success:(res)=>{
						console.log(res)
					}
					
				});
				this.verifycode = "http://123.56.164.172:8081/verifyCode"

	
			    console.log("click")
			},
			getcode(){
				if(this.second>0){
					return;
				}

				this.verifycode = ""
				uni.request({
				    url: 'http://123.56.164.172:8081/verifyCode', //仅为示例，并非真实接口地址。
					method: 'GET',
				    success: (res) => {
						this.verifycode = "http://123.56.164.172:8081/verifyCode"
				    }
				});
											
				
			},
		    async bindLogin() {
				
				if (this.xieyi == false) {
				    uni.showToast({
				        icon: 'none',
				        title: '请先阅读《软件用户协议》'
				    });
				    return;
				}
				if (this.id.length !=11) {
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
				
				if(this.role == null || this.role!='老师' && this.role!='学生'){
					uni.showToast({
					    icon: 'none',
					    title: '角色不正确'
					});
					return;
					
				}													
				const isPermission =  await this.$myRequest({
					url: '/isVerified',
					data: {
						code : this.code
					}						
				})
				
				this.iscodeAlive = isPermission.data																
				if (this.iscodeAlive == false) {
					//console.log(this.iscodeAlive)
				    uni.showToast({
				        icon: 'none',
				        title: '验证码不正确'
				    });
				    return;
				
				}
															
				 uni.request({
				    url: 'http://123.56.164.172:8081/userSignUp',
				    data: {
						userId:this.id,
						userPassword:this.userPassword,
						userType:this.role
												
					},
					method: 'GET',
				    success: (res) => {
						if(res.data.code!=200){						
							if(res.data){
								setTimeout(()=>{
									uni.showToast({
										icon:"loading"
									})
									
								},2000)								
								setTimeout(()=>{
									uni.showToast({
										icon:'none',
										title:'注册成功'
										
									})									
								},3000)																
								setTimeout(()=>{
									uni.reLaunch({
										url: '../shilu-login/login',
										
									});
									
								},5000)							
							}
							else{
								uni.showToast({
									icon: 'none',
									title: '注册失败'
									
								});
							}
						}else{
							uni.showToast({title:res.data.msg});
							setTimeout(function(){
								uni.navigateBack();
							},1500) 
						}
						
						
				    }
				});
								
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
	.list-call .bigImage{
		width: 110upx;
		height: 45upx;
		
	}
	.yzm {
		color: #FF7D13;
		font-size: 24upx;
		line-height: 64upx;
		padding-left: 10upx;
		padding-right: 10upx;
		width:auto;
		height:64upx;
		border:1upx solid #FFA800;
		border-radius: 50upx;
	}
	.yzms {
		color: #999999 !important;
		border:1upx solid #999999;
	}
	.dlbutton {
		color: #FFFFFF;
		font-size: 34upx;
		width:470upx;
		height:100upx;
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
	.xieyi image{
		width: 40upx;
		height: 40upx;
	}
</style>
