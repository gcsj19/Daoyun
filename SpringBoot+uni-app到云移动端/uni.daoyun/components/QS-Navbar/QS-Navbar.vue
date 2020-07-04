<template>
	<view class="QS-navbar">
		<view :class="{ 'QS-navbar--fixed': String(fixed)==='true' }" :style="{ 'background-color': backgroundColor, 'z-index': zIndex }">
			<view class="QS-navbar-statusBar" :style="{ 'height': statusBarHeight }" v-if="String(statusBar)==='true'"></view>
			<view class="QS-navbar-header" :style="{ 'padding-right': menuButtonInfoLeft + 'px' }">
				<block 
				v-for="(item, index) in navbarItems || []" 
				:key="index" >
					<view class="QS-navbar-item" :style="{ 
						'width': (Number(item.width)*wW + 'rpx'), 
						'background-color': item.backgroundColor, 
						'border-style': item.borderStyle,
						'border-width': item.borderWidth,
						'border-color': item.borderColor,
						'border': item.border,
						'border-bottom-left-radius': item.borderBottomLeftRadius,
						'border-bottom-right-radius': item.borderBottomRightRadius,
						'border-top-left-radius': item.borderTopLeftRadius,
						'border-top-right-radius': item.borderTopRightRadius,
						'border-radius': item.borderRadius,
						'padding': item.padding,
						
						'justify-content': item.layout==='left'?'flex-start':item.layout==='right'?'flex-end':'center'
					}"
				:data-index="index" 
				@tap.stop="_click($event)">
						<block v-if="item&&item.type === 'search'">
							<view class="QS-navbar-item--search">
								<uniIcons type="search" :size="item.iconSize || iconSize" :color="item.iconColor || '#000000'"></uniIcons><text class="QS-navbar-text text_overflow_ellipsis" :style="{ 'color': item.color || '#000000' }">{{item.text}}</text>
							</view>
						</block>
						<block v-else-if="item&&item.type === 'icon'">
							<uniIcons :type="item.icon" :size="item.iconSize || iconSize" :color="item.iconColor || '#000000'"></uniIcons>
						</block>
						<block v-else-if="item&&item.type === 'image'">
							<image :src="item.src" :mode="item.mode||'aspectFill'" :style="{
								'border-radius': item.itemBorderRadius || '8rpx',
								'background-color': item.itemBackgroundColor || '',
								'width': item.itemWidth || '100%',
								'height': item.itemHeight || '100%'
							}"></image>
						</block>
						<block v-else-if="item&&item.type==='text'"><text class="text_overflow_ellipsis" :style="{
								'border-radius': item.itemBorderRadius || '8rpx',
								'background-color': item.itemBackgroundColor || '',
								'width': item.itemWidth || '100%',
								'height': item.itemHeight || '100%',
								'font-size': item.fontSize || '16px',
								'color': item.color || '#000',
								'text-align' : item.textAlign || 'center',
								'font-weight': item.weight || '0'
							}">{{item.text}}</text>
						</block>
						<block v-else-if="item&&item.type==='colImageText'">
							<view class="colImageText" :style="{
								'width': item.itemWidth || '100%',
								'height': item.itemHeight || '100%',
								'border-radius': item.itemBorderRadius || '8rpx',
								'background-color': item.itemBackgroundColor || '',
								'font-size': item.fontSize || '12px', 
								'color': item.color || '#000', 
								'font-weight': item.weight || '0'
							}">
								<image 
								:src="item.src" 
								:mode="item.mode||'aspectFill'" 
								class="colImageText-image"
								:style="{
									'border-radius': item.imageBorderRadius || '4px',
								}"></image><text class="colImageText-text">{{item.text}}</text>
							</view>
						</block>
					</view>
				</block>
			</view>
		</view>
		<block v-if="String(hasPlacherholder) === 'true' && String(fixed)==='true'">
			<view class="QS-navbar-statusBar" :style="{ 'height': statusBarHeight }" v-if="String(statusBar)==='true'&&String(fixed)==='true'"></view>
			<view class="QS-navbar--fixed--placherholder" v-if="String(fixed)==='true'"></view>
		</block>
	</view>
</template>

<script>
	import uniIcons from '@/components/uni-icons/uni-icons.vue';
	const statusBarHeight = uni.getSystemInfoSync().statusBarHeight
	export default {
		components: {
			uniIcons
		},
		props: {
			fixed: {
				type: [Boolean, String],
				default: true
			},
			backgroundColor: {
				type: String,
				default: '#ffffff'
			},
			statusBar: {
				type: [Boolean, String],
				default: true
			},
			navbarItems: {
				type: Array,
				default () {
					return [];
				}
			},
			diffMenuButtonInfo: {
				type: [Boolean, String],
				default: true
			},
			hasPlacherholder: {
				type: [Boolean, String],
				default: true
			},
			zIndex: {
				type: [Number, String],
				default: 998
			}
		},
		data() {
			return {
				statusBarHeight: statusBarHeight + 'px',
				menuButtonInfoLeft: 0,
				iconSize: 26,
				wW: 7.2
			};
		},
		created() {
			// #ifdef MP
			if (String(this.diffMenuButtonInfo) === 'true') {
				const {
					windowWidth
				} = uni.getSystemInfoSync();
				const menuButtonInfoLeftDx = uni.getMenuButtonBoundingClientRect().left - 10;
				const menuButtonInfoLeft = (windowWidth - menuButtonInfoLeftDx);
				this.menuButtonInfoLeft = menuButtonInfoLeft;
				this.wW = menuButtonInfoLeftDx / windowWidth * 750 / 100;
			}
			// #endif
		},
		methods: {
			_click(e) {
				//console.log('点击了')
				let index;
				// #ifdef APP-NVUE
				if(e.currentTarget.dataset) {
					index = e.currentTarget.dataset.index;
				}
				else {
					index = e.currentTarget.attr.dataIndex;
				}
				// #endif
				// #ifndef APP-NVUE
				index = e.currentTarget.dataset.index;
				// #endif
				if(index == undefined || index == null) {
					uni.showToast({
						icon: 'none',
						title: '找不到下标',
						position: 'center'
					})
					return;
				}
				const item = this.navbarItems[index];
				if (item.doEvent) {
					switch (item.doEvent) {
						case 'back':
							uni.navigateBack()
							break;
						case 'navigateTo':
							uni.navigateTo({
								url: item.targetPath
							})
							break;
						default:
							this.$emit('click', item, index);
							break;
					}
				} else {
					this.$emit('click', item, index);
				}
			},
			getQuery(cb) {
				let view;
				// #ifndef MP-BAIDU || MP-ALIPAY
				view = uni.createSelectorQuery();
				// #endif
				// #ifdef MP-BAIDU || MP-ALIPAY
				view = uni.createSelectorQuery().in(this);
				// #endif
				view.select('.QS-navbar').fields({
					size: true
				})
				view.exec(res => {
					console.log('获取navbar布局信息成功: ' + JSON.stringify(res))
					if (res[0]) {
						if (cb && typeof cb === 'function') cb(res[0])
					} else {
						if (cb && typeof cb === 'function') cb({
							height: 77
						})
					}
				})
			}
		}
	}
</script>

<style scoped>
	/* #ifndef APP-NVUE */
	view,
	scroll-view,
	swiper,
	swiper-item,
	cover-view,
	cover-image,
	icon,
	text,
	rich-text,
	progress,
	button,
	checkbox,
	form,
	input,
	label,
	radio,
	slider,
	switch,
	textarea,
	navigator,
	audio,
	camera,
	image,
	video,
	picker-view,
	picker-view-column {
		box-sizing: border-box;
	}
	/* #endif */

	.QS-navbar {
		width: 750rpx;
	}

	.QS-navbar-statusBar {
		width: 750rpx;
		height: 20px;
	}

	.QS-navbar--fixed {
		position: fixed;
	}

	.QS-navbar-header {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		width: 750rpx;
		height: 44px;
		padding: 5px 15rpx;
		flex-wrap: nowrap;
		overflow: hidden;
	}

	.QS-navbar--fixed--placherholder {
		width: 750rpx;
		height: 44px;
	}

	.QS-navbar-item {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		overflow: hidden;
	}

	.QS-navbar-item--search {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding: 3px 8px;
	}

	.QS-navbar-text {
		font-size: 30rpx;
	}

	.text_overflow_ellipsis {
		/* #ifndef APP-NVUE */
		word-break: break-all;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		/* #endif */
		/* #ifdef APP-NVUE */
		lines: 1;
		/* #endif */
	}

	.colImageText {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	.colImageText-image{
		height: 20px;
		width: 20px;
	}
	.colImageText-text{
		height: 14px;
		/* #ifndef APP-NVUE */
		width: 100%;
		/* #endif */
		font-size: 10px;
		margin-top: 1px;
		text-align: center;
	}
</style>
