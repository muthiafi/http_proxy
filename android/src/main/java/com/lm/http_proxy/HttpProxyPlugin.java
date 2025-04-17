package com.lm.http_proxy;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** HttpProxyPlugin */
public class HttpProxyPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "com.lm.http.proxy";
  
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    setupChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), CHANNEL_NAME);
  }

  private void setupChannel(io.flutter.plugin.common.BinaryMessenger messenger, String channelName) {
    channel = new MethodChannel(messenger, channelName);
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "getProxyHost":
        result.success(getProxyHost());
        break;
      case "getProxyPort":
        result.success(getProxyPort());
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private static String getProxyHost() {
    return System.getProperty("http.proxyHost");
  }

  private static String getProxyPort() {
    return System.getProperty("http.proxyPort");
  }
}
