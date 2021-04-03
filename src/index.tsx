import { NativeModules } from 'react-native';

type callback = () => void;

type JafarReactNativeDocumentPickerType = {
  doPicking(): callback;
};

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as JafarReactNativeDocumentPickerType;
