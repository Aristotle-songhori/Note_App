package com.aristotele.noteapp.data.repository

import com.aristotele.noteapp.data.database.NoteDao
import com.aristotele.noteapp.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

//    میره یه آبجکت جدید رو ذخیره میکنه
    suspend fun saveNote(entity: NoteEntity) = dao.saveNote(entity)

    //میره یه ابجکت خاصی رو ادیت میکنه
    suspend fun updateNote(entity: NoteEntity) = dao.updateNote(entity)


    //این فقط یه گزینه برمیگردونه
    fun getNote(id: Int) = dao.getNote(id)
}