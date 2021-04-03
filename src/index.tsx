import { NativeModules } from 'react-native';

type successCallback = (response: Record<string, string | number>) => void;
type errorCallback = (errorCode: number, errormessage: string) => void;

// type JafarReactNativeDocumentPickerType = {
//   doPicking(error: errorCallback, response: successCallback): null;
// };
interface RNDocumentPickerInterface {
  doPicking(error: errorCallback, response: successCallback): null;
}

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as RNDocumentPickerInterface;
