package com.aristotele.noteapp.utils


/**
 * این چی ؟
 * این یه کلاسی خودمون مینویسیم برای چک کردن وضعیت داده های رسیده از دیتابیس
 * ببینه خالی /پُره / چی این ؟
 * -------------------------------
 * val status: Status
 * val data: T? = null // با اینترنت وقتی هست اون دیتا نمرسه میشه مثلا کلا دیتانداریم
 * val isEmpty: Boolean
 * -------------------------------
 * بخش ارور و ... هم نداریم
 * -------------------------------
 * آموز بخش فلو رو ببینید یک مثال کامل دارره خیلی خوبه په
 * فکر کنم بخش رتروفیت باشه
 *
 */


data class DataStatus<out T>(val status: Status, val data: T? = null, val isEmpty: Boolean) {

    enum class Status {
        SUCCESS
    }

    companion object {
        fun <T> success(data: T?, isEmpty: Boolean): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, isEmpty)
        }
    }
}