package id.alinea.tokukuapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import id.alinea.tokukuapp.R
import id.alinea.tokukuapp.model.Produk

class ProdukAdapter(
    private val produkList: List<Produk>,
    private val onHapusClick: (Produk) -> Unit
) : RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder>(){

    class ProdukViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){
        val txvIDProduk: TextView = itemView.findViewById(R.id.txvIDProduk)
        val txvNamaProduk: TextView = itemView.findViewById(R.id.txvNamaProduk)
        val txvHarga: TextView = itemView.findViewById(R.id.txvHarga)
        val btnHapus: ImageButton = itemView.findViewById(R.id.btnHapus)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdukViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produk,parent,false)
        return ProdukViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val produk = produkList[position]
        holder.txvNamaProduk.text = produk.NamaProduk
        holder.txvIDProduk.text = produk.IDProduk
        holder.txvHarga.text = "Rp." + produk.Harga.toString()

        holder.btnHapus.setOnClickListener {
            onHapusClick(produk)
        }
    }

    override fun getItemCount(): Int = produkList.size
}