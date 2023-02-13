package com.example.ass8mysql_queryinsert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ass8mysql_queryinsert.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var employeeList  = arrayListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )
        // Link to RecyclerView
        binding.recyclerView.adapter = EmployeeAdapter(this.employeeList, applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(binding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL)
        )

        // Add Employee Button
        binding.btnAdd.setOnClickListener {
            val intent = Intent(applicationContext, InsertActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        callEmployee()
    }

    private fun callEmployee(){
        employeeList.clear();
        val serv : EmployeeAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeAPI ::class.java)

        serv.retrieveEmployee()
            .enqueue(object : Callback<List<Employee>> {
                override fun onResponse(call: Call<List<Employee>>,
                                        response: Response<List<Employee>>
                ) {
                    response.body()?.forEach {
                        employeeList.add(Employee(it.emp_name,it.emp_gender,it.emp_email,it.emp_salary))
                    }
                    //// Set Data to RecyclerRecyclerView
                    binding.recyclerView.adapter = EmployeeAdapter(employeeList,applicationContext)
                }
                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure " + t.message,
                        Toast.LENGTH_LONG).show()
                }
            })
    }
}