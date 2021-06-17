

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
- [x] Interstitial (picture, video), highly recommended. :fire:
- [x] Reward Video, highly recommended. :fire:
- [ ] Native Ads (on roadmap)
- [ ] Native Ads Advanced (on roadmap)

## Quick Demo

Wanna quickly see the mobile ad on your simulator or device? Try the following commands.

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

Step 1: Create new application, in [Adivery portal](http://www.adivery.com/), then write it in your javascript code.

```javascript
Adivery.initialize('AppID');
```

Step 2: Want cheap and basic banner? single line of javascript code.

```javascript
// it will display small banner at top center, using the default options
Adivery.createBanner(zoneId, Adivery.AD_POSITION.TOP_CENTER, Adivery.AD_SIZE.BANNER_320x50);
```

Step 3: Want interstitial Ad to earn more money ? Easy, 2 lines of code. 

```javascript
// prepare and load interstitial ad resource in background, e.g. at begining of game level
Adivery.requestInterstitialAd(zoneId);

// prepare and load rewarded ad resource in background, e.g. at begining of game level
Adivery.requestRewardedAd(zoneId);

// show the interstitial or rewarded ad later, e.g. at end of game level
Adivery.showAd();
```

## API

Methods:
```javascript
// initialize plugin
initialize(AppID);

// use banner
createBanner(zoneId, position, size);
createBannerAtXY(zoneId, x, y, size);
removeBanner();
showBanner();
hideBanner();

// use interstitial/rewarded
requestInterstitialAd(zoneId);
requestRewardedAd(zoneId);
showAd();
```

Events:
```javascript
// onAdLoaded
// onAdRewarded
// onShowFailed
document.addEventListener('onAdLoaded', function(e){
    // handle the event
	if (typeof e.originalEvent !== 'undefined') e = e.originalEvent;
	var data = e.detail || e.data || e;
	consoleLog("onAdLoaded. Type: " + data.adType);
});
```

## Wiki and Docs

Quick start, simply copy & paste:
* [Example Code](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.0-Quick-Start-Example-Code)
* [Complete Demo index.html](https://github.com/vinoosir/adivery-cordova-plugin/blob/master/test/index.html)

API Reference:
* [API Overview](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.1-API-Overview)
* [How to Use Banner](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.2-Methods-for-Banner)
* [How to Use Interstitial](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.3-Methods-for-Interstitial)
* [How to Use Rewarded](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.4-Methods-for-Rewarded)
* [How to Handle Ad Events](https://github.com/vinoosir/adivery-cordova-plugin/wiki/1.5-Events)

Other Documentations:
* [ChangeLog](https://github.com/vinoosir/adivery-cordova-plugin/wiki/ChangeLog)
* [FAQ](https://github.com/vinoosir/adivery-cordova-plugin/wiki/FAQ)

## Screenshots

Android Banner | Android Interstitial
-------|---------------
![ScreenShot](https://raw.githubusercontent.com/VinoosIr/adivery-cordova-plugin/master/docs/screenshot_banner.png) | ![ScreenShot](https://raw.githubusercontent.com/VinoosIr/adivery-cordova-plugin/master/docs/screenshot_interstitial.png)


## License

You can use the plugin for free.

## Credits

This project is created and maintained by Milad Mohammadi.

More Cordova/PhoneGap plugins by Milad Mohammadi, [find them in plugin registry](http://plugins.cordova.io/#/search?search=miladesign), or [find them in npm](https://www.npmjs.com/~miladesign).

Project outsourcing and consulting service is also available. Please [contact us](mailto:rezagah.milad@gmail.com) if you have the business needs.