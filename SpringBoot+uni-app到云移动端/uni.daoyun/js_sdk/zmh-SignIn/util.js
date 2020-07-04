var isIos
// #ifdef APP-PLUS
isIos = (plus.os.name == "iOS")
// #endif



function openAllGPS(isIos) {
	let content = isIos ? "为保证签到成功，请打开系统设置->隐私->定位服务->开启定位服务" :
		"为保证签到成功，请务必开启GPS定位服务！"
	uni.showModal({
		title: '警告',
		content: content,
		showCancel: false, // 不显示取消按钮
		success(res) {
			if (res.cancel) {
				return plus.runtime.quit();
			}
			if (isIos) {
				var UIApplication = plus.ios.import("UIApplication");
				var application2 = UIApplication.sharedApplication();
				var NSURL2 = plus.ios.import("NSURL");
				// var setting2 = NSURL2.URLWithString("prefs:root=LOCATION_SERVICES");		
				var setting2 = NSURL2.URLWithString("app-settings:");
				application2.openURL(setting2);

				plus.ios.deleteObject(setting2);
				plus.ios.deleteObject(NSURL2);
				plus.ios.deleteObject(application2);
			} else {
				var Intent = plus.android.importClass('android.content.Intent');
				var Settings = plus.android.importClass('android.provider.Settings');
				var intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				var main = plus.android.runtimeMainActivity();
				main.startActivity(intent); // 打开安卓系统设置GPS服务页面
			}
		}
	});
}


// 上传GPS 坐标
function uploadGps(baseUrl, userName, userId) {
	uni.getLocation({
		type: 'gcj02',
		success: function(result) {
			// console.log(JSON.stringify(result));
			// result.latitude,result.longitude
			let data = {
				"Account": userName,
				"UserId": userId,
				"Longitude": result.longitude,
				"Latitude": result.latitude
			}
			// console.log(data);
			uni.request({
				url: baseUrl + '/api/Common/GspLocation', // api.apiPublic.GspLocation,
				method: 'POST',
				data: data,
				success: function(res) {
					// console.log(res.data);
				}
			})

		}
	});
}



// 计算坐标距离
function Rad(d) {
	return d * Math.PI / 180.0; //经纬度转换成三角函数中度分表形式。
}

function GetDistance(lat1, lng1, lat2, lng2) {
	var radLat1 = Rad(lat1);
	var radLat2 = Rad(lat2);
	var a = radLat1 - radLat2;
	var b = Rad(lng1) - Rad(lng2);
	var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
		Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
	s = s * 6378.137; // EARTH_RADIUS;
	s = Math.round(s * 10000) / 10000; //输出为公里
	//s=s.toFixed(4);
	return s * 1000;
}

function judgeSign(allPark, lat, lon) { // 计算坐标距离
	// console.log(lat,lon);
	let status = false,
		carParkLast, err = '未知错误';
	if (allPark.carParkList.length < 1) {
		return {
			ok: false,
			err: '未匹配到停车场'
		};
	};
	let firstDistance = 0,
		distance = [],
		getIndex = 0,
		myRADIUS;

	allPark.carParkList.forEach((item, index) => {
		if (item.PK_ORG == allPark.PK_ORG) {
			// 属于该停车场
			let tt = GetDistance(lat, lon, item.LATITUDE, item.LONGITUDE);
			console.log(tt, item.RADIUS);
			if (tt < item.RADIUS) { // 在允许签到范围内
				distance.push(tt);
				status = true;
				item.myRADIUS = tt;
				getIndex = index;
				err = '';
				// console.log(distance);
				if (index != 0 && distance[index] < distance[index - 1]) {
					getIndex = index;
					carParkLast = item;
					item.myRADIUS = tt;
					err = '';
				}
			} else {
				err = '签到失败：未在停车场签到范围内。';
			}
		} else if (distance.length < 1 && item.PK_ORG != allPark.PK_ORG) {
			status = false
			err = '未匹配到该组织下的停车场';
		}
	})
	let b = allPark.carParkList[getIndex];
	// console.log(getIndex);
	// console.log(distance);
	return {
		ok: status,
		item: b,
		err: err
	};



}




// 跳转到**应用**的权限页面
function gotoAppPermissionSetting() {
	if (isIos) {
		var UIApplication = plus.ios.import("UIApplication");
		var application2 = UIApplication.sharedApplication();
		var NSURL2 = plus.ios.import("NSURL");
		// var setting2 = NSURL2.URLWithString("prefs:root=LOCATION_SERVICES");		
		var setting2 = NSURL2.URLWithString("app-settings:");
		application2.openURL(setting2);

		plus.ios.deleteObject(setting2);
		plus.ios.deleteObject(NSURL2);
		plus.ios.deleteObject(application2);
	} else {
		console.log(plus.device.vendor);
		var Intent = plus.android.importClass("android.content.Intent");
		var Settings = plus.android.importClass("android.provider.Settings");
		var Uri = plus.android.importClass("android.net.Uri");
		var mainActivity = plus.android.runtimeMainActivity();
		var intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		var uri = Uri.fromParts("package", mainActivity.getPackageName(), null);
		intent.setData(uri);
		mainActivity.startActivity(intent);
	}
}

// 设置页面标题
function setPageTitle(d) {
	uni.setNavigationBarTitle({
		title: d
	});
}

module.exports = {
	gotoAppPermissionSetting,
	openAllGPS,
	GetDistance
}
