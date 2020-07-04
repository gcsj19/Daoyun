<template>
	<view class="content">
		<view class="page-body">
			<view class="sign-title">
				<!--标题-->
				<view class="sign-title-l">
					<view class="portrait">
						{{name.substr(-2)}}
					</view>
					<view class="text">
						<view class="name">
							{{name}}
						</view>
					</view>
				</view>
				<!--日期-->
				<view class="sign-title-r">
					<view class="date">
						<picker mode="date" :value="date"  @change="bindDateChange">
							<view class="uni-input">{{date}} <uni-icon type="arrowdown"></uni-icon></view>							
						</picker>
					</view>
				</view>
				
				
			</view>
			
			<!--签到球-->
			<view class="uni-timeline">
				<view class="uni-timeline-item uni-timeline-first-item">
					<view class="uni-timeline-item-content">
						<view class="content-show">
							<view>
								<view v-if="isAlreadySign==false"class="module CBlue" @click="clickSign">
									<view class="text">发起签到</view>
									<view class="time">{{time}}</view>
								</view>
								<view v-if="isAlreadySign==true">
									<view class="module CAsh">
										<view class="text">已开始</view>
										<view class="time">{{time}}</view>
									</view>
								</view>

							</view>
						
						</view>
						
					</view>
					
					
				</view>
				
				
			</view>

			
			
			
		</view>
		
		
		
		
	</view>
	
	
</template>

<script>
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import uniIcon from "@/components/uni-icon/uni-icon.vue"
	import {formateDate,pointInsideCircle ,isSameDay} from "@/common/util.js"	
	import {handleSignClick,setSignInfo,addSignInfo,getSignInfo,delSignInfo,getInfo , key} from "./index.js"
	
	export default{
		components: {uniIcon,uniPopup},
		data(){
			return{
				name:"",
				date:formateDate(new Date(),'Y-M-D'),
				time:formateDate(new Date(),'h:min:s'),	
				longitude:'',
				latitude:'',
			    deadline:'',
				remainingd:300,  //数据返回时间秒
				remaining:'',  //显示剩余时间
				m:5,
				s:59,
				isAlreadySign:false,
				userId:'',
				token:''
																							
			}
			
		},
		onLoad:function(options){
							
			try{
				const courses = uni.getStorageSync("courseInfo")				
				this.name = courses[options.index].name
				const userstate = uni.getStorageSync('userState')
				this.userId = userstate.id
				this.token = userstate.token
				const sign = uni.getStorageSync("signInfo")
				if(sign !== null){
					this.deadline = sign.endTime					
				}
							
			}catch(e){																
			}
			
			this.getCountTime()
			this.getTimes()
			this.getLocation()

												
		},
			
		onUnload(){
			
		},
		methods:{
			//发起签到
			clickSign(){
				var that = this							
				uni.showModal({
				    title: '提示',
				    content: '确认要发起签到吗？',
				    success: function (res) {
				        if (res.confirm) {
														
							that.isAlreadySign = true;
							that.startSign()
																					
				        } else if (res.cancel) {

				        }
				    }
				});
				
			},
			// 获取当前位置
		    getLocation(){
				var that = this
				uni.getLocation({
					type: 'wgs84',
					success: function (res) {
						that.longitude = res.longitude
						that.latitude = res.latitude
												
					}
				});
			},
			bindDateChange(){
				
			},
			async startSign(){
				let dateEnd = new Date();//获取当前时间				
				let t = this.happenTimeFun(dateEnd.getTime())
				const value = await this.$myRequest({
					url:'/sign',
					data:{
						courseName:this.name,
						userId:this.userId,
						longitude:this.longitude,
						latitude:this.latitude,
						time:t,
						token:this.token
					}				
				})					
				if(value){

				}					
				else{
					console.log("发起失败")
				}
						
				
			},
			//时间戳转换成 “yyyy-MM-dd hh:mm:ss”格式
			happenTimeFun(num){//时间戳数据处理			
			    let date = new Date(num);			
			//时间戳为10位需*1000，时间戳为13位的话不需乘1000			
		        let y = date.getFullYear();			
		        let MM = date.getMonth() + 1;			
		        MM = MM < 10 ? ('0' + MM) : MM;//月补0			
		        let d = date.getDate();			
		        d = d < 10 ? ('0' + d) : d;//天补0			
		        let h = date.getHours();			
		        h = h < 10 ? ('0' + h) : h;//小时补0			
		        let m = date.getMinutes();			
		        m = m < 10 ? ('0' + m) : m;//分钟补0			
		        let s = date.getSeconds();			
		        s = s < 10 ? ('0' + s) : s;//秒补0		
		        return y + '-' + MM + '-' + d + ' ' + h + ':' + m+ ':' + s;
			
			},
			//“yyyy-MM-dd hh:mm:ss”格式 转换成时间戳
			timeProcessing(stringTime){	
				
				let timeDate = stringTime			
				let Time = new Date(timeDate)							
				let timestemp = Time.getTime()				
				return timestemp
		
			},
											
			timeCD(){
				var dj = this.remainingd
				if(dj <= 0){
					this.remaining = "00:00"
					this.isAlreadySign = false
				}else{
					var ddf = this.djs(dj)
					this.remaining = ddf
					//当前时间减去时间
					dj = dj - 1
					this.remainingd = dj
				}				
				
			},
			//秒换算成时分秒
			djs(ms){
				var h = parseInt(ms / (60 * 60));
				var m = parseInt((ms % (60 * 60)) / 60)
				var s = (ms % (60 * 60)) % 60
				return  m + ":" + s
				
			},
			// 实时时间
			getTimes(){
				var that = this;
				setInterval(function(){
					that.time = formateDate(new Date(),'h:min:s')
				},1000)
			},
			async getCountTime(){				
				let dateEnd = new Date();//获取当前时间
				let t = this.happenTimeFun(dateEnd.getTime())				
				const value = await this.$myRequest({
					url:'/checkSign',
					data:{
						id: this.userId,
						time: t,
						token: this.token
					}
					
				})

				if(value.data.alive == true){
					this.deadline = value.data.endTime
					this.isAlreadySign = true
					// var that = this
					// setInterval(function(){
					// 	that.timeCD()						
					// },1000)

										
				}
				else{
					this.isAlreadySign = false
				}
								
			},
			setCutDown(){
				
			}
			
		}
		
	}
	
	
</script>

<style>
	.map{width: 100%; height: 260px;}
	.uni-list-cell-left {padding: 0 30upx;}
	.text-desc{display: flex;justify-content: space-between;padding:10upx 20upx}
	.colorRed{color: red;}
	.colorGreen{color: #32CD32;}
	.colorYellow{color: yellow;}
	.colorBlue{color: #007aff;}
	
	.bgBlue{background-color: #007aff;}
	.bgGreen{background-color: #32CD32;}
	.bgAsh{background-color: #C8C7CC;}
	
	.uni-timeline{padding: 30upx 20upx;}
	.uni-timeline-item-content-t{font-weight: bold;}
	.desc-pad{padding: 0 0upx}
	.desc-pad .bz{color: rgb(0, 122, 255);}
	.desc-pad .bz .icon{color:rgb(0, 122, 255)}
	.uni-timeline-last-item .uni-timeline-item-divider{background: #bbb;}
	
	.CBlue{background-color: #4ca2ff;box-shadow: 0 3px 3px #007aff;}
	.CGreen{background-color: #32CD32;box-shadow: 0 5px 5px #32CD32;}
	.CAsh{background-color: #C8C7CC;box-shadow: 0 5px 5px #C8C7CC;}
	
	.module{overflow: hidden; margin: 20upx auto; width: 220upx;height: 220upx;border-radius: 50%;color: #fff;text-align: center;}
	.module .text{font-size: 20px;margin: 50upx auto 10upx;}
	.uni-timeline-item .uni-timeline-item-content{width: 100%;padding-right: 20upx;}
	
	.content-show{width:100%}
	
	.sign-title{display: flex;justify-content: space-between; padding: 30upx 24upx;border-bottom: 1upx solid #333;}
	.sign-title .portrait{width: 100upx;height: 100upx;line-height: 100upx;border-radius: 50%;background-color: #007AFF;color: #fff;font-size: 28upx;text-align: center;}
	.sign-title .sign-title-l{display: flex;}
	.sign-title .sign-title-l .text{margin-left: 20upx;}
	.sign-title .sign-title-l .text .name{font-size: 32upx;}
	.sign-title .sign-title-l .text  .gz{color: darkblue;display: inline-flex;}
	.sign-title .sign-title-l .text .gz text{display: inline-block;}
	.sign-title .sign-title-l .text .gz .t1{
		overflow: hidden; /*超出部分隐藏*/
	text-overflow:ellipsis;/* 超出部分显示省略号 */
	white-space: nowrap;/*规定段落中的文本不进行换行 */
	width: 166upx;/*需要配合宽度来使用*/}
	.iswq{padding: 0px 12px;color: #99CC33;border: 1px solid #99CC33;width: 26px;height: 24px;border-radius: 4px;margin-left: 20upx;display: inline-block;}
	.desc-pad .last{margin-bottom:80upx;}
	.relocation{color: #0000FF;}
	.uni-popup .content .text{color:#666666;}
	
	
	
</style>
