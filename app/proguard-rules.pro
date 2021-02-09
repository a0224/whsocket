# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xuhao/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.xuhao.didi.socket.client.**
-dontwarn com.xuhao.didi.socket.common.**
-dontwarn com.xuhao.didi.socket.server.**
-dontwarn com.xuhao.didi.core.**

-keep class com.android.socket.client.** { *; }
-keep class com.android.socket.client.common.** { *; }
-keep class com.android.socket.server.** { *; }
-keep class com.android.didi.core.** { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.android.socket.client.sdk.client.OkSocketOptions$* {
    *;
}
-keep class com.android.socket.server.impl.OkServerOptions$* {
    *;
}