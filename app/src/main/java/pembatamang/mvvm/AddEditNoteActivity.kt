package pembatamang.mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {
    companion object{
        val extraTitle : String = "pembatamang.mvvm.title"
        val extraDesc : String = "pembatamang.mvvm.desc"
        val extrapriority : String = "pembatamang.mvvm.priority"
        val extraTitleBarText : String = "pembatamang.mvvm.titleBar"
        val itemID : String = "pembatamang.mvvm.itemID"
    }


    lateinit var titleET: EditText
    lateinit var descET: EditText
    lateinit var picker : NumberPicker
    lateinit var button : MaterialButton
    var edit = false
    private var itemIDval : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        titleET = mtitle
        descET = desc
        picker = mpicker
        button = save
        picker.minValue = 1
        picker.maxValue = 10

        if(intent.hasExtra(itemID)){
            title = "Edit Note"
            edit = true
            itemIDval = intent.getIntExtra(itemID,0)
            titleET.setText(intent.getStringExtra(extraTitle))
            descET.setText(intent.getStringExtra(extraDesc))
            picker.value = intent.getIntExtra(extrapriority,-1)
        }else{
            title = "Add Note"

        }
        button.setOnClickListener {
            if(titleET.text.isNullOrEmpty() || descET.text.isNullOrEmpty()) {
               Toast.makeText(this,"please fill both fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val title = titleET.text.toString()
            val desc = descET.text.toString()
            val priority = picker.value


            val intent = Intent()
            intent.putExtra(extraTitle,title)
            intent.putExtra(extraDesc,desc)
            intent.putExtra(extrapriority,priority)
            if(edit){
                intent.putExtra(itemID,itemIDval)
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

    }
}
