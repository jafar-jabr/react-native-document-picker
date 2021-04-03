import { NativeModules } from 'react-native';

type callback = (response: Record<string, string | number>) => void;

type JafarReactNativeDocumentPickerType = {
  doPicking(): callback;
};

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as JafarReactNativeDocumentPickerType;
