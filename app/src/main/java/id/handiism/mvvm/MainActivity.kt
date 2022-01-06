package id.handiism.mvvm

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.handiism.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel

    override fun onItemClickListener(category: Category) {
        val dialog = UpdateDeleteCategoryDialogFragment(category)
        dialog.show(supportFragmentManager, "updateDeleteCategoryDialogFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        initViewModel()
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
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getAllCategoryObservable().observe(this) {
            Log.d(TAG, "initViewModel: $it")
            if (it == null) {
                Toast.makeText(
                    this@MainActivity,
                    "404 NOT FOUND",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                recyclerViewAdapter.categories = it.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
        viewModel.getAllCategory()
        Log.d(TAG, "initViewModel: pass")
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
        }
        return super.onOptionsItemSelected(item)
    }
}