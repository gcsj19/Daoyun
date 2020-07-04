<template>

	<view class="content">
		
		<view class="float">
			<QSNavbar :navbarItems="navbarItems" fixed="false" statusBar="false" diffMenuButtonInfo="false" @click="click"></QSNavbar>
		     <action class="float" ref="action" @itemClick="itemClick" @titleClick="titleClick" @cancelClick="cancelClick"></action>
		</view>
		
		<view v-if="hasLogin" class="hello">
			
			<view v-if="tabbarIndex==0">
			<view v-if="userType=='老师'">
			<uni-list v-for="(item,id) in course" :key = "item.id">
			    <uni-list-item class="course" :title = "item.name" :id="item.id" :note="userName" thumb="/static/course.png" @click="toCourseInfo(id)">
				</uni-list-item>
			</uni-list>
			 </view>
			<view v-if="userType=='学生'">

			</view>														 
			</view>
			<view v-if="tabbarIndex==1">
				<view v-if="userType=='老师'">

				 </view>
				<view v-if="userType=='学生'">
					<uni-list v-for="(item,id) in course" :key = "item.id">
					    <uni-list-item class="course" :title = "item.name" :id="item.id" note="teacher" thumb="/static/course.png" @click="toCourseInfo(id)">
						</uni-list-item>
					</uni-list>
					
				</view>
												
			</view>						
					 
		</view>
		<view v-if="!hasLogin" class="hello">
			<view class="title">
				您好 游客。
			</view>
		</view>

	</view>
</template>

<script>
	import QSNavbar from '@/components/QS-Navbar/QS-Navbar.vue'
	import HmTaskCard from '@/components/hm-task-card/index.vue'
	import action from "@/components/shufei-action/action.vue"
	import uniIcons from "@/components/uni-icons/uni-icons.vue"
	
	import {
		mapState
	} from 'vuex'

	export default {
		components: { QSNavbar,HmTaskCard,action,uniIcons},
		
		
		data() {
		    return {
				hasLogin:false,
				forcedLogin:false,
				id:"",
				userType:"",
				token:"",
				tabbarIndex:0,
																		
				//导航栏
				navbarItems: [
					{
						type: 'text',
						text: '我创建的',
						width: 20,
						layout: 'left',
						color:''
										
					},
					{
						type: 'text',
						text: '我加入的',
						width: 25,
						layout: 'left',
						color:''
												
					},
					{
						type: 'text',
						text:'',
						width:25,
						layout:'center'												
					},
					{
						type: 'icon',
						icon: 'plusempty',
						iconSize: 28,
						width: 10,
						layout: 'right'
					},
					{
						type: 'icon',
						icon: 'scan',
						iconSize: 22,
						width: 12.5,
						layout: 'right'
					}
				],
				course:[
					
					//id,name
				],
				courseMember:[
					//userId,name

				],
				userName:''

				
				
				
		    };
		  },
		onLoad() {			
			try {
			    const value = uni.getStorageSync('userInfo');								
				this.id = value.id
				this.userType = value.userType	
				this.userName = value.userName
				const state = uni.getStorageSync('userState');
				this.hasLogin = state.hasLogin
				this.token = state.token				
				var str = ''				
				if(this.userType == "老师"){
					str = 'courseInfo'
				}
				if(this.userType == "学生"){
					
					str = 'courseInfo2'
				}
				const course = uni.getStorageSync(str);
				this.course = course
			
			} catch (e) {
			    // error
				
			}
			// 监听事件  
			uni.$on('login',(usnerinfo)=>{  
				this.getData()

			})  
			
			if(this.userType == "老师"){
				this.navbarItems[0].color = "#3B7ED5"
				this.tabbarIndex = 0
				
			}
			if(this.userType == "学生"){
				this.navbarItems[1].color = "#3B7ED5"
				this.tabbarIndex = 1
				
			}
			
			if (!this.hasLogin) {
				uni.showModal({
					title: '未登录',
					content: '您未登录，需要登录后才能继续',
					/**
					 * 如果需要强制登录，不显示取消按钮
					 */
					showCancel: !this.forcedLogin,
					success: (res) => {
						if (res.confirm) {
							/**
							 * 如果需要强制登录，使用reLaunch方式
							 */
							if (this.forcedLogin) {
								uni.reLaunch({
									url: '../shilu-login/login'
								});
							} else {
								uni.navigateTo({
									url: '../shilu-login/login'
								});
							}
						}
					}
				});
			}
			this.getCourseMember();
			
		},
		onPullDownRefresh(){					
			setTimeout(()=>{
				this.getData()
				uni.stopPullDownRefresh()
			},1000)
									
		},
		onUnload(){
			uni.$off('login')
		},

			
	    methods: {
		onClick: function(e) {
		  console.log('onClick');
		},
		click(e, index) {
			if(index == 0){
				this.navbarItems[0].color = "#3B7ED5"
				this.navbarItems[1].color = ""
				this.tabbarIndex = 0
				
			}
			if(index == 1){
				this.navbarItems[0].color = ""
				this.navbarItems[1].color = "#3B7ED5"
				this.tabbarIndex = 1
								
			}
			
			if(index == 3){
				 this.openType()
				 
			}

		},
		openType(){

			this.$refs.action.actionConfig={
				list:['创建课程','添加课程','取消'],
				type:5,
				isCloseCancel:true,
				specClass: 'show',
			}
			// uni.hideTabBar({		
			// })
		},
		itemClick(index,type){
			//这里根据不同的类型点击的索引值,做对应的逻辑处理
			//console.log(`你点击的action-sheet的类型是${type},list对应的索引值是${index}`)
			if(index == 0){
				uni.showModal({
					title: '提示',
					content: '您没有权限，请联系管理员',
					success: function (res) {
					    if (res.confirm) {																					
					    } else if (res.cancel) {
					
					    }
					}
				})
			}
			if(index == 1){
				uni.showModal({
					title: '提示',
					content: '请联系管理员添加课程',
					success: function (res) {
					    if (res.confirm) {																					
					    } else if (res.cancel) {
					
					    }
					}
				})
			}
			
			uni.showTabBar({
			})
		},
		titleClick(type){
			//这里根据不同的类型做对应的逻辑处理
			uni.showTabBar({
			})
			
		},
		cancelClick(type){
			
			//点击取消
			uni.showTabBar({
			})
			
		},
			
		toCourseInfo(num){
			uni.navigateTo({
				url: '../main/course?index='+num

			});
			
		},
		getData(){
			
			try {
				const value = uni.getStorageSync('userInfo');								
				this.id = value.id
				this.userType = value.userType
						
				const state = uni.getStorageSync('userState');
				this.hasLogin = state.hasLogin
				this.token = state.token
				var str = ''				
				if(this.userType == "老师"){
					str = 'courseInfo'
				}
				if(this.userType == "学生"){
					str = 'courseInfo2'
				}
				const course = uni.getStorageSync(str);
				this.course = course
				
			} catch (e) {
				// error
			}
			
			if(this.userType == "老师"){
				this.navbarItems[0].color = "#3B7ED5"
				
			}
			if(this.userType == "学生"){
				this.navbarItems[1].color = "#3B7ED5"
				
			}				
			
		},
		async getCourseMember(){
			const courses = this.course
			for(let i in courses){
				var id = courses[i].id
				const members = await this.$myRequest({
					url:'/courseMember',
					data:{
						courseId: id,
						token: this.token
					}
				})
				this.courseMember[i] = members.data
			    		
			}
			
			uni.setStorage({
				key: 'courseMemberInfo',
				data: this.courseMember,
				success: function () {
					
				}
			});
			
									
		}
							
	  }

	}
</script>

<style>
	.hello {
		display: flex;
		flex: 1;
		flex-direction: column;
	}

	.title {
		color: #8f8f94;
		margin-top: 25px;
	}

	.ul {
		font-size: 15px;
		color: #8f8f94;
		margin-top: 25px;
	}

	.ul>view {
		line-height: 25px;
	}
	.float{
		display: flex;
		flex-wrap: wrap;
		align-items: center;
	}
	.course{
		height: 70px;

	}
	
</style>
