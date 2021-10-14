package com.example.headsup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var llMain : LinearLayout
    lateinit var rvItems : RecyclerView
    lateinit var bAdd : Button
    lateinit var eNameforSearch : EditText
    lateinit var bSubmit : Button

    lateinit var llAdd : LinearLayout
    lateinit var eNameforAdd : EditText
    lateinit var eT1 : EditText
    lateinit var eT2: EditText
    lateinit var eT3 : EditText
    lateinit var bAddCelebrity : Button
    lateinit var bBack : Button

    lateinit var llEdit : LinearLayout
    lateinit var eNameforEdit : EditText
    lateinit var eT1Edit : EditText
    lateinit var eT2Edit: EditText
    lateinit var eT3Edit : EditText
    lateinit var bUpdate : Button
    lateinit var bDel: Button
    lateinit var bBack2 : Button

    lateinit var itemList : ArrayList<CelebrityInfo.Info>
    lateinit var rvAdapter : RVAdapter

    var listName= ""
    var listT1= ""
    var listT2 = ""
    var listT3 = ""
    var pk = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llMain = findViewById(R.id.llMain)
        llAdd = findViewById(R.id.llAdd)
        llEdit = findViewById(R.id.llEdit)
        bAdd = findViewById(R.id.bAdd)
        eNameforSearch = findViewById(R.id.eNameforSearch)
        bSubmit = findViewById(R.id.bSubmit)
        eNameforAdd = findViewById(R.id.eNameforAdd)
        eT1 = findViewById(R.id.eT1)
        eT2 = findViewById(R.id.eT2)
        eT3 = findViewById(R.id.eT3)
        bAddCelebrity = findViewById(R.id.bAddCeleb)
        bBack = findViewById(R.id.bBack)
        eNameforEdit = findViewById(R.id.eNameforEdit)
        eT1Edit = findViewById(R.id.eT1Edit)
        eT2Edit = findViewById(R.id.eT2Edit)
        eT3Edit = findViewById(R.id.eT3Edit)
        bUpdate = findViewById(R.id.bUpdate)
        bDel = findViewById(R.id.bDel)
        bBack2 = findViewById(R.id.bBack2)



        itemList = ArrayList()

        rvItems = findViewById(R.id.rvItems)
        rvAdapter = RVAdapter(itemList)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        getCelebrities()

        bAdd.setOnClickListener {
            llMain.isVisible = false
            llAdd.isVisible = true

        }

        bAddCelebrity.setOnClickListener {
            addCelebrities()
            rvAdapter.notifyDataSetChanged()
        }

        bBack.setOnClickListener {
            llAdd.isVisible = false
            llMain.isVisible = true
            rvAdapter.notifyDataSetChanged()

        }

        bSubmit.setOnClickListener {
            llMain.isVisible = false
            llEdit.isVisible = true
            findCelebrity()

        }

        bUpdate.setOnClickListener {
            updateCelebrity()
            rvAdapter.notifyDataSetChanged()

        }

        bDel.setOnClickListener {
            deleteCelebrity()
            rvAdapter.notifyDataSetChanged()
        }

        bBack2.setOnClickListener {
            rvAdapter.notifyDataSetChanged()
            llMain.isVisible = true
            llEdit.isVisible= false

        }

    }

    fun getCelebrities(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getCelebrities()?.enqueue(object : Callback<List<CelebrityInfo.Info>> {
            override fun onResponse(
                call: Call<List<CelebrityInfo.Info>>,
                response: Response<List<CelebrityInfo.Info>>
            ) {
                var result: String? = ""

                for (i in response.body()!!) {
//                    result = i.name+ " \n\n"+ i.taboo1+" \n"+ i.taboo2 +" \n"+ i.taboo3
//                    itemList.add(result!!)
                     listName= i.name.toString()
                     listT1= i.taboo1.toString()
                     listT2= i.taboo2.toString()
                     listT3= i.taboo3.toString()
                     pk = i.pk!!
                    itemList.add(CelebrityInfo.Info(listName,listT1,listT2,listT3, pk))

                }
                rvAdapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<List<CelebrityInfo.Info>>, t: Throwable) {
                Log.d("Main", "Unable to get data")
            }
        })
    }

    fun addCelebrities(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.addCelebrities(EditCelebrity(eNameforAdd.text.toString(), eT1.text.toString(), eT2.text.toString(), eT3.text.toString()))?.enqueue(object:
            Callback<EditCelebrity> {
            override fun onResponse(call: Call<EditCelebrity>, response: Response<EditCelebrity>) {
                Toast.makeText(this@MainActivity,"Celebrity has been Added", Toast.LENGTH_LONG).show()

                rvAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<EditCelebrity>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun findCelebrity(){
        var name = eNameforSearch.text.toString()

        for (i in itemList ){
            if (name == i.name){
                eNameforEdit.setText("${i.name}")
                eT1Edit.setText("${i.taboo1}")
                eT2Edit.setText("${i.taboo2}")
                eT3Edit.setText("${i.taboo3}")
                pk = i.pk!!

            }
        }

    }

    fun updateCelebrity(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.updateCelebrity(pk,
            CelebrityInfo.Info(eNameforEdit.text.toString(), eT1Edit.text.toString(), eT2Edit.text.toString(), eT3Edit.text.toString()
            , pk))?.enqueue(object : Callback<CelebrityInfo.Info> {
            override fun onResponse(call: Call<CelebrityInfo.Info>, response: Response<CelebrityInfo.Info>) {
                Toast.makeText(this@MainActivity,"Celebrity updated", Toast.LENGTH_LONG).show()
                rvAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<CelebrityInfo.Info>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun deleteCelebrity(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.deleteCelebrity(pk)?.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@MainActivity,"User deleted", Toast.LENGTH_LONG).show()
                rvAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }




}