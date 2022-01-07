package id.handiism.mvvm

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import id.handiism.mvvm.databinding.UpdateDeleteCategoryDialogFragmentBinding

class UpdateDeleteCategoryDialogFragment : AppCompatDialogFragment(), View.OnClickListener {
    private lateinit var binding: UpdateDeleteCategoryDialogFragmentBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var viewModel: UpdateDeleteCategoryDialogViewModel
    private var category: Category? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        binding = UpdateDeleteCategoryDialogFragmentBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        initView()
        binding.btnUpdate.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        alertDialog = builder.create()
        alertDialog.setOnDismissListener {
            (activity as MainActivity).updateDataSet()
        }
        return alertDialog
    }

    private fun initUpdateCategoryViewModel() {
        viewModel = ViewModelProvider(this)[UpdateDeleteCategoryDialogViewModel::class.java]
        viewModel.getUpdateCategoryObservable().observe(this, fun(it: Category?) {
            Log.d("UpdateCategoryViewModel", "category: $it")
            if (it != null) {
                category = it
            }
        })
        Log.d(
            "UpdateCategoryViewModel",
            "params: category($category!!.id , ${binding.etCategoryName.text})"
        )
        viewModel.updateCategory(category!!.id, RequestBody(binding.etCategoryName.text.toString()))
    }

    private fun initDeleteCategoryViewModel() {
        viewModel = ViewModelProvider(this)[UpdateDeleteCategoryDialogViewModel::class.java]
        viewModel.getDeleteCategoryObservable().observe(this, fun(_: Category?) {
            Log.d("DeleteCategoryViewModel", "params: category(${category!!.id})")
        })
        viewModel.deleteCategory(category!!.id)
    }

    private fun initView() {
        this.category = arguments?.getParcelable("category")
        Log.d("initView", "category: ${this.category}")
        binding.etCategoryId.text = Editable.Factory().newEditable(category?.id.toString())
        binding.etCategoryId.isFocusable = false
        binding.etCategoryId.isClickable = false
        binding.etCategoryId.isLongClickable = false
        binding.etCategoryId.isCursorVisible = false
        binding.etCategoryName.text = Editable.Factory().newEditable(category?.name)
    }

    override fun onClick(v: View?) {
        if (v == binding.btnUpdate) {
            initUpdateCategoryViewModel()
            Toast.makeText(context, "Category Changed Successfully", Toast.LENGTH_SHORT).show()
            dismiss()
            (activity as MainActivity).updateDataSet()
        } else if (v == binding.btnDelete) {
            initDeleteCategoryViewModel()
            Toast.makeText(context, "Category Deleted Successfully", Toast.LENGTH_SHORT).show()
            dismiss()
            (activity as MainActivity).updateDataSet()
        }
    }
}