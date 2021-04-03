import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
// @ts-ignore
import RNDocumentPicker from '@jafar/react-native-document-picker';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    RNDocumentPicker.doPicking((documentData: any) => {
      setResult(documentData);
      console.warn(JSON.stringify(documentData));
    });
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
