# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wupeitao/Downloads/adt-bundle-mac-x86_64-20131030/sdk/tools/proguard/proguard-android.txt
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
-ignorewarnings
-dontwarn
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}


#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}
#-libraryjars libs/alipay.jar
-dontwarn com.alipay.**
-keep class com.alipay.**{*;}
-dontwarn com.ta.utdid2.**
-keep class com.ta.utdid2.**{*;}

#-libraryjars libs/libammsdk.jar
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}

-dontwarn net.soureceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.** { *;}
-dontwarn demo.**
-keep class demo.** { *;}

#
#baidu location
-dontwarn com.baidu.**
-keep class com.baidu.**{*;}

#fastjson-1.1.40.jar
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}

#glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}

#json-simple-1.1.1.jar
-dontwarn org.json.simple.**
-keep class org.json.simple.**{*;}




-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class [com.cityant.main].R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[com.cityant.main] values();
    public static ** valueOf(java.lang.String);
}
#友盟
-dontwarn com.umeng.**
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.iwater.R$*{
public static final int *;
}

-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}