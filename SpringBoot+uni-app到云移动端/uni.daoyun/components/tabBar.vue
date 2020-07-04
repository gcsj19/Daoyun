<template>
	<view class="zdTabBar">
		<view class="ul">
			<view :class="['li',current == index?'cur' :'']" v-for="(item,index) in taBbarList" :key="index" @tap="navigatorTo(item.url)">
				<view class="img" v-if="item.type == 0">
					<image :src="current == index ? item.selectIcon : item.icon" mode="widthFix"></image>
				</view>
				<view class="ic" v-if="item.type == 1">+</view>
				<view class="p">{{item.name}}</view>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		props: {
			current: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				taBbarList: [{
						type: 0,
						icon: '/static/enter.png',
						selectIcon: '/static/enter-select.png',
						name: '签到记录',
						url: ''
					},
					{
						type: 1,
						icon: '/static/logo.png',
						selectIcon: '/static/logo_cur.png',
						name: '签到',
						url: '/pages/index/index'
					},
					{
						type: 0,
						icon: '/static/correct.png',
						selectIcon: '/static/correct-select.png',
						name: '成员',
						url: ''
					}
				]
			}
		},
		methods: {
			navigatorTo(e) {
				uni.redirectTo({
					url: e,
				});
			}
		}
	}
</script>
