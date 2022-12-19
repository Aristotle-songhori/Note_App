package com.aristotele.noteapp.utils.di


import android.content.Context
import androidx.room.Room
import com.aristotele.noteapp.data.database.NoteDatabase
import com.aristotele.noteapp.data.model.NoteEntity
import com.aristotele.noteapp.utils.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * مدول hilt برای بخش database در room
 * اینجا میایم 3 تا بخش اصلی دیتابیس رو مشخص میکنیم
 * 1- خود دیتابیس
 * 2-dao
 * 3-entities
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NoteDatabase::class.java, NOTE_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideEntity() = NoteEntity()
}