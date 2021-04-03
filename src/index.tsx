import { NativeModules } from 'react-native';

type callback = (response: Record<string, string | number>) => void;

type JafarReactNativeDocumentPickerType = {
  doPicking(cb: callback): null;
};

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as JafarReactNativeDocumentPickerType;
