<template>
	<view>
		<uni-swiper-dot :info="info" :current="current" field="content" :mode="mode">
		    <swiper class="swiper-box" @change="change">
		        <swiper-item v-for="(item ,index) in info" :key="index">
		            <view class="swiper-item">
		                {{item.content}}
		            </view>
		        </swiper-item>
		    </swiper>
		</uni-swiper-dot>
		<view>
			<text>
				测试账号：
				   15900000002 老师 密码 123456
				   15900000003 学生1 密码 123456
				   15900000004 学生2 密码 123456
				使用：
				   老师提供两门课程，学生1有一门工程训练，学生2有两门，工程训练和自然辨证法，课程设置由管理员配置
				   
				   1、老师进入某项课程发起签到，签到时长是五分钟
				   2、该课程所在成员在老师发布签到后，即可开始签到，若离老师的坐标超出2千米则会被记为位置异常的签到
				   若超出时间范围签到以及没有签到则被记为缺勤
				   
				   3、老师下拉刷新可查看学生签到情况
				
			</text>
		</view>
	</view>
</template>

<script>
	import uniSwiperDot from "@/components/uni-swiper-dot/uni-swiper-dot.vue"

	export default {
		
		components: {uniSwiperDot},
		data() {
			return {
				info: [{
					content: '使用说明'
				}, {
					content: '使用说明'
				}, {
					content: '使用说明'
				}],
				current: 0,
				mode: 'round',
			}
		},
		methods: {
			change(e) {
				this.current = e.detail.current;
			}
		}
	}
</script>

<style>

</style>
