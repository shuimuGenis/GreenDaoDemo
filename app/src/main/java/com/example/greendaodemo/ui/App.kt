package com.example.greendaodemo.ui

import android.app.Application
import com.greendao.gen.DaoMaster
import com.greendao.gen.DaoSession

class App : Application() {
    companion object {
        var INSTANTCE: App? = null
    }

    init {
        INSTANTCE = this
    }

    /**
     *只有 第一次调用的时候会进行初始化
     */
    val daoSession: DaoSession by lazy {
        val devOpenHelper = DaoMaster.DevOpenHelper(this, "green_app_dap")
        val db = devOpenHelper.writableDb
        DaoMaster(db).newSession()
    }

    override fun onCreate() {
        super.onCreate()
        daoSession
    }
}