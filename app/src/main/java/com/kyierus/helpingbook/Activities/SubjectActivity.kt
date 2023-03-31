package com.kyierus.helpingbook.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kyierus.helpingbook.Adapters.SubjectListAdapter
import com.kyierus.helpingbook.Models.SubjectModel
import com.kyierus.helpingbook.R
import com.kyierus.helpingbook.databinding.ActivitySubjectBinding

class SubjectActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubjectBinding

    lateinit var list: ArrayList<SubjectModel>

    lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_subject)

        firebaseDatabase= FirebaseDatabase.getInstance()

        list= ArrayList()

        val classId=intent.getIntExtra("classId",0)

        val className=intent.getStringExtra("className")

        binding.optionsTitle.text="CLASS $className"

        var adapter=SubjectListAdapter(list,this)

        val layoutManager=LinearLayoutManager(this)

        binding.subjectRv.layoutManager=layoutManager

        firebaseDatabase.reference.child("Subjects").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                if (snapshot.exists()){

                    for (dataSnapshot in snapshot.children){

                        var data= dataSnapshot.getValue(SubjectModel::class.java)

                        data?.subjectId= dataSnapshot.key?.toInt()!!

                        if (data?.classId==classId){

                            list.add(data)

                        }

                    }

                    adapter.notifyDataSetChanged()

                    binding.subjectRv.adapter=adapter

                }


            }

            override fun onCancelled(error: DatabaseError) {


            }


        })



    }
}