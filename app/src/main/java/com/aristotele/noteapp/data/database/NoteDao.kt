package com.aristotele.noteapp.data.database


import androidx.room.*
import com.aristotele.noteapp.data.model.NoteEntity
import com.aristotele.noteapp.utils.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * بخش dao که میدونیم
 * سینکس های room رو نگه میداره
 * اگر خروجی بده حتما از flow و جریان اطلاغات استفاده بشه
 */
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(entity: NoteEntity)

    @Delete
    suspend fun deleteNote(entity: NoteEntity)

    @Update
    suspend fun updateNote(entity: NoteEntity)

    @Query("SELECT * FROM $NOTE_TABLE")
    fun getAllNotes(): Flow<    MutableList<NoteEntity> >

    @Query("SELECT * FROM $NOTE_TABLE WHERE id == :id")
    fun getNote(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE priority == :priority")
    fun filetNote(priority: String): Flow<MutableList<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE title LIKE '%' || :title || '%' ")
    fun searchNote(title: String): Flow<MutableList<NoteEntity>>
}