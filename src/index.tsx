import { NativeModules, Platform } from 'react-native';

export  var PermissionFile = Platform.OS === 'android' ? NativeModules.PermissionFile : { checkAndGrantPermission: () => { } }

export default PermissionFile
