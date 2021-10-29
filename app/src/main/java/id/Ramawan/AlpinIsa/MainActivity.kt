package id.Ramawan.AlpinIsa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.Ramawan.AlpinIsa.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.hitung.setOnClickListener {
            //Data Peminjam dan data angsuran yang akan ditampilkan
            val totalPinjaman = binding.pokokPinjamanText.text.toString().toBigDecimalOrNull()
            if(totalPinjaman == null){return@setOnClickListener}
            val jangkaWaktu = binding.jangkaWaktuText.text.toString().toIntOrNull()
            if(jangkaWaktu == null){return@setOnClickListener}

            val biayaLayanan = hitungBiayaLayanan(totalPinjaman,0.05)
            val angsuranBunga = hitungAngsuranBunga(totalPinjaman,0.0375)
            val angsuranPokok = hitungAngsuranPokok(totalPinjaman,jangkaWaktu)

            val totalAngsuran = angsuranPokok+angsuranBunga
            val angsuranPertama = totalAngsuran+biayaLayanan
            val totalYangDibayarkan = (totalAngsuran*jangkaWaktu.toBigDecimal())+biayaLayanan

            //Menampilkan hasil perhitungan angsuran
            val dataPeminjamText:String =
                "Jumlah Pinjaman : "+formatToRupiah(totalPinjaman)+
                "\nLama Pinjaman : "+jangkaWaktu+" bulan"+
                "\nSuku Bunga : 3.75% per Bulan"+
                "\nBiaya Layanan : (5%)"+formatToRupiah(biayaLayanan)+"\n"

            val hasilPerhitunganText:String =
                "Angsuran Pokok per Bulan : "+formatToRupiah(angsuranPokok)+
                "\nAngsuran Bunga per Bulan : "+formatToRupiah(angsuranBunga)+
                "\n__________________________+"+
                "\nTotal Angsuran per Bulan : "+formatToRupiah(totalAngsuran)+
                "\n\nAngsuran Pertama(+Biaya Layanan) : "+formatToRupiah(angsuranPertama)+
                "\nTotal Tagihan : "+formatToRupiah(totalYangDibayarkan)

            binding.hasilDataPeminjamHeader.text = "Data Peminjam:"
            binding.hasilDataPeminjam.text = dataPeminjamText
            binding.hasilPerhitunganHeader.text = "Angsuran Anda:"
            binding.hasilPerhitungan.text  = hasilPerhitunganText
            binding.hasilLayout.background = getDrawable(R.drawable.customborder)
        }
    }

    fun hitungBiayaLayanan(totalPinjaman: BigDecimal, persenbiayaLayanan: Double): BigDecimal {
        return totalPinjaman * persenbiayaLayanan.toBigDecimal()
    }

    fun hitungAngsuranBunga(totalPinjaman: BigDecimal, sukuBunga: Double): BigDecimal {
        return totalPinjaman * sukuBunga.toBigDecimal()
    }

    fun hitungAngsuranPokok(totalPinjaman: BigDecimal, jangkaWaktu: Int): BigDecimal {
        return totalPinjaman / jangkaWaktu.toBigDecimal()
    }
    fun formatToRupiah(number:BigDecimal):String{
        val localeID = Locale("in","ID")
        return NumberFormat.getCurrencyInstance(localeID).format(number).toString()
    }
}