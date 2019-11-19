package pembatamang.mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity(), mAdapter.RecyclerClick {
    override fun click(itemPos: Int) {
        val note = adapter.getItemAt(itemPos)
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra(AddNoteActivity.extraTitleBarText, "Add Note Activity")
        intent.putExtra(AddNoteActivity.extraTitle, note!!.title)
        intent.putExtra(AddNoteActivity.extraDesc,note.description)
        intent.putExtra(AddNoteActivity.itemID,note.id)
        intent.putExtra(AddNoteActivity.extrapriority,note.priority)
        startActivityForResult(intent, editRreqCode)
    }


    lateinit var noteViewModel: NoteViewModel
    lateinit var adapter: mAdapter
    val addReqCode = 120
    val editRreqCode = 121
    var edited = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        val recyclerView = recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = mAdapter()
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        noteViewModel.allNotesRepo.observe(this, Observer<List<Note>> {
            adapter.submitList(it)
            Toast.makeText(this, if(edited) "edited" else "onchanged", Toast.LENGTH_LONG).show()
        })
        val mIth = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                return false// true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val note = adapter.getItemAt(viewHolder.adapterPosition)
                noteViewModel.delete(note!!)
                // remove from adapter
            }
        })
        mIth.attachToRecyclerView(recyclerView)

        fab.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra(AddNoteActivity.extraTitleBarText, "Add Note Activity")
            startActivityForResult(intent, addReqCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addReqCode && resultCode == Activity.RESULT_OK) {

            val title = data!!.getStringExtra(AddNoteActivity.extraTitle)
            val desc = data.getStringExtra(AddNoteActivity.extraDesc)
            val priority = data.getIntExtra(AddNoteActivity.extrapriority, -1)
            Log.i("mvvm", "$title  $desc $priority")
            val note = Note(title!!, desc!!, priority)
            edited = false
            noteViewModel.insert(note)

        } else if (requestCode == editRreqCode && resultCode == Activity.RESULT_OK) {
            val itemid = data!!.getIntExtra(AddNoteActivity.itemID,-1)
            val title = data.getStringExtra(AddNoteActivity.extraTitle)
            val desc = data.getStringExtra(AddNoteActivity.extraDesc)
            val priority = data.getIntExtra(AddNoteActivity.extrapriority, -1)
            val note = Note(title!!, desc!!, priority)
            note.id = itemid
            edited = true
            noteViewModel.update(note)


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                noteViewModel.deleteAll()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
