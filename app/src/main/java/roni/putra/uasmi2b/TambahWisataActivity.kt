package roni.putra.uasmi2b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.uasmi2b.api.ApiClient
import roni.putra.uasmi2b.model.TambahWisataResponse
import java.io.File

class TambahWisataActivity : AppCompatActivity() {
    private lateinit var etWisata: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnGambar: Button
    private lateinit var etNotlpn: EditText
    private lateinit var etAlamat: EditText
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_wisata)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnTambah = findViewById(R.id.btnTambah)
        etWisata = findViewById(R.id.etWisata)
        etNotlpn = findViewById(R.id.etNotlpn)
        etAlamat = findViewById(R.id.etAlamat)
        progressBar = findViewById(R.id.progressBar)


        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                addWisata(etWisata.text.toString(), etNotlpn.text.toString(), etAlamat.text.toString(), etDeskripsi.text.toString(), file)
            }
        }

    }

    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    //proses tambah berita
    private fun addWisata(nama_wisata: String, notlpn: String, alamat: String, deskripsi_wisata: String, gambar_sekolah: File) {
        progressBar.visibility = View.VISIBLE
        val requestBody = gambar_sekolah.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val gambar =
            MultipartBody.Part.createFormData("gambar", gambar_sekolah.name, requestBody)
        val nama_wisata = nama_wisata.toRequestBody("text/plain".toMediaTypeOrNull())
        val notlpn = notlpn.toRequestBody("text/plain".toMediaTypeOrNull())
        val alamat = alamat.toRequestBody("text/plain".toMediaTypeOrNull())
        val deskripsi_wisata = deskripsi_wisata.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addWisata(nama_wisata,notlpn,alamat, deskripsi_wisata, gambar)
            .enqueue(object : Callback<TambahWisataResponse> {
                override fun onResponse(
                    call: Call<TambahWisataResponse>,
                    response: Response<TambahWisataResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            startActivity(
                                Intent(
                                    this@TambahWisataActivity,
                                    DashboardActivity::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@TambahWisataActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@TambahWisataActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<TambahWisataResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahWisataActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            })
    }
}