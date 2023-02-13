package com.example.ass8mysql_queryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.ass8mysql_queryinsert.databinding.ActivityInsertBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {
    private lateinit var bindingInsert : ActivityInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInsert = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(bindingInsert.root)
        // Add Button
        bindingInsert.btnAdd.setOnClickListener {
            addEmployee()
        }
        // Reset Button
        bindingInsert.btnReset.setOnClickListener {
            resetEmployee()
        }
    }
    private fun addEmployee(){
        var  radioGroup: RadioGroup =  findViewById(R.id.radioGroup)
        var  selectedId : Int = radioGroup.checkedRadioButtonId
        var  radioButton : RadioButton = findViewById(selectedId)

        val api: EmployeeAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeAPI::class.java)
        api.insertEmployee(
            bindingInsert.edtName.text.toString(),
            radioButton.text.toString(),
            bindingInsert.edtEmail.text.toString(),
            bindingInsert.edtSalary.text.toString().toInt()
        ).enqueue(object : Callback<Employee> {
            override fun onResponse(
                call: Call<Employee>,
                response: retrofit2.Response<Employee>
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext,"Successfully Inserted",
                        Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Inserted Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(applicationContext,"Error onFailure " +
                        t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetEmployee(){
        bindingInsert.edtName.text?.clear()
        bindingInsert.edtEmail.text?.clear()
        bindingInsert.edtSalary.text?.clear()
        var  radioGroup: RadioGroup =  findViewById(R.id.radioGroup)
        radioGroup.clearCheck()
    }

}