module.exports = {
	AD_POSITION: {
        TOP_LEFT: 0,
        TOP_CENTER: 1,
		TOP_RIGHT: 2,
		LEFT: 3,
		CENTER: 4,
		RIGHT: 5,
		BOTTOM_LEFT: 6,
		BOTTOM_CENTER: 7,
		BOTTOM_RIGHT: 8
    },
	AD_SIZE: {
        BANNER_SMALL: 1,
		BANNER_LARGE: 2,
		BANNER_MEDIUM_RECTANGLE: 3,
        BANNER_SMART: 4,
    },
    initialize: function(appID) {
        cordova.exec(
			null,
			null,
            'Adivery',
            'initialize',
            [ appID ]
        ); 
    },
    setLoggingEnabled: function(logEnabled) {
        cordova.exec(
            null,
            null,
            "Adivery",
            "setLoggingEnabled",
            [ logEnabled ]
        );
    },
    requestNativeAd: function(zoneId) {
        cordova.exec(
            null,
            null,
            "Adivery",
            "requestNativeAd",
            [ zoneId ]
        )
    },
    recordNativeAdImpression: function() {
        cordova.exec(
            null,
            null,
            "Adivery",
            "recordNativeAdImpression",
        )
    },
    recordNativeAdClick: function() {
        cordova.exec(
            null,
            null,
            "Adivery",
            "recordNativeAdClick",
        )
    },
    createBanner: function(zoneId, position, type) {
        cordova.exec(
			null,
			null,
            'Adivery',
            'createBanner',
            [ zoneId, position, type ]
        ); 
    },
    createBannerAtXY: function(zoneId, x, y, type) {
        cordova.exec(
			null,
			null,
            'Adivery',
            'createBannerAtXY',
            [ zoneId, x, y, type ]
        ); 
    },
    removeBanner: function() {
        cordova.exec(
			null,
			null,
            'Adivery',
            'removeBanner',
            []
        ); 
    },
    showBanner: function() {
        cordova.exec(
			null,
			null,
            'Adivery',
            'showBanner',
            []
        ); 
    },
    hideBanner: function() {
        cordova.exec(
			null,
			null,
            'Adivery',
            'hideBanner',
            []
        ); 
    },
    prepareInterstitialAd: function (zoneId) {
        cordova.exec(
            null,
            null,
            'Adivery',
            'prepareInterstitialAd',
            [ zoneId ]
        );
    },
    prepareRewardedAd: function (zoneId) {
        cordova.exec(
            null,
            null,
            'Adivery',
            'prepareRewardedAd',
            [ zoneId ]
        );
    },
    prepareAppOpenAd: function (zoneId) {
        cordova.exec(
            null,
            null,
            "Adivery",
            "prepareAppOpenAd",
            [ zoneId ]
        )
    },
    showAd: function (zoneId) {
        cordova.exec(
            null,
            null,
            'Adivery',
            'showAd',
            [ zoneId ]
        );
    },
    showAppOpenAd: function(zoneId) {
        cordova.exec(
            null,
            null,
            "Adivery",
            "showAppOpenAd",
            [ zoneId ]
        )
    }
};