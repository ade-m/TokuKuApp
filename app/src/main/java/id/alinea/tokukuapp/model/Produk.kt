package id.alinea.tokukuapp.model

import com.google.firebase.firestore.Exclude

class Produk (
    var IDProduk: String="",
    var NamaProduk: String="",
    var Harga: Double =0.0,

    @get:Exclude
    var documentId:String = ""
)
