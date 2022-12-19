package com.aristotele.noteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * این هم که کلاس دست ساز اپلیکیشن برای hilt هست
 * مکه باید در مانیفست معرفی شود
 */
@HiltAndroidApp
class MyApp:Application() {


}