import {NativeModules} from 'react-native';
/**
 * refresh(String msg, Callback successCallback,Callback errorCallback)
 * msg:提示内容
 * successCallback：成功回调:{refreshMsg,responseTime,successResponseText}
 * 
 * 
 * errorCallback:失败回调{errorResponseText}
 *  
 */


export default NativeModules.RefreshModule;

