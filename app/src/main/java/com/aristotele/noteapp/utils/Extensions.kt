package com.aristotele.noteapp.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner


/**
 * روی اسپینر یه اکستنشن میزنیم
 * ----------------------------------------------------------------
 * اسپینر همیشه یه ادابتر داره
 * که کانتکس و لایه و یه لیست میگیره
 * ----------------------------------------------------------------
 * کانتکس رو که میدیم
 * بهش میدیم از لایه خود اندروید میدیم
 * فقط لیست رو از بیرون میارم میدم بهش که همون چیزایی که باید تو اسپینر نمایش داده بشه
 * ----------------------------------------------------------------
 *callback: (String) -> Unit
 * این یه لاندا فانکشن هست
 * که تو ورودی اکستنشن فانکشن میدیم
 * که نوع دیتایی که میخواهیم انتقال بدیم استرینگ هست
 * و این برمیگردونه برامون مقدار کلیک شده را
 * ----------------------------------------------------------------
 *
 *
 */
fun Spinner.setupListWithAdapter(list: MutableList<String>, callback: (String) -> Unit) {

    //ساخت ادابتر اسپینر
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

//    دادن به اسپینر
    this.adapter = adapter


    //این روش دادن کلیک به اسپینره که 2 تا متد باید امپلیمنت بشه اتوماتیک داخلش
    //onItemSelected
    //onNothingSelected

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

//دگت کنید ما میایم با این مزمون میفهمیم روی چی کلیک شده
        //list[p2]
        //اما باید این رو برگردونیم به فرکمنت و اکتیوتی که بفهمیم رو چی کلیک شده که از
        //callback استفاده میکنیم که یه لاندا فانکشن هست

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            callback( list[p2] )
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
//هیچ کاری نمیخواهم انجام بده خالی رد میکنیم
        }
    }
}

/**
 * 1 لیست
 * 2 آیتم مدنظر من
 * item: Any میگذاریم تا هرچی تونست بگیره
 * برای لیست هم میتونیم کلا میوتیبل میکنیم
 * MutableList<out Any>
 * -------------------------------
 * میگه از هر لیستی به هرجنسی رسید
 * شما عملیات زیر رو روش اجرا کن
 * یعنی مادربگرید دیگه خلاصه
 * --
 * ایندکس اون داده رو بهمون میده
 * راهت
 *
 */
fun MutableList<out Any>.getIndexFromList(item: Any): Int {
    var index = 0
    for (i in this.indices) {
        if (this[i] == item) {
            index = i
            break
        }
    }
    return index
}