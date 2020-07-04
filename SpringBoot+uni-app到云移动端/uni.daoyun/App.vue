<script>
	
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {
		onLaunch: function() {		
			 			
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		
		computed: {
			...mapState(['hasLogin', 'id','userName','Email'])
		}
	}
</script>

<style lang="less">
	
	
	
</style>
