package pembatamang.mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from note_table order by priority desc")
     fun getAllNotes():LiveData<List<Note>>

    @Query("delete from note_table ")
    fun deleteAll()

}