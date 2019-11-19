package pembatamang.mvvm

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NoteRepository(application: Application) {
    var noteDao: NoteDao? = null
    var allNotes: LiveData<List<Note>>
    init {
        val noteDatabase = NoteDatabase.getInstance(application)
        noteDao = noteDatabase.getNoteDao()
        allNotes = noteDao!!.getAllNotes()

    }

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