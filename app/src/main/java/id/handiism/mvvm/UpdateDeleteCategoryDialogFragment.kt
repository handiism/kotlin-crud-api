package id.handiism.mvvm

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import id.handiism.mvvm.databinding.UpdateDeleteCategoryDialogFragmentBinding

class UpdateDeleteCategoryDialogFragment(category: Category) : AppCompatDialogFragment() {
    private lateinit var binding: UpdateDeleteCategoryDialogFragmentBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    var detail: Category = category


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        binding = UpdateDeleteCategoryDialogFragmentBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.etCategoryName.setText(detail.name)
        alertDialog = builder.create()
        return alertDialog
    }
}