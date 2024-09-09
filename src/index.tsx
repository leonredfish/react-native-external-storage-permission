import { NativeModules, Platform } from 'react-native';

export  var PermissionFile = Platform.OS === 'android' ? NativeModules.ManageExternalStorage : { checkAndGrantPermission: () => { return true; } }

export default PermissionFile
