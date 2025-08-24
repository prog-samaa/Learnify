package com.example.learnify
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Categories (أفقي)
        val categories = listOf(
            Category("Programming", R.drawable.ic_code),
            Category("Design", R.drawable.ic_pallate),
            Category("Math", R.drawable.ic_math),       // أضيفي أيقونات Vector
            Category("Physics", R.drawable.ic_atom),
            Category("Business", R.drawable.ic_bar_chart)
        )
        val rvCat = findViewById<RecyclerView>(R.id.rvCategories)
        rvCat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCat.adapter = CategoryAdapter(categories)

        // 2) Courses (Grid 2 أعمدة)
        val courses = listOf(
            Course("Learn HTML & CSS", 60, R.drawable.ic_programming),
            Course("Python for Beginners", 20, R.drawable.ic_python),
            Course("Kotlin Basics", 45, R.drawable.ic_kotlin),
            Course("UX Fundamentals", 10, R.drawable.ic_design)
        )
        val rvCourses = findViewById<RecyclerView>(R.id.rvCourses)
        rvCourses.layoutManager = GridLayoutManager(this, 2)
        rvCourses.adapter = CourseAdapter(courses)
    }
}

