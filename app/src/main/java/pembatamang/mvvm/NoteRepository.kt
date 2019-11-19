package pembatamang.mvvm

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import pembatamang.mvvm.database.Note
import pembatamang.mvvm.database.NoteDao
import pembatamang.mvvm.database.NoteDatabase

class NoteRepository(application: Application) {
    var noteDao: NoteDao? = null
    lateinit var allNotes: LiveData<List<Note>>
    init {
        val noteDatabase = NoteDatabase.getInstance(application)
        noteDao = noteDatabase.getNoteDao()
        allNotes = noteDao!!.getAllNotes()

    }
    /**
     * CouroutineScope has three ways of specifying the thread where the operation will take place namely IO, Main and Default
     * for more info on coroutines see this https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
     */
    fun insert(note: Note) {
     CoroutineScope(IO).launch {
         noteDao!!.insert(note)
     }
    }

    fun delete(note: Note) {
        CoroutineScope(IO).launch {
            noteDao!!.delete(note)
        }
    }
    fun updateNote(note: Note){
        CoroutineScope(IO).launch {
            noteDao!!.update(note)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            noteDao!!.deleteAll()
        }
    }

}