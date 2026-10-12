package id.alinea.tokukuapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.alinea.tokukuapp.model.Produk

class TambahProdukActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_produk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = FirebaseFirestore.getInstance()
        val edtIdProduk: EditText = findViewById(R.id.edtIdProduk)
        val edtNamaProduk: EditText = findViewById(R.id.edtNamaProduk)
        val edtHargaProduk: EditText = findViewById(R.id.edtHargaProduk)
        val btnSimpan: Button = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val IdProduk = edtIdProduk.text.toString()
            val NamaProduk = edtNamaProduk.text.toString()
            val Harga = edtHargaProduk.text.toString().toDouble()
            val produk = Produk(IdProduk,NamaProduk,Harga)
            db.collection("produk")
                .add(produk)
                .addOnSuccessListener {
                    Toast.makeText(
                        this, "Berhasi Menyimpan data", Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                .addOnFailureListener { error ->
                    Toast.makeText(
                        this, "Gagal Menyimpan data : ${error.message}", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}