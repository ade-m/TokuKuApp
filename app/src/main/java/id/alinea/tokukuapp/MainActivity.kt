package id.alinea.tokukuapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.alinea.tokukuapp.adapter.ProdukAdapter
import id.alinea.tokukuapp.model.Produk

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private  lateinit var rcyProduk: RecyclerView
    private lateinit var adapter: ProdukAdapter

    private  val produkList = ArrayList<Produk>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        rcyProduk = findViewById(R.id.rcyProduk)
        val btnTambah = findViewById<Button>(R.id.btnTambah)

        rcyProduk.layoutManager = LinearLayoutManager(this)
        adapter = ProdukAdapter(produkList){
            produk -> konfirmasiHapus(produk)
        }
        rcyProduk.adapter= adapter

        btnTambah.setOnClickListener {
            startActivity(Intent(this, TambahProdukActivity::class.java))
          //  finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun loadData(){
        val userId = auth.currentUser?.uid ?: return

        db.collection("produk")
            .get()
            .addOnSuccessListener {
                documents-> produkList.clear()

                for(doc in documents){
                    val produk = doc.toObject(Produk::class.java)
                    produk.documentId = doc.id
                    produkList.add(produk)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    this, "Gagal Mengambil data : ${error.message}", Toast.LENGTH_SHORT
                ).show()
            }
    }
    private fun konfirmasiHapus(produk: Produk){
        AlertDialog.Builder(this)
            .setTitle("Hapus Produk")
            .setMessage("Apakah anda yakin ingin menghapus" +
                    "${produk.NamaProduk}"
            )
            .setPositiveButton("Hapus"){
                _, _-> hapusProduk(produk)
            }
            .setNegativeButton("Batal",null)
            .show()
    }
    private fun hapusProduk(produk: Produk){
        db.collection("produk")
            .document(produk.documentId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    this, "Berhasi Menghapus data", Toast.LENGTH_SHORT
                ).show()
                loadData()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    this, "Gagal Mengambil data : ${error.message}", Toast.LENGTH_SHORT
                ).show()
            }
    }
}