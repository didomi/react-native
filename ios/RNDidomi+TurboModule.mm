#ifdef RCT_NEW_ARCH_ENABLED

#import <React/RCTBridgeModule.h>
#import <ReactCommon/RCTTurboModule.h>
#import "RNDidomiSpec/RNDidomiSpec.h"

@interface Didomi (TurboModule) <NativeDidomiSpec>
@end

@implementation Didomi (TurboModule)

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
  return std::make_shared<facebook::react::NativeDidomiSpecJSI>(params);
}

@end

#endif
