import { NativeModules } from 'react-native';

type JafarReactNativeDocumentPickerType = {
  multiply(a: number, b: number): Promise<number>;
};

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as JafarReactNativeDocumentPickerType;
