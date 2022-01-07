package id.handiism.mvvm

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import id.handiism.mvvm.databinding.CreateCategoryDialogFragmentBinding

class CreateCategoryDialogFragment : AppCompatDialogFragment(), View.OnClickListener {
    private lateinit var binding: CreateCategoryDialogFragmentBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var viewModel: CreateCategoryDialogViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        binding = CreateCategoryDialogFragmentBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        alertDialog = builder.create()
        alertDialog.setOnDismissListener {
            (activity as MainActivity).updateDataSet()
        }
        binding.btnCreate.setOnClickListener(this)
        return alertDialog
    }

    override fun onClick(v: View?) {
        if (v == binding.btnCreate) {
            viewModel = ViewModelProvider(this)[CreateCategoryDialogViewModel::class.java]
            viewModel.getCreateCategoryObservable().observe(this) {
                Log.d(
                    "CreateCategoryViewModel",
                    "params: category(${binding.etCategoryName.text})"
                )
            }
            viewModel.createCategory(RequestBody(binding.etCategoryName.text.toString()))
            dismiss()
            (activity as MainActivity).updateDataSet()
        }
    }
}