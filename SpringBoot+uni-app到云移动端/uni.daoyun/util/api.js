const BASE_URL = 'http://123.56.164.172:8081'
const usl = 'http://123.56.164.172:8081'
export const myRequest = (options) =>{	
	return new Promise((reslove,reject)=>{
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			success: (res) => {
				// if(res.data.status !== 0){
				// 	console.log(res)
				// 	return uni.showToast({
				// 		title:'获取数据失败！'
				// 	})
				// }				 
				reslove(res)
			},
			fail: (err) => {
				uni.showToast({
					title:'请求失败！'
				})
				reject(err)
			}
			
				
			
		})
		
	})
}