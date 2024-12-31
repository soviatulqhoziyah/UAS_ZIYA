package roni.putra.uasmi2b

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.uasmi2b.adapter.adapterWisata
import roni.putra.uasmi2b.api.ApiClient
import roni.putra.uasmi2b.model.wisataResponse

class DashboardActivity : AppCompatActivity() {
    private lateinit var svWisata: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvWisata: RecyclerView
    private lateinit var floatbtnTambah: FloatingActionButton
    private lateinit var adapterWisata: adapterWisata
    private lateinit var imgNotFound: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        svWisata = findViewById(R.id.svWisata)
        progressBar = findViewById(R.id.progressBar)
        rvWisata = findViewById(R.id.rvWisata)
        floatbtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)

        getWisata("")

        svWisata.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(pO: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pencarian: String?): Boolean {
                getWisata(pencarian.toString())
                return true
            }
        })


    }

    private fun getWisata(nama_wisata: String){
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.getListWisata(nama_wisata).enqueue(object:
            Callback<wisataResponse> {
            override fun onResponse(
                call: Call<wisataResponse>,
                response: Response<wisataResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        //set data
                        adapterWisata= adapterWisata(arrayListOf())
                        rvWisata.adapter = adapterWisata
                        adapterWisata.setData(response.body()!!.data)

                        // Sembunyikan gambar error
                        imgNotFound.visibility = View.GONE
                    }else{
                        //jika data tidak ditemukan
                        adapterWisata= adapterWisata(arrayListOf())
                        rvWisata.adapter = adapterWisata
                        imgNotFound.visibility= View.VISIBLE

                    }
                }
                progressBar.visibility= View.GONE
            }

            override fun onFailure(call: Call<wisataResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error : ${t.message}", Toast.LENGTH_LONG)
                progressBar.visibility = View.GONE
            }
        })
        floatbtnTambah.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,TambahWisataActivity::class.java))
        }
    }
}