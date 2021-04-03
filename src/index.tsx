import { NativeModules } from 'react-native';

type callback = (
  error: { code: number; message: string } | null,
  response: {
    path: string;
    fileName: string;
    type: string;
    fileSize: number;
  } | null
) => void;

interface RNDocumentPickerInterface {
  doPicking(cb: callback): null;
}

const { RNDocumentPicker } = NativeModules;

export default RNDocumentPicker as RNDocumentPickerInterface;
