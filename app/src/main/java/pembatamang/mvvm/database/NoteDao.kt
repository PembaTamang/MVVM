package pembatamang.mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    /**
     * the suspend keyword makes a function useable by  coroutines
     * for more info on coroutines see this https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
     *
     */
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from note_table order by priority desc")
    fun getAllNotes():LiveData<List<Note>>  // making this a suspend function gave me a ton of errors so I decided not to ues it for now.

    @Query("delete from note_table ")
    suspend fun deleteAll()

}