package roni.putra.uasmi2b.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.uasmi2b.DetailWisataActivity
import roni.putra.uasmi2b.R
import roni.putra.uasmi2b.model.wisataResponse

class adapterWisata (
    val dataWisata: ArrayList<wisataResponse.ListItems>

): RecyclerView.Adapter<adapterWisata.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        //inisiallisasi widget
        val imgWisata = view.findViewById<ImageView>(R.id.imgWisata)
        val tvWisata = view.findViewById<TextView>(R.id.tvWisata)
        val tvNoTelpon = view.findViewById<TextView>(R.id.tvNoTelpon)
        val tvAlamat = view.findViewById<TextView>(R.id.tvAlamat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_wisata, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataWisata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //tampilkan data//
        val hasilResponse = dataWisata[position]
        Picasso.get().load(hasilResponse.gambar).into(holder.imgWisata)
        holder.tvWisata.text = hasilResponse.nama_wisata
        holder.tvNoTelpon.text = hasilResponse.notlpn
        holder.tvAlamat.text = hasilResponse.alamat

        //klik item sekolah
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailWisataActivity::class.java).apply {
                putExtra("gambar", hasilResponse.gambar)
                putExtra("nama_wisata", hasilResponse.nama_wisata)
                putExtra("notlpn", hasilResponse.notlpn)
                putExtra("alamat", hasilResponse.alamat)
                putExtra("deskripsi_wisata", hasilResponse.deskripsi_wisata)

            }
            holder.imgWisata.context.startActivity(intent)
        }
}
    fun setData(data: List<wisataResponse.ListItems>){
        dataWisata.clear()
        dataWisata.addAll(data)
    }
}