package com.example.greendaodemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.greendaodemo.R
import com.example.greendaodemo.bean.BookData
import com.example.greendaodemo.bean.HomeAddressData
import com.example.greendaodemo.bean.UserData
import com.greendao.gen.UserDataDao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        saveToDb.setOnClickListener(this)
        queryFromDb.setOnClickListener(this)
        oneToOne.setOnClickListener(this)
        deletByconfiden.setOnClickListener(this)
        updateByconfiden.setOnClickListener(this)
        oneToMany.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.saveToDb -> {
                saveAction()
            }
            R.id.queryFromDb -> {
                query()
            }
            R.id.oneToOne -> {
                oneToOne()
            }
            R.id.deletByconfiden -> {
                deleteByConfidence()
            }
            R.id.updateByconfiden -> {
                updataByconfidence()
            }
            R.id.oneToMany -> {
                oneToMany()
            }
        }
    }

    /**
     *保存数据
     */
    fun saveAction() {
        GlobalScope.launch(Dispatchers.Main) {
            val asyncJob = GlobalScope.async {
                val userData = UserData()
                userData.name = "花语"
                userData.age = 20
                App.INSTANTCE?.daoSession!!.userDataDao!!.save(userData)
            }
            asyncJob.await()
            Toast.makeText(this@MainActivity, "保存成功！", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 演示保存集合
     */
    fun saveCollection() {
        GlobalScope.launch(Dispatchers.Main) {
            var asycJob = GlobalScope.async {
                //创建集合对象
                var datalist = mutableListOf<UserData>()
                val userData1 = UserData()
                userData1.name = "火星"
                val userData2 = UserData()
                userData2.name = "木星"
                val userData3 = UserData()
                userData3.name = "土星"
                //添加集合
                datalist.add(userData1)
                datalist.add(userData2)
                datalist.add(userData3)
                //通过insertInTx()方法保存集合
                App.INSTANTCE?.daoSession?.userDataDao?.insertInTx(datalist)
            }
            asycJob.await()
        }
    }

    /**
     * 从数据库中查询数据
     */
    fun query() {
        GlobalScope.launch(Dispatchers.Main) {
            val tempResultJob = GlobalScope.async {
                App.INSTANTCE?.daoSession!!.userDataDao!!.loadAll()
            }
            val await = tempResultJob.await()
            var result: String? = ""
            await?.forEach {
                result += "userid=${it.id},name= ${it.name},age = ${it.age} \n"
            }
            showDbData.text = result
        }
    }

    /**
     * 演示 根据条件删除数据
     */
    fun deleteByConfidence() {
        GlobalScope.launch(Dispatchers.Main) {
            var tempjob = GlobalScope.async {
                //1.根据条件进行删除数据
                App.INSTANTCE?.daoSession!!.userDataDao!!
                    .queryBuilder()
                    .where(UserDataDao.Properties.Name.eq("花语"))
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities()
                //2.直接根据key删除数据,这里的key指主键ID
                App.INSTANTCE?.daoSession!!.userDataDao!!
                    .deleteByKey(124)
                //3.直接清除全部数据等
                App.INSTANTCE?.daoSession!!.userDataDao!!.deleteAll()
                //greenDao的还有很多重载的删除方法..大同小异罢了。
            }
            tempjob.await()
        }
    }

    /**
     * 根据条件更新数据库
     */
    fun updataByconfidence() {
        GlobalScope.launch {
            var tempjob = GlobalScope.async {
                //1.根据条件进行搜索出对应的数据
                var queryList = App.INSTANTCE?.daoSession!!.userDataDao!!
                    .queryBuilder()
                    .where(UserDataDao.Properties.Name.eq("水木"), UserDataDao.Properties.Id.eq(134))
                    .build()
                    .list()
                //2.修改查询出来的数据
                queryList?.apply {
                    if (isEmpty()) {
                        return@apply
                    }
                    queryList.forEach {
                        it.name = "扶苏"
                    }
                    //3.修改成功之后，更新到数据库中。因为集合更新可以使用updateInTx()方法,当个更新则用update()
                    App.INSTANTCE?.daoSession!!.userDataDao!!.updateInTx(queryList)
                }
            }
            tempjob.await()
        }
    }


    /**
     * 演示一对一 数据库关系
     */
    fun oneToOne() {
        GlobalScope.launch(Dispatchers.Main) {
            var tempjob = GlobalScope.async {
                //创建一个地址信息
                var homeAddress = HomeAddressData()
                homeAddress.id = 111
                homeAddress.address = "广东省广州市天河区华景新城南方科技大厦"
                //创建一个用户信息,注意 地址信息的ID 与 用户信息的addressId一致
                var userData = UserData()
                userData.id = 134
                userData.age = 20
                userData.name = "水木"
                userData.addressId = 111
                //分别存放进不同的表中
                App.INSTANTCE?.daoSession?.homeAddressDataDao?.save(homeAddress)
                App.INSTANTCE?.daoSession?.userDataDao?.insert(userData)
                //这时候可以查询..我们刚才放入的数据
                var result = App.INSTANTCE?.daoSession?.userDataDao?.queryBuilder()
                    ?.where(UserDataDao.Properties.Name.eq("水木"))?.list()
                result
            }
            var temp = tempjob.await()
            Toast.makeText(this@MainActivity, "保存成功", Toast.LENGTH_SHORT)
            if (temp!!.isNotEmpty()) {
                var tempData = temp[0]
                showDbData.text =
                    "[userName = ${tempData.name},userId = ${tempData.id},age=${tempData.age},address = ${tempData.address?.address}]"

            }
        }
    }

    /**
     * 演示 一对多 关系
     */
    fun oneToMany() {
        GlobalScope.launch(Dispatchers.Main) {
            var tempJob = GlobalScope.async {
                //创建一个书本对象
                var tempBook = BookData()
                tempBook.name = "中华上下五千年"
                //设置这本书所关联的用户的主键ID
                tempBook.userId = 321
                //创建用户对象
                var tempuser = UserData()
                tempuser.name = "相遇"
                //设置用户对象的主键ID值
                tempuser.id = 321
                tempuser.age = 23
                //分别存放进不同的表中
                App.INSTANTCE?.daoSession?.bookDataDao?.insert(tempBook)
                App.INSTANTCE?.daoSession?.userDataDao?.insert(tempuser)
                //查询出该用户
                var result = App.INSTANTCE?.daoSession?.userDataDao?.queryBuilder()
                    ?.where(UserDataDao.Properties.Name.eq("相遇"))?.list()
                result
            }

            var temp = tempJob.await()
            Toast.makeText(this@MainActivity, "保存成功", Toast.LENGTH_SHORT)
            if (temp!!.isNotEmpty()) {
                var tempData = temp[0]
                showDbData.text =
                    "[userName = ${tempData.name},userId = ${tempData.id},age=${tempData.age},book = ${tempData.books?.get(
                        0
                    )?.name}]"

            }
        }
    }
}
