#import "RNDocumentPicker.h"
#import <UIKit/UIKit.h>
#import <UniformTypeIdentifiers/UniformTypeIdentifiers.h>

@interface RNDocumentPicker () <UIDocumentPickerDelegate>
@end

@implementation RNDocumentPicker

RCT_EXPORT_MODULE()

- (void)viewDidLoad {
  [super viewDidLoad];
  // Do any additional setup after loading the view.
}

RCT_EXPORT_METHOD(doPicking:(RCTResponseSenderBlock)callback)
{
  @try {
      NSArray<UTType*> * contentTypes = @[[UTType typeWithFilenameExtension:@"txt"],
                                          [UTType typeWithFilenameExtension:@"png"],
                                          [UTType typeWithFilenameExtension:@"heic"],
                                          [UTType typeWithFilenameExtension:@"pdf"]
      ];

      UIDocumentPickerViewController *picker = [[UIDocumentPickerViewController alloc] initForOpeningContentTypes:contentTypes];

      picker.allowsMultipleSelection = YES;
      picker.delegate = self;
      picker.modalPresentationStyle = UIModalPresentationFormSheet;
      [self presentViewController:picker animated:YES completion:nil];
  }
  @catch ( NSException *e ) {
      NSNumber *errorCode = [NSNumber numberWithInt:301];
      NSString *errorMessage = @"here is objc 2";
      NSDictionary *errorDict = @{ @"code" : errorCode,
                                 @"message" : errorMessage
            };
      callback(@[errorDict, [NSNull null]]);
  }
}


- (void)documentPicker:(UIDocumentPickerViewController *)controller didPickDocumentsAtURLs:(NSArray<NSURL *> *)urls {
    for (NSURL* url in urls) {
        [url startAccessingSecurityScopedResource];

        NSNumber *fileSizeValue = nil;
        NSError *fileSizeError = nil;
        [url getResourceValue:&fileSizeValue
                           forKey:NSURLFileSizeKey
                            error:&fileSizeError];

        NSURLSession *session = [NSURLSession sharedSession];
        [[session dataTaskWithURL: url
                  completionHandler:^(NSData *data,
                                      NSURLResponse *response,
                                      NSError *error) {
            NSString *mimeType = [response MIMEType];
            NSDictionary *respinseDict = @{ @"path" : url.absoluteString ? url.absoluteString : @"-",
                                    @"fileName" : url.lastPathComponent ? url.lastPathComponent : @"-",
                                    @"fileSize" : fileSizeValue,
                                    @"type" : mimeType ? mimeType : @"-"
            };

          //callB(@[[NSNull null], respinseDict]);
          }] resume];

        [url stopAccessingSecurityScopedResource];
    }
}

- (void)documentPickerWasCancelled:(UIDocumentPickerViewController *)controller {

}
@end
