<template>
	<view class="content">		

		<view class="example-body" v-if="listId==0">
						
				<uni-steps :options="signRecord" direction="column" :active="active" active-color="#4ca2ff">					
				</uni-steps>
							
		</view>		
		<view class="example-body" v-if="listId==1" >
			
			<view class="example-body">
				<uni-nav-bar :fixed="false" color="#333333"  background-color="#FFFFFF" right-icon="navigate"  @clickRight="toSign(index)">
					<block slot="left"></block>				
					<view class="input-view">
						<uni-icons class="input-uni-icon" type="search" size="22" color="#666666" />
						<input confirm-type="search" class="nav-bar-input" type="text" placeholder="输入搜索关键词" @confirm="confirm">
					</view>
					<block slot="right">
						<view><text class="uni-nav-bar-text">签到</text></view>
					</block>
				</uni-nav-bar>
				
			</view>
			
			<uni-list v-for="(item,id) in member" :key = "item.userId">
               <uni-list-item :title="item.userName" :show-extra-icon="true" :extra-icon="{color: '#B0C4DE',size: '35',type: 'contact'}" :show-arrow="false">
				   
			   </uni-list-item>
				
			</uni-list>
			
		</view>
		<view class="example-body" v-if="listId==2">
			<view v-if="userType=='老师'">
				<uni-list v-for="(item,id) in member" :key = "item.userId">
				    <uni-list-item  :title = "item.userName" :id="item.id" :show-extra-icon="true" :extra-icon="{color: '#B0C4DE',size: '35',type: 'contact'}" @click="toStudentInfo(id)">
					</uni-list-item>
				</uni-list>
				
				
			</view>
			
		</view>
		
		
		<view class="bar">
			<hans-tabber class="bar" :list="list"  @tabChange="tabChange"></hans-tabber>			
		</view>
			
		
	</view>
</template>

<script>
	
	import hansTabber from '@/components/hans-tabbar/hans-tabbar.vue'
	import uniNavBar from "@/components/uni-nav-bar/uni-nav-bar.vue"
	import uniSteps from '@/components/uni-steps/uni-steps.vue'
	

	export default{
		components:{
			hansTabber,
			uniNavBar,
			uniSteps
			
		},
				
		onLoad:function(options) {
										
			try{
				
				const userInfo = uni.getStorageSync("userInfo")
				this.userType = userInfo.userType
				const userState = uni.getStorageSync("userState")
				this.token = userState.token
				this.userId = userState.id
				var str = ''			
				if(this.userType == "老师"){
					str = 'courseInfo'
				}					
				if(this.userType == "学生"){
					str = 'courseInfo2'					
				}
				const value = uni.getStorageSync(str)	
				this.courseName = value[options.index].name
				this.courseId = value[options.index].id
				const members = uni.getStorageSync('courseMemberInfo')
				this.member = members[options.index]							
														
			}catch(e){
				
			}
			this.index = options.index
			uni.setNavigationBarTitle({
				title: this.courseName
			})
			if(this.userType == "老师"){
				this.list[2].text = '签到情况'
			}
			this.getSignRecord(this.userType)
			
		},
		onPullDownRefresh(){
			setTimeout(()=>{
				this.getSignRecord(this.userType)
				uni.stopPullDownRefresh()
			},1000)
									
		},
		data() {
			return{
				courseName:"",
				courseId:"",
				member:[],
                list: [{
                     "text": "签到记录",
                     "iconPath": '../../static/enter.png' ,
                     "selectedIconPath": '../../static/enter-select.png'
                   },
                   {
                     "text": "课内成员",
                     "iconPath": '../../static/correct.png',
                     "selectedIconPath":'../../static/correct-select.png'
                     },
                     {
                       "text": "成绩分析",
                       "iconPath": '../../static/analyse.png',
                       "selectedIconPath": '../../static/analyse-select.png'
                     }]	,
				listId:1,
				index:0,
				userType:"",
				signRecord:[
				
				],
				courseSignRecord:[
					
				],
				token:'',
				userId:'',
				active:-1,
				score:0

				
				
				

				
			}
		},
		methods:{
			
            tabChange(index) {
				this.listId = index
            },
			click(e, index) {
				uni.showToast({
					title: `点击了第${index}项`,
					position: 'center',
					icon: 'none'
				})
			},
			toSign(index){
				
				if(this.userType == "老师"){
					uni.navigateTo({
						url:'../index/sign-in-start?index='+index
					})
				}
				if(this.userType == "学生"){
					uni.navigateTo({
						url:'../sign/sign-in?index='+index
					})

				}

				
			},
				
			confirm(){
				
			},
			async getSignRecord(userType){
				if(userType == "老师"){
					const value = await this.$myRequest({
						url:'/signRecord',
						data:{
							userId:this.userId,
							courseId:this.courseId,
							token:this.token
							
						}
											
					})
					this.courseSignRecord = value.data
					this.setRecord()
					
				}
				else{
					const value = await this.$myRequest({
						url:'/signRecord2',
						data:{
							userId:this.userId,
							courseId:this.courseId,
							token:this.token
							
						}
											
					})
					this.courseSignRecord = value.data
					this.setRecord2()
					
				}
				
								
			},
			setRecord(){
				this.signRecord = []
				this.active = -1
				let signs = this.courseSignRecord
				let count = 0
				signs.reverse()
				for(let i in signs){						
					if(count > 8){
						break
					}
					count++
					if(signs[i].alive == true){
						this.active = 0
					}

					let arry = {
						title:'',
						desc:''
					}
					arry.title = "5分钟签到"
					arry.desc = signs[i].createTime
										
					this.signRecord.push(arry)
										
				}
				
			
			},
			setRecord2(){
				this.signRecord = []
				let signs = this.courseSignRecord
				let count2 = 0
				signs.reverse()				
				for(let i in signs){
					if(count2 < 9){

						let arry = {
							title:'',
							desc:''
						}
						
						arry.title = signs[i].status
						arry.desc = signs[i].createTime											
						this.signRecord.push(arry)
					}
					count2++
					
					if(signs[i].status == "正常签到"){
						this.score = this.score + 2
					}			

				}
				
				
				
			},
			toStudentInfo(index){
				
				let userId = this.member[index].userId
				let courseId = this.courseId

				
				uni.navigateTo({
					url:'../main/studentSignInfo?userId='+userId+'&courseId='+courseId
				})
				
			}
			
			
				
					
						
			
		}
		
		
	}
</script>

<style>
	.bar{
		position:fixed;
		bottom:0;
		width:100%;
		left:0;
		right:0;	
		
	}
	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	}

	/* #endif */

	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	view {
		font-size: 14px;
		line-height: inherit;
	}

	.example {
		padding: 0 15px 15px;
	}

	.example-info {
		padding: 15px;
		color: #3b4144;
		background: #ffffff;
	}

	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14px;
		background-color: #ffffff;
	}

	/* #endif */
	.example {
		padding: 0 15px;
	}

	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 15px;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 14px;
		line-height: 20px;
	}

	.example-info-text {
		font-size: 14px;
		line-height: 20px;
		color: #3b4144;
	}


	.example-body {
		flex-direction: column;
		padding: 15px;
		background-color: #ffffff;
	}

	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}

	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}

	.word-btn--hover {
		background-color: #4ca2ff;
	}

	.uni-nav-bar-text {
		font-size: 23rpx;
	}

	.city {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: flex-start;
		/* width: 160rpx;
 */
		margin-left: 4px;
	}

	.input-view {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		/* width: 500rpx;
 */
		flex: 1;
		background-color: #f8f8f8;
		height: 30px;
		border-radius: 15px;
		padding: 0 15px;
		flex-wrap: nowrap;
		margin: 7px 0;
		line-height: 30px;
	}

	.input-uni-icon {
		line-height: 30px;
	}

	.nav-bar-input {
		height: 30px;
		line-height: 30px;
		/* #ifdef APP-PLUS-NVUE */
		width: 370rpx;
		/* #endif */
		padding: 0 5px;
		font-size: 28rpx;
		background-color: #f8f8f8;
	}

	.example-body {
		padding: 0;
	}
</style>
