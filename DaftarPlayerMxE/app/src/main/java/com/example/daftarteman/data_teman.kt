package com.example.daftarteman

class data_teman {
    var nama: String? = null
    var alamat: String? = null
    var no_hp: String? = null
    var key: String? = null
    var jkel: String? = null

    constructor()

    constructor(nama: String?, alamat: String?, no_hp: String?, jkel: String?) {
        this.nama = nama
        this.alamat = alamat
        this.no_hp = no_hp
        this.jkel = jkel
    }
}