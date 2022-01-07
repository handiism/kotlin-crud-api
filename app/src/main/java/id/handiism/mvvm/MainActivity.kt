package id.handiism.mvvm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.handiism.mvvm.MainActivity.Const.TAG
import id.handiism.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var category: Category

    object Const {
        const val TAG = "MainActivity"
    }


    fun updateDataSet() {
        initRecyclerView()
        initAllCategoryViewModel()
    }

    override fun onItemClickListener(category: Category) {
        this.category = category
        val dialog = UpdateDeleteCategoryDialogFragment()
        val args = Bundle()

        Log.d("OnItemClickListener", "category: ${this.category}")
        args.putParcelable("category", this.category)
        dialog.arguments = args
        initCategoryViewModel(category.id)
        dialog.show(supportFragmentManager, "updateDeleteCategoryDialogFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        initAllCategoryViewModel()
    }

    private fun initRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            Log.d(TAG, "initRecyclerView: pass")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAllCategoryViewModel() {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getAllCategoryObservable().observe(this, fun(it: List<Category>?) {
            Log.d("InitAllCategoryVM", "category: $it")
            if (it == null) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvCategory.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvCategory.visibility = View.VISIBLE
                recyclerViewAdapter.categories = it.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getAllCategory()
        Log.d(TAG, "initViewModel: pass")
        viewModel.status.observe(this) {
            Log.d(TAG, "initAllCategoryViewModel: ")
            if (!it) {
                Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initCategoryViewModel(id: Int) {
        Log.d("InitCategoryViewModel", "id: $id")
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getCategoryByIdObservable().observe(this, fun(it: Category?) {
            Log.d("InitCategoryViewModel", "category: $it")
            if (it != null) {
                category = it
            }
        })
        viewModel.getCategoryById(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_add) {
            val dialog = CreateCategoryDialogFragment()
            dialog.show(supportFragmentManager, "createCategoryDialogFragment")
            return true
        } else if (item.itemId == R.id.item_refresh) {
            initRecyclerView()
            initAllCategoryViewModel()
        }
        return super.onOptionsItemSelected(item)
    }
}