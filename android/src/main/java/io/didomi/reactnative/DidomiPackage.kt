package io.didomi.reactnative

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class DidomiPackage : TurboReactPackage() {

    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
        if (name == DidomiModule.NAME) DidomiModule(reactContext) else null

    override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
        mapOf(
            DidomiModule.NAME to ReactModuleInfo(
                DidomiModule.NAME,
                DidomiModule.NAME,
                false, // canOverrideExistingModule
                false, // needsEagerInit
                true,  // hasConstants — DidomiModule.getConstants() exposes event registration names
                false, // isCxxModule
                BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
            )
        )
    }
}
