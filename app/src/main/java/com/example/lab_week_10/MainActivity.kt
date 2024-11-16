package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { prepareDatabase() }

    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

//    private var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        total = 0
//        updateText(total)
//
//        findViewById<Button>(R.id.button_increment).setOnClickListener {
//            incrementTotal()
//        }

        initializeValueFromDatabase()

        prepareViewModel()
    }

//    private fun incrementTotal() {
//        total++
//        updateText(total)
//    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }

    private fun initializeValueFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val total = db.totalDao().getTotal(ID)
            if (total.isEmpty()) {
                db.totalDao().insert(Total(id = 1, total = 0))
            } else {
                withContext(Dispatchers.Main) {
                    viewModel.setTotal(total.first().total)
                }
            }
        }
    }

    companion object {
        const val ID: Long = 1
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch(Dispatchers.IO) {
            val currentTotal = viewModel.total.value ?: 0
            db.totalDao().update(Total(ID, currentTotal))
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) {
            updateText(it)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }
}

//GOSONG CHEF SAMA TUGASNYA, KUPASRAHKAN KEPADA YANG MAHA KUASA AJA, TRYHARD BET ITU ASLI, UDH BIKIN MALAH CRASH SEMUA CODE, SAKTI BET. ~PIBI

