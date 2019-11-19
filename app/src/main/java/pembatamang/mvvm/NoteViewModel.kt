package pembatamang.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    var repository: NoteRepository = NoteRepository(application)

    var allNotesRepo: LiveData<List<Note>> = repository.allNotes

    fun insert(note: Note){
        repository.insert(note)
    }
    fun delete(note: Note){
        repository.delete(note)
    }
    fun update(note: Note){
        repository.updateNote(note)
    }
    fun deleteAll(){
        repository.deleteAll()
    }

}