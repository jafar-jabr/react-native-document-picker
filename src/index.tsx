import { NativeModules } from 'react-native';

type JafarReactNativeDocumentPickerType = {
  multiply(a: number, b: number): Promise<number>;
};

const { JafarReactNativeDocumentPicker } = NativeModules;

export default JafarReactNativeDocumentPicker as JafarReactNativeDocumentPickerType;
