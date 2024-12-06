package com.example.memorina

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.memorina.ui.theme.MemorinaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private var firstSelected: ImageView? = null
    private var secondSelected: ImageView? = null
    private var won:Boolean=false
    private var solved:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var array=arrayOf(R.drawable.sprite_0001,R.drawable.sprite_0002,R.drawable.sprite_0003,R.drawable.sprite_0004,R.drawable.sprite_0005,R.drawable.sprite_0006,R.drawable.sprite_0007,R.drawable.sprite_0008)
        val layout = LinearLayout(applicationContext)
        layout.orientation=LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight=1.toFloat()
        params.height=300
        val catViews=ArrayList<ImageView>()
        for (i in 0 .. 1){
            array.shuffle(Random)
            for (j in 0..7){
                val cat = ImageView(applicationContext)
                cat.setImageResource(R.drawable.sprite_0009)//array[j])//R.drawable.sprite_0009)
                cat.layoutParams=params
                cat.tag=when(array[j]){
                        R.drawable.sprite_0001 -> "1"
                        R.drawable.sprite_0002 -> "2"
                        R.drawable.sprite_0003 -> "3"
                        R.drawable.sprite_0004 -> "4"
                        R.drawable.sprite_0005 -> "5"
                        R.drawable.sprite_0006 -> "6"
                        R.drawable.sprite_0007 -> "7"
                        R.drawable.sprite_0008 -> "8"
                        else -> "9"
                    }
                cat.setOnClickListener{colorListener(cat)}
                catViews.add(cat)
            }
        }

        val rows=Array(4) { LinearLayout(applicationContext) }

        var count=0
        for (view in catViews){
            val row:Int=count/4
            rows[row].addView(view)
            count+=1
        }
        for (row in rows){
            layout.addView(row)
        }

        setContentView(layout)
    }

    private fun changeImage(imageView:ImageView, imageResId:Int){
        if (imageResId==0){
            imageView.setImageResource(R.drawable.sprite_0009)
        } else {
            imageView.setImageResource(when (imageView.tag) {
            "1" -> R.drawable.sprite_0001
            "2" -> R.drawable.sprite_0002
            "3" -> R.drawable.sprite_0003
            "4" -> R.drawable.sprite_0004
            "5" -> R.drawable.sprite_0005
            "6" -> R.drawable.sprite_0006
            "7" -> R.drawable.sprite_0007
            "8" -> R.drawable.sprite_0008
            else -> R.drawable.sprite_0009
        })
        }
    }
    private fun resetImages(){
        if(firstSelected!= null &&  secondSelected!=null){
            if (firstSelected!!.tag!= secondSelected!!.tag){
                firstSelected?.let{
                    it.setImageResource(R.drawable.sprite_0009)
                }
                secondSelected?.let{
                    it.setImageResource(R.drawable.sprite_0009)
                }
            } else {
                solved+=1
                firstSelected!!.visibility=View.INVISIBLE
                secondSelected!!.visibility=View.INVISIBLE
                if (solved>=8){
                    val show = Toast.makeText(this, "Вы победили!", Toast.LENGTH_LONG).show()
                    return
                }
            }
            firstSelected=null
            secondSelected=null
        }
    }
    private fun colorListener(cat:ImageView) {
        if(firstSelected==null){
            firstSelected=cat
            changeImage(cat,1)
        } else if(secondSelected==null && cat!=firstSelected){
            secondSelected=cat
            changeImage(cat,1)
        }
        if(firstSelected!=null && secondSelected!=null){

            Handler(Looper.getMainLooper()).postDelayed({
                resetImages()
            }, 2000)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemorinaTheme {
        Greeting("Android")
    }
}