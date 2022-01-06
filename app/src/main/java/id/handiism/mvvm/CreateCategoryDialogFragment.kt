package id.handiism.mvvm

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import id.handiism.mvvm.databinding.CreateCategoryDialogFragmentBinding

class CreateCategoryDialogFragment : AppCompatDialogFragment() {
    private lateinit var binding: CreateCategoryDialogFragmentBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        binding = CreateCategoryDialogFragmentBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        alertDialog = builder.create()
        return alertDialog
    }
}