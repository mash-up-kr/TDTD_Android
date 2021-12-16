package com.tdtd.presentation.utils

import android.app.Activity
import android.content.Context

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tdtd.presentation.BuildConfig
import com.tdtd.presentation.R
import timber.log.Timber

object AdsExt {
    private var mInterstitialAd: InterstitialAd? = null

    fun initAds(context: Context) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            setAdUnitId(context),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.v(adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Timber.v("Ad was loaded")
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Timber.v("Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Timber.v("Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Timber.v("Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
    }

    private fun setAdUnitId(context: Context): String {
        return if (BuildConfig.DEBUG) context.getString(R.string.banner_ad_unit_id_for_test)
        else context.getString(R.string.banner_ad_unit_id_for_real)
    }

    fun showAds(activity: Activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Timber.v("The interstitial ad wasn't ready yet.")
        }
    }
}