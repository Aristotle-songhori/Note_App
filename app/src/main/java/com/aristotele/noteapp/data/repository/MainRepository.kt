package com.aristotele.noteapp.data.repository

import com.aristotele.noteapp.data.database.NoteDao
import com.aristotele.noteapp.data.model.NoteEntity
import javax.inject.Inject

/**
 * repository یا مخزن هست که همیشه قبل dao هست
 * بنابر این dao رو با hilt بهش میدیم
 * و داده ها رو میفرستیم به dao
 */
class MainRepository @Inject constructor(private val dao: NoteDao) {
    //یه لیست فلو میوتیبل از داده ها برمیگردونه
    fun allNotes() = dao.getAllNotes()

    //یه استرینگ میگیره و میفرسته تو دایو و یه لیست از داده ها برمیگردونه که اون حرف یا حروف رو دارن
    fun searchNotes(search: String) = dao.searchNote(search)

    //میاد میره فقط یک دسته بندی خاص رو میاره نمایش میده
    fun filterNotes(filter: String) = dao.filetNote(filter)

    // اینم که مشخصه میره دقیقا یه کوئری رو پاک میکنه
    suspend fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
}