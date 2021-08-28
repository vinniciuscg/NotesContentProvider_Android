package com.example.applicationcontentprovider

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.applicationcontentprovider.database.NotesDatabaseHelper.Companion.DESCRIPTION_NOTES
import com.example.applicationcontentprovider.database.NotesDatabaseHelper.Companion.TITLE_NOTES
import com.example.applicationcontentprovider.database.NotesProvider.Companion.URI_NOTES

class NoteDatailFragment: DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var noteEditTitle: EditText
    private lateinit var noteEditDescription: EditText
    private var id: Long = 0

    companion object{
        private const val EXTRA_ID = "id"
        fun newInstance(id: Long): NoteDatailFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val noteFragment = NoteDatailFragment()
            noteFragment.arguments = bundle
            return noteFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.note_detail, null)

        noteEditTitle = view?.findViewById(R.id.note_edt_title) as EditText
        noteEditDescription = view?.findViewById(R.id.note_edt_description) as EditText

        var newNote = true
        if(arguments != null && arguments?.getLong(EXTRA_ID) != 0L){
            id = arguments?.getLong(EXTRA_ID) as Long
            val uri = Uri.withAppendedPath(URI_NOTES, id.toString())
            val cursor = activity?.contentResolver?.query(uri, null, null, null, null)

            if(cursor?.moveToNext() as Boolean){
                newNote = false
                noteEditTitle.setText(cursor.getString(cursor.getColumnIndex(TITLE_NOTES)))
                noteEditDescription.setText(cursor.getString(cursor.getColumnIndex(DESCRIPTION_NOTES)))
            }
            cursor.close()
        }

        return AlertDialog.Builder(activity as Activity)
            .setTitle(if(newNote) "Nova nota" else "Editar nota")
            .setView(view)
            .setPositiveButton("Salvar", this)
            .setNegativeButton("Cancelar", this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val values = ContentValues()
        values.put(TITLE_NOTES, noteEditTitle.text.toString())
        values.put(DESCRIPTION_NOTES, noteEditDescription.text.toString())

        if(which == DialogInterface.BUTTON_POSITIVE){
            if(id != 0L){
                val uri = Uri.withAppendedPath(URI_NOTES, id.toString())
                context?.contentResolver?.update(uri, values, null, null)
            }else{
                context?.contentResolver?.insert(URI_NOTES, values)
            }
        }


    }
}