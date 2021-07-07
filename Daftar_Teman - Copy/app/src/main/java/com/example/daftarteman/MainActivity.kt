package com.example.daftarteman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        show_data.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        setDataSpinnerRole()
        setDataSpinnerTim()
    }

    private fun setDataSpinnerRole(){
        val adapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        role.adapter = adapter
    }

    private fun setDataSpinnerTim(){
        val adapter = ArrayAdapter.createFromResource(this, R.array.tim, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tim.adapter = adapter
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.save -> {
                val getUserId = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNama: String = nama.getText().toString()
                val getAlamat: String = alamat.getText().toString()
                val getNo_Hp: String = no_hp.getText().toString()
                val getRole: String = role.selectedItem.toString()
                val getTim: String = tim.selectedItem.toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNama) || isEmpty(getAlamat) || isEmpty(getNo_Hp) || isEmpty(getRole) || isEmpty(getTim)) {
                    Toast.makeText(
                        this@MainActivity,
                        "Data tidak boleh Ada yang kosong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    getReference.child("Admin").child(getUserId).child("DataTeman").push()
                        .setValue(data_teman(getNama, getAlamat, getNo_Hp, getRole, getTim))
                        .addOnCompleteListener(this) {
                            nama.setText("")
                            alamat.setText("")
                            no_hp.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
            R.id.logout -> {
                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Login Berhasil", Toast.LENGTH_SHORT)
                                .show()
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
            R.id.show_data -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}