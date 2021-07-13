# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/24.3.3/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# Keep the Didomi events classes
-keep class io.didomi.sdk.apiEvents.** { *; }
-keep enum io.didomi.sdk.apiEvents.** { *; }

# Prevent errors while using gson
-keepattributes Signature
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Prevent a bug in some proguard versions
-keep,allowshrinking,allowobfuscation enum com.iabtcf.v2.RestrictionType
-keep,allowshrinking,allowobfuscation enum io.didomi.sdk.ConsentStatus
