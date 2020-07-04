 <template>
	<view>		
		<!--主体-->
		<view class="meMain">
			<image src="../../static/icon/meBg.png"></image>
			<view class="mePosition">
				<view class="meMainBox">				
					<!--头部-->
					<view class="meHead">
						<view class="meHeadAvatar"><image :src="avatarUrl" mode="aspectFill" @click="BindGetUserInfo()"></image></view>
						<view class="meHeadName"><text>{{nickName}}</text></view>
					</view>
					<!--头部-->
					
					<!--模板-->
					<view class="meOverBg">
						<view class="meVisitor">
							<view class="meVisitorLt">
								<view class="meVisitorTxt_02">{{courseNum}}</view>
								<view class="meVisitorTxt_01">课程</view>
							</view>
							<view class="meVisitorLt">
								<view class="meVisitorTxt_02">0</view>
								<view class="meVisitorTxt_01">积分</view>
							</view>
						</view>
					</view>
					<!--模板-->
					
					<!--订单-->
					<view class="meOverBg">
						<view class="meVisitorTitle">资料</view>
						<view class="meVisitor">
							<view class="meOrderLt">
								<view class="meOrderTxt_01">
									<!-- <image src="../../static/icon/meIcon_01.png"></image> -->
									<uni-icons type="contact" size="25" color="#3B7ED5"></uni-icons>
									</view>
								<view class="meOrderTxt_02">我的勋章</view>
							</view>
							<view class="meOrderLt" @click="toMySpace">
								<view class="meOrderTxt_01">
									<!-- <image src="../../static/icon/meIcon_01.png"></image> -->
									<uni-icons type="pengyouquan" size="25" color="#3B7ED5"></uni-icons>
									</view>
								<view class="meOrderTxt_02">我的空间</view>
							</view>
							<view class="meOrderLt">
								<view class="meOrderTxt_01">
									<!-- <image src="../../static/icon/soucang.png"></image> -->
									<uni-icons type="starhalf" size="25" color="#3B7ED5"></uni-icons>
									</view>
								<view class="meOrderTxt_02">我的收藏</view>
							</view>
							<view class="meOrderLt">
								<view class="meOrderTxt_01">
									<!-- <image src="../../static/icon/meIcon_01.png"></image> -->
									<uni-icons type="heart-filled" size="25" color="#3B7ED5"></uni-icons>
									</view>
								<view class="meOrderTxt_02">心意卡片</view>
							</view>
						</view>
					</view>
					<!--订单-->
					
					<!--其他-->
					<view class="meOverBg">
						<view class="businessList">
							<view class="businessListTxt">手机号</view>
							<view class="businessListYou">{{phone}}</view>
						</view>

					</view>
					<!--其他-->
					
					<!--其他-->
					<view class="meOverBg">
						<view class="businessList" @click="toAboutUs">
							<view class="businessListTxt">
								<!-- <image src="../../static/icon/meIcon_01.png"></image> -->
								使用说明
								</view>
							<view class="businessListYou" >
								<image src="../../static/icon/you.png"></image>
								</view>
						</view>
						<view class="businessList">
							<view class="businessListTxt">
								<!-- <image src="../../static/icon/meIcon_01.png"></image> -->
								<!-- <uni-icons type="chat" size="20"></uni-icons> -->
								<text>联系客服</text>						
								</view>
								
							<view class="businessListYou">
								<image src="../../static/icon/you.png"></image>
								</view>
						</view>
					</view>
					<!--其他-->
					
				</view>
			</view>
		</view>
		<!--主体-->
		
	</view>
</template>

<script>
	
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default{
		data(){
			return{
				avatarUrl:'https://avatar.bbs.miui.com/images/noavatar_small.gif',//头像
				nickName:'点击登录',
				phone:'',
				email:'',
				major:'',
				loginState:false,
				courseNum:0,
				userType:'',
				course:[]
			}
		},
		onLoad() {
					
			try {	
				const state = uni.getStorageSync('userState')			
				if(state.hasLogin){
					this.loginState = state.hasLogin
					this.phone = state.id					
					const value = uni.getStorageSync('userInfo')
					this.userType = value.userType
					var str = ''
					if(this.userType == "老师"){
						str = 'courseInfo'
					}
					if(this.userType == "学生"){
						
						str = 'courseInfo2'
					}
					const course = uni.getStorageSync(str);
					this.course = course
					this.courseNum = course.length
					
					
					
					if(value.userName == null){
						this.nickName = "未设置"
					}				
					else{
	
						this.nickName = value.userName
					}
					
					
				}
			    				
			} catch (e) {
			    // error
			}
			
		
		},
		onShow() {
			
			//this.token = uni.getStorageSync('token');
			//console.log(this.nickName)
			
		},		
		computed: {
			...mapState(['forcedLogin'])
		},
		methods:{
			
			//点击登录
			BindGetUserInfo:function(){
				if ( this.loginState) {
					uni.navigateTo({
						url:'../user/info/info'
					})
										
				} else{

					uni.navigateTo({
						url:'../shilu-login/login'
					});
					
					
				}
			},	
			toDetail(){
				if(this.loginState){					
					uni.navigateTo({
						url:'../user/info/info'
					})
				}
				

			},
			toMySpace(){
				
				console.log("我的空间")

			},
			getUserInfo(){
				
				 uni.request({
					url:'http://localhost:8081/user',
					data:{
						id:'1222222'
					},

					method:'GET',
					
					success: (res) => {

						console.log(res.data)
																		
					}
					
				})
				
			
			},
			toAboutUs(){
				
				uni.navigateTo({
					url:'../user/about-us/about-us'
				})
			}
					
			
			
		}
	}
</script>

<style>
	page{ background: #F5F5F5; }
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
	.meMain{ width: 100%;position: relative; }
	.meMain>image{ width: 100%;height: 220upx;display: block; }
	.meMainBox{ width: 92%;margin: 0 auto; }
	.mePosition{ position: absolute;top: 0;left: 0;width: 100%; }
	.meHead{ overflow: hidden;position: relative; }
	.meHeadAvatar{ float: left;width: 19%;margin-top: 10upx; }
	.meHeadAvatar image{ width: 110upx;height: 110upx;display: block;border-radius: 50%; }
	.meHeadName{ float: left;width: 81%;line-height: 120upx;color: #FFFFFF;font-size: 28upx;overflow:hidden;text-overflow:ellipsis;white-space:nowrap; }
	
	.meOverBg{ background: #FFFFFF;overflow: hidden;border-radius: 12upx;margin-top: 30upx; }
	.meVisitor{ display: flex;flex-direction: row; }
	.meVisitorLt{ width: 50%;text-align: center;margin: 30upx 0; }
	.meVisitorLt:nth-child(1){ border-left: 1px #F5F5F5 solid;border-right: 1px #F5F5F5 solid; }
	.meVisitorTxt_01{ font-size: 28upx;color: #666666; }
	.meVisitorTxt_01 image{ width: 35upx;height: 35upx;vertical-align: middle;margin: 0 10upx 4upx 0; }
	.meVisitorTxt_02{ font-size: 30upx;color: #3B7ED5; }
	
	.meVisitorTitle{ font-size: 30upx;color: #333333;border-bottom: 1px #F5F5F5 solid;padding: 20upx 30upx; }
	.meOrderLt{ width: 25%;text-align: center;padding: 30upx 0;transition: all 0.4s; }
	.meOrderLt:active{ background: #E2E2E2; }
	.meOrderTxt_01{  }
	.meOrderTxt_01 image{ width: 40upx;height: 40upx;display: block;margin: 0 auto 6upx; }
	.meOrderTxt_02{ font-size: 24upx;color: #666666; }
	
	.businessList{ overflow: hidden;padding: 30upx;border-bottom: 1px #F5F5F5 solid;transition: all 0.4s; }
	.businessList:active{ background: #E2E2E2; }
	.businessList:last-child{ border-bottom: none; }
	.businessListTxt{ float: left;font-size: 28upx;color: #333333; }
	.businessListTxt image{ width: 35upx;height: 35upx;vertical-align: middle;margin: 0 10upx 4upx 0; }
	.businessListYou{ float: right;font-size: 28upx;color: #333333; }
	.businessListYou image{ width: 20upx;height: 20upx;vertical-align: middle;margin: 0 0 4upx 10upx; }
	
	
</style>
