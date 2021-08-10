package ir.nikapps.motionlayout_playground5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.rv_main_rv).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = RvAdapter(MyData.getData(), {//onRemove
                Toast.makeText(context, "REMOVE", Toast.LENGTH_SHORT).show()
            }, {//onEdit
                Toast.makeText(context, "EDIT", Toast.LENGTH_SHORT).show()
            })
        }
    }
}