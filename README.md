

## Adivery Plugin 

Cordova / PhoneGap Plugin for Adivery.

## Contents

1. [Description](#description)
2. [Features](#features)
3. [Demo](#quick-demo)
4. [Quick Start](#quick-start)
5. [Installation](#installation)
6. [Usage](#usage)
7. [API](#api)
8. [Wiki and Docs](#wiki-and-docs)
9. [Screenshots](#screenshots)
10. [License](#license)
11. [Credits](#credits)

## Description

This Cordova / PhoneGap plugin enables displaying mobile Ads with single line of javascript code. Designed for the use in HTML5-based cross-platform hybrid games and other applications.

## Features

Platforms supported:
- [ ] Amazon-FireOS, via Android SDK (part of Google Play service)
- [x] Android, via Android SDK (part of Google Play service)
- [ ] iOS
- [ ] Windows Phone

Ad Types:
- [x] Banner
- [x] Interstitial
- [x] Reward Video
- [x] Native Ads
- [x] Native Ads Advanced
- [x] AppOpen Ads

## Quick Demo

To quickly test this plugin and see ads on your devices use commands bellow.

```bash
    # install cordova CLI
    [sudo] npm install cordova -g

    # install a small utility to run all the commands for you
    [sudo] npm install plugin-verify -g

    # Demo: run adivery demo with sample index.html
    plugin-verify adivery-cordova-plugin
```

## Quick start
```bash
	# create a demo project
    cordova create test1 com.adivery.example Test1
    cd test1
    cordova platform add android

    # now add the plugin, cordova CLI will handle dependency automatically
    cordova plugin add adivery-cordova-plugin

    # now remove the default www content, copy the demo html file to www
    rm -r www/*;
    cp plugins/adivery-cordova-plugin/test/* www/;

	# now build and run the demo in your device or emulator
    cordova prepare; 
    cordova run android;
    # or import into eclipse
```

## Installation

* If use with Cordova CLI:
```bash
cordova plugin add adivery-cordova-plugin
```

* If use with PhoneGap Build:
```xml
<plugin name="adivery-cordova-plugin" source="npm"></plugin>
```

Notice:
* If build locally using ```adivery-cordova-plugin```, to avoid build error, you need install some extras in Android SDK manager (type ```android sdk``` to launch it):
![android extra](https://cloud.githubusercontent.com/assets/2339512/8176143/20533ec0-1429-11e5-8e17-a748373d5110.png)

## Usage

Show Mobile Ad with single line of javascript code.

### Initialization

Create new application, in [Adivery portal](http://www.adivery.com/), then write it in your javascript code and replace AppID with the ID generated for your application in Adivery panel.

```javascript
Adivery.initialize('AppID');
```

### Banner ads

To create banner ads in your application use code bellow.

```javascript
// it will display small banner at top center, using the default options
Adivery.createBanner(zoneId, Adivery.AD_POSITION.TOP_CENTER, Adivery.AD_SIZE.BANNER_SMALL);
```

### Interstitial

To prepare Interstitial ads on your application run code bellow.  

```javascript
// prepare and load interstitial ad resource in background, e.g. at begining of game level
Adivery.prepareInterstitialAd(zoneId);
```

* :alert: Adivery will automatically request next ad when current ad is displayed and there is no need to call `prepareInterstitialAd` again.

To get events related to Interstitial ads use code bellow.

```javascript
document.addEventListener('onInterstitialAdLoaded', function(event) {
	if (typeof event.originalEvent !== 'undefined') event = event.originalEvent;
    var data = eevent.detail || event.data || event;

	console.log("Adivery InterstitialAd Loaded", data.placementId);
});
```

### Rewarded

To prepare Rewarded ads on your application use code bellow.

```javascript
// prepare and load Rewarded ad resource in background, e.g. at begining of game level
Adivery.prepareRewardedAd(zoneId);
```

* :alert: Adivery will automatically request next ad when current ad is displayed and there is no need to call `prepareRewardedAd` again.

To get events related to rewarded ads use code bellow.

```javascript
document.addEventListener('onRewardedAdClosed', function(event) {
	if (typeof event.originalEvent !== 'undefined') event = event.originalEvent;
    var data = eevent.detail || event.data || event;

	console.log("Adivery RewardedAd closed, isRewared:", data.isRewarded);
});
```

### AppOpen

To prepare AppOpen ads on your application run code bellow.  

```javascript
// prepare and load interstitial ad resource in background, e.g. at begining of game level
Adivery.prepareAppOpenAd(zoneId);
```

* :alert: Adivery will automatically request next ad when current ad is displayed and there is no need to call `prepareInterstitialAd` again.

To get events related to Interstitial ads use code bellow.

```javascript
document.addEventListener('onAppOpenAdLoaded', function(event) {
	if (typeof event.originalEvent !== 'undefined') event = event.originalEvent;
    var data = eevent.detail || event.data || event;

	console.log("Adivery AppOpen Loaded", data.placementId);
});
```

### Displaying Ads

To display ads just call `showAd` or `showAppOpenAd` depending on if you want to display interstitial, rewareded ads or AppOpen ads.

```javascript
Adivery.showAd('ZoneId');

Adivery.showAppOpenAd('ZoneId');
```


### Native Ads

To get a Native ad use `requestNativeAd` function to load a native ad.

```javascript
Adivery.requestNativeAd('ZoneId`);
```

To get loaded ad and begin displaying it, use this code.

```javascript
document.addEventListener('nativeAdLoaded', function(event) {
	if (typeof event.originalEvent !== 'undefined') event = event.originalEvent;
    var data = eevent.detail || event.data || event;

	console.log(data['headline']);
	console.log(data['description']);
	console.log(data['advertiser']);
	console.log(data['callToAction']);
	console.log(data['icon']);
	console.log(data['image']);
});
```

### Handling impression and click of Native ads

To record Impression of native ad in your application use this code.

Please have in mind to call this code only and only if Ad is displayed to the user.

```javascript
Adivery.recordNativeAdImpression();
```

Finally to record click of native ads, use this code.

Please have in mine, calling this function will navigate user to market or web browser depending on what ad is dispaying, so call it when user actually clicks on CTA button.

```javascript
Adivery.recordNativeAdClick();
```


## API

Methods:
```javascript
// initialize plugin
initialize(AppID);
setLoggingEnabled(logEnabled);

// use banner
createBanner(zoneId, position, size);
createBannerAtXY(zoneId, x, y, size);
removeBanner();
showBanner();
hideBanner();

// use native
requestNativeAd(zoneId);
recordNativeAdImpression();
recordNativeAdClick();

// use interstitial/rewarded
prepareInterstitialAd(zoneId);
prepareRewardedAd(zoneId);
showAd(zoneId);

// use app open ads
prepareAppOpenAd(zondId);
showAppOpenAd(zoneId);
```

Events:
```javascript
// onAdLoaded
// onAdRewarded
// onShowFailed
document.addEventListener('onBannerAdClicked', function(e){
    // handle the event
	// no data will be passed to this event
});
document.addEventListener('onBannerAdLoaded', function(e){
    // handle the event
	// no data will be passed to this event
});
document.addEventListener('onBannerAdShown', function(e){
    // handle the event
	// no data will be passed to this event
});
document.addEventListener('nativeAdClicked', function(e){
    // handle the event
	// no data will be passed to this event
});
document.addEventListener('nativeAdLoadFailed', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.message);
});
document.addEventListener('nativeAdLoaded', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
    console.log("native ad loaded", data['headline']);
    console.log("native ad loaded", data['advertiser']);
    console.log("native ad loaded", data['description']);
    console.log("native ad loaded", data['callToAction']);
    console.log("native ad loaded", data['icon']);
    console.log("native ad loaded", data['image']);
});
document.addEventListener('nativeAdShown', function(e){
    // handle the event
	// no data will be passed to this event
});
document.addEventListener('onAppOpenAdClicked', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onAppOpenAdClosed', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onAppOpenAdLoaded', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onAppOpenAdShown', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onInterstitialAdClicked', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onInterstitialAdClosed', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onInterstitialAdLoaded', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onInterstitialAdShown', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onRewardedAdClicked', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onRewardedAdClosed', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
    consoleLog("onAdClosed, reward: " + data.isRewarded);
});
document.addEventListener('onRewardedAdLoaded', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onRewardedAdShown', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
});
document.addEventListener('onError', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.placement);
	consoleLog("onAdLoaded. Type: " + data.message);
});
```

## License

You can use the plugin for free.

## Credits

This project is created and maintained by Milad Mohammadi and then updated to latest fearures by Adivery Team.

More Cordova/PhoneGap plugins by Milad Mohammadi, [find them in plugin registry](http://plugins.cordova.io/#/search?search=miladesign), or [find them in npm](https://www.npmjs.com/~miladesign).

More about [Adivery](https://adivery.com)