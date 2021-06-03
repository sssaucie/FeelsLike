package com.example.feelslike.view

import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.feelslike.R

class ResultsFragment : Fragment()
{
    private fun showCreateFavoriteDialog()
    {
        val dialogPrompt = getString(R.string.text_name_favorite)
        val saveButtonTitle = getString(R.string.text_save)

        val builder = AlertDialog.Builder(this)
        val favoritesNameEditText = EditText(this)
        favoritesNameEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogPrompt)
        builder.setView(favoritesNameEditText)

        builder.setPositiveButton(saveButtonTitle)
        {
            dialog, _ -> dialog.dismiss()
        }
        builder.create().show()
    }
}