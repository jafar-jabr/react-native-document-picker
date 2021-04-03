#import "RNDocumentPicker.h"

@implementation RNDocumentPicker

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(doPicking: callback: (RCTResponseSenderBlock)callback)
{
  @try {
     NSNumber *errorCode = [NSNumber numberWithInt:301];
     callback(@[[NSNull null], errorCode]);
  }
  @catch ( NSException *e ) {
      NSNumber *errorCode = [NSNumber numberWithInt:301];
      callback(@[[NSNull null], errorCode]);
  }
}
@end
