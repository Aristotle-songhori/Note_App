package com.aristotele.noteapp.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aristotele.noteapp.R
import com.aristotele.noteapp.data.model.NoteEntity
import com.aristotele.noteapp.databinding.ActivityMainBinding
import com.aristotele.noteapp.ui.main.note.NoteAdapter
import com.aristotele.noteapp.ui.main.note.NoteFragment
import com.aristotele.noteapp.utils.*
import com.aristotele.noteapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * اکتیوتی اصلی
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding رو کپسوله میکنیم
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding



    //تزریق آدابتر ریسایکلر ویو
    @Inject
    lateinit var notesAdapter: NoteAdapter

    //تزریق انتیتی
    @Inject
    lateinit var noteEntity: NoteEntity

    //Other
    //اینشیالایز ویو مدل این اکتیوتی
    private val viewModel: MainViewModel by viewModels()

    //پارامتر تنظیمی لازم
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //پُر کردن بایندینگ
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        //InitViews
        binding?.apply {



            //Support toolbar
            // ست کردن اکشن بار بالا
            //notesToolbar اسم آیدی تولباره در فایل xml
            setSupportActionBar(notesToolbar)




            //Note fragment
            // دکمه بعلاوه پایین صفحه که میزنیم یه فرگمنتی رو میاره بالا
            // ما یه فرگمنت داریم از پایین میاد بالا به  اسم  NoteFragment
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }




            //Get data
            // بیا برو از دیتابیس همه داده ها رو بگیر
            viewModel.getAllNotes()
            // وقتی بالایی اجرا میشه اتوماتیک مقدار notesData رو آپدیت میکنه و این پایینی کارمی افته
            viewModel.notesData.observe(this@MainActivity) {
                //مرحله 1 نمایش ریسایکلر و یا نمایش عکس خالی بودن
                showEmpty(it.isEmpty)

                // این لیستی که میرسه رو بده به آدابتر تا مرحله بعد آدابتر رو بدیم به ریسایکلر ویو
                it.data?.let { it1 -> notesAdapter.setData(it1) }


                //این ریسایکلر ویو هست و 2 مقدار توش رو مقدار دهی میکنیم
                //یکی مدل نمایش داده ها در 2 ستون و زیر هم و یکی هم آدابتر
                noteList.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = notesAdapter
                }
            }




            /**
             * این تشخیص میده رو کدوم دکمه داخل اون بالا کلیک شده
             *  این میاد فیلتر اضافه میکنه به اون قسمت بالا
             *
             */

            //Filter
            notesToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionFilter -> {
                        //این فانکشن میاد یک دیالوگ با 4 گزینه میسازه
                        priorityFilter()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }



            //Clicks
            /**
             * این کلیک روی اون دکه پایینی هست
             * -------------------------------
             * یه متغیر تعریف میکنیم که فرگمنت بریزیم روش
             * noteFragment
             * -------------------------------
             * یه متغیرم به عنوان باندل تعریف میکنیم که باندل و آیدی بدیم بهش
             * bundle
             * در واقع اطلاعاتمون دادیم و ریختیم تو باندل
             * bundle.putInt(BUNDLE_ID, entity.id)
             * ----------------
             *بعد اون باندل دادیم به فرگمنت
             * noteFragment.arguments = bundle
             *-------------------------------
             * و بعد اون فرگمنت رو نمایش دادیم
             */
            notesAdapter.setOnItemClickListener { entity, type ->
                when (type) {
                    EDIT -> {
                        val noteFragment = NoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID, entity.id)
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager, NoteFragment().tag)
                    }
                    DELETE -> {
                        noteEntity.id = entity.id
                        noteEntity.title = entity.title
                        noteEntity.desc = entity.desc
                        noteEntity.category = entity.category
                        noteEntity.priority = entity.priority
                        viewModel.deleteNote(noteEntity)
                    }
                }
            }
        }
    }

    private fun showEmpty(isShown: Boolean) {
        binding?.apply {
            if (isShown) {
                emptyLay.visibility = View.VISIBLE
                noteList.visibility = View.GONE
            } else {
                emptyLay.visibility = View.GONE
                noteList.visibility = View.VISIBLE
            }
        }
    }


    /**
     * این برای نمایش اون سرچ بالای صفحه درست شده
     * در همه برنامه هم کپی پیست کرده
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //این سرچ یک کلمه هست و زدن دکمه سرچ
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

//            این در عوض با نوشتن هر کلمه سرچ رو میکنه
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getSearchNotes(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    /**
     * این رو هم برای نمایش اون بخش فیلتر راه انداخته
     * و نوشته و فقط کپی پیست کرد اینجا
     */
    private fun priorityFilter() {

//        یه دیالوگ میسازیم با 4 تا رادیو باتن
        val builder = AlertDialog.Builder(this)
// این چهار تا رو میدیم بهش
        val priority = arrayOf("All", HIGH, NORMAL, LOW)

//        حالا میایم کلیک رو تا ببینیم روی کدوم گزینه کلیک و انتخواب میشه
//        اگر همه باشه میره همه رو میگیره اگر نه یکی از 3 گزینه بعدی
        builder.setSingleChoiceItems(priority, selectedItem) { dialog, item ->
            when (item) {
                0 -> {
                    viewModel.getAllNotes()
                }
                in 1..3 -> {
                    viewModel.getFilterNotes(priority[item])
                }
            }
            selectedItem = item
            dialog.dismiss()
        }
//        ساخت دیالوگ
        val dialog: AlertDialog = builder.create()
//        نمایشش
        dialog.show()
    }


//    اینم تکلیفش معلومه
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}