<template>
	<view>
		<uni-steps :options="signRecord" direction="column" :active="active" active-color="#4ca2ff">
		</uni-steps>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
				signRecord:[
				
				],
				courseSignRecord:[
					
				],
				userId:'',
				courseId:'',
				token:'',
				active:-1,
				userName:''
		
				

				
			}
		},
		onLoad:function(options){
			
			this.userId = options.userId
			this.courseId = options.courseId
			
			try{
				const userState = uni.getStorageSync("userState")
				this.token = userState.token
	
								
			}catch(e){								
			}
			
			setTimeout(()=>{
				uni.showToast({
					icon:'loading'
				})
				
			},200)


			this.getSignRecord()

			
			
			
		},
		methods: {
			async getSignRecord(){
				const value = await this.$myRequest({
					url:'/signRecord2',
					data:{
						userId:this.userId,
						courseId:this.courseId,
						token:this.token
						
					}
										
				})
				this.courseSignRecord = value.data
				this.setRecord()

				
				
				
			},
			setRecord(){
				let signs = this.courseSignRecord
				signs.reverse()	
				for(let i in signs){
					let arry = {
						title:'',
						desc:''
					}
					arry.title = signs[i].status
					arry.desc = signs[i].createTime
					this.signRecord.push(arry)
					
				}
				
			}
			
		}
	}
</script>

<style>

</style>
