package pnj.uts.ti.razita_nazla_febriani

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataAlumniActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var dao: AlumniDao
    private var alumniList = MutableLiveData<List<AlumniModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_alumni)

        database = DatabaseHelper.getInstance(applicationContext)
        dao = database.alumniDao()
        dao.getAlumni().observe(this) {
            alumniList.value = it ?: emptyList()
        }

        alumniList.observe(this) {
            val arrayAdapter: ArrayAdapter<AlumniModel> = AlumniAdapter(
                this,
                alumniList.value ?: emptyList(),
                onItemClickListener = {
                    val pindah = Intent(applicationContext, DetailAlumniActivity::class.java)
                    pindah.putExtra("nim", it)
                    startActivity(pindah)
                }
            )
            val listView = findViewById<ListView>(R.id.alumniListView)
            listView.adapter = arrayAdapter
        }
    }
}