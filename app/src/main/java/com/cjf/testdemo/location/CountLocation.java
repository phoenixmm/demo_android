package com.cjf.testdemo.location;

/**
 * 采集用户的基站信息类
 * 
 * @author weijiang204321
 *
 */
public class CountLocation {

//	private LocationManager mLocationManager;
//	private LocationResult locationResult;
//	private boolean gps_enabled = false;
//	private boolean network_enabled = false;
//
//	public boolean getLocation(Context context, LocationResult result) {
//		if (context == null)
//			return false;
//		try {
//			locationResult = result;
//			if (mLocationManager == null) {
//				try {
//					mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//				} catch (Exception e) {
//					AppListYPLog.e("获取locationManager对象出现异常:" + e.getMessage());
//				}
//			}
//			try {
//				gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//			} catch (Exception e) {
//				AppListYPLog.e("获取gps是否可用值的时候出现异常:" + e.getMessage());
//			}
//			AppListYPLog.i("gps是否可用:" + gps_enabled);
//
//			try {
//				network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && CountUtils.checkNetWorkState();
//			} catch (Exception e) {
//				AppListYPLog.e("获取net是否可用值的时候出现异常:" + e.getMessage());
//			}
//			AppListYPLog.i("net是否可用:" + network_enabled);
//
//			// 如果gps和net都不可用的话直接返回false
//			if (!gps_enabled && !network_enabled)
//				return false;
//
//			// 如果gps可用的话,就用gps
//			if (gps_enabled) {
//				try {
//					mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps, Looper.myLooper());
//				} catch (Exception e) {
//					AppListYPLog.e("设置gps位置更新的回调接口时出现异常:" + e.getMessage());
//				}
//			}
//			// 如果net可用的话，就用net
//			if (network_enabled) {
//				try {
//					mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork, Looper.myLooper());
//				} catch (Exception e) {
//					AppListYPLog.e("设置net位置更新的回调接口时出现异常:" + e.getMessage());
//				}
//			}
//
//			// 获取最新的location对象
//			getLastLocation();
//			return true;
//
//		} catch (Exception e) {
//			AppListYPLog.e("调用getLocation方法获取Location对象的时候出现异常:" + e.getMessage());
//		}
//		return false;
//	}
//
//	/**
//	 * Gps更新的回调接口
//	 */
//	LocationListener locationListenerGps = new LocationListener() {
//		public void onLocationChanged(Location location) {
//			try {
//				AppListYPLog.e("GPS回调接口  locationListenerGps");
//				locationResult.gotLocation(location);
//				mLocationManager.removeUpdates(this);
//				mLocationManager.removeUpdates(locationListenerNetwork);
//			} catch (Exception e) {
//				AppListYPLog.e("GPS回调接口中出现异常:" + e.getMessage());
//			}
//		}
//
//		public void onProviderDisabled(String provider) {
//		}
//
//		public void onProviderEnabled(String provider) {
//		}
//
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//		}
//	};
//
//	/**
//	 * NetWork更新的回调接口
//	 */
//	LocationListener locationListenerNetwork = new LocationListener() {
//		public void onLocationChanged(Location location) {
//			try {
//				AppListYPLog.e("Net回调接口  locationListenerNetwork");
//				locationResult.gotLocation(location);
//				mLocationManager.removeUpdates(this);
//				mLocationManager.removeUpdates(locationListenerGps);
//			} catch (Exception e) {
//				AppListYPLog.e("Net回调接口中出现异常:" + e.getMessage());
//			}
//		}
//
//		public void onProviderDisabled(String provider) {
//		}
//
//		public void onProviderEnabled(String provider) {
//		}
//
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//		}
//	};
//
//	/**
//	 * 获取location对象
//	 *
//	 * @author weijiang204321
//	 *
//	 */
//	private void getLastLocation() {
//		try {
//			// 移除位置更新的回调接口
//			mLocationManager.removeUpdates(locationListenerGps);
//			mLocationManager.removeUpdates(locationListenerNetwork);
//			Location net_loc = null, gps_loc = null;
//			// 如果GPS可用的话就用GPS
//			if (gps_enabled) {
//				gps_loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//			}
//			if (network_enabled) {
//				net_loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//			}
//			// 如果两者都可以用的话，直接选择最快的一个
//			if (gps_loc != null && net_loc != null) {
//				if (gps_loc.getTime() > net_loc.getTime()) {
//					locationResult.gotLocation(gps_loc);
//				} else {
//					locationResult.gotLocation(net_loc);
//				}
//				return;
//			}
//
//			// 如果可用，用gps
//			if (gps_loc != null) {
//				locationResult.gotLocation(gps_loc);
//				return;
//			}
//
//			// 如果可用，用net
//			if (net_loc != null) {
//				locationResult.gotLocation(net_loc);
//				return;
//			}
//
//			// 如果gps和net获取到的值都是null,就借用第三种方式获取location
//
//			locationResult.gotLocation(mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
//		} catch (Exception e) {
//			AppListYPLog.e("获取最新的Location对象的时候出现异常:" + e.getMessage());
//		}
//	}

}