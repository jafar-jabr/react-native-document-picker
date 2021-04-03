#import "RNDocumentPicker.h"

@implementation RNDocumentPicker

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(doPicking:cb: (RCTResponseSenderBlock)callback)
{
  @try {
     NSNumber *errorCode = [NSNumber numberWithInt:301];
     NSString *errorMessage = @"here is objc 2";
     NSDictionary *errorDict = @{ @"code" : errorCode,
                              @"message" : errorMessage
      };
      NSDictionary *responseDict = @{ @"path" : @"-",
                              @"fileName" : @"-",
                              @"fileSize" : [NSNumber numberWithInt:666],
                              @"type" : @"-"
      };
    // callback(@[errorDict, [NSNull null]]);
     callback(@[errorDict, responseDict]);
  }
  @catch ( NSException *e ) {
      NSNumber *errorCode = [NSNumber numberWithInt:301];
      callback(@[[NSNull null], errorCode]);
  }
}
@end
