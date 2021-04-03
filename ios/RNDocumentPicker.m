#import "RNDocumentPicker.h"

@implementation RNDocumentPicker

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(doPicking: errorCallback: (RCTResponseSenderBlock)errorCallback
                  successCallback: (RCTResponseSenderBlock)successCallback)
{
  @try {
      NSNumber *errorCode = [NSNumber numberWithInt:301];
      NSString *errorMessage = @"here is objc 1";
      errorCallback(@[errorCode, errorMessage]);
  }

  @catch ( NSException *e ) {
    NSNumber *errorCode = [NSNumber numberWithInt:301];
    NSString *errorMessage = @"here is objc 2";
    errorCallback(@[errorCode, errorMessage]);
  }
}
@end
