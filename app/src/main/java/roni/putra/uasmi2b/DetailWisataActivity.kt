package roni.putra.uasmi2b

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailWisataActivity : AppCompatActivity() {
    private lateinit var imgWisata: ImageView
    private lateinit var tvWisata: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var tvNoTelpon: TextView
    private lateinit var tvAlamat: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_wisata)

        imgWisata= findViewById(R.id.imgWisata)
        tvWisata= findViewById(R.id.tvWisata)
        tvDeskripsi= findViewById(R.id.tvDeskripsi)
        tvNoTelpon=findViewById(R.id.tvNoTelpon)
        tvAlamat=findViewById(R.id.tvAlamat)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgWisata)
        tvWisata.text= intent.getStringExtra("nama_wisata")
        tvDeskripsi.text= intent.getStringExtra("deskripsi_wisata")
        tvNoTelpon.text= intent.getStringExtra("no_tlpn")
        tvAlamat.text= intent.getStringExtra("alamat")
    }
}