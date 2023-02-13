package com.example.ass8mysql_queryinsert

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ass8mysql_queryinsert.databinding.EmpItemLayoutBinding

class EmployeeAdapter(val items : List<Employee>, val context: Context) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, val binding: EmpItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmpItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.nameText.text = "Name: " + items[position].emp_name
        binding.genderText.text = "Gender : " + items[position].emp_gender
        binding.emailText.text = "E-mail : "+items[position].emp_email
        binding.salaryText.text = "Salary : "+ String.format("%,d",items[position].emp_salary) + " Baht"
        // binding.salaryText.text = "Salary : "+items[position].salary.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}