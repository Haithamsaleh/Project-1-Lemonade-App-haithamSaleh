package com.example.lemonade
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    /**
     * DO NOT ALTER ANY VARIABLE OR VALUE NAMES OR THEIR INITIAL VALUES.
     *
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has been drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)

        setViewElements()
        lemonImage!!.setOnClickListener {

            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {

            showSnackbar()
            false
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
//        var picking_sound = MediaPlayer.create(this, R.raw.test_sound);
//        var squeez_sound = MediaPlayer.create(this, R.raw.test_sound);
//        var drink_sound = MediaPlayer.create(this, R.raw.test_sound);

        when(lemonadeState){
           SELECT -> {
               lemonadeState =SQUEEZE
               lemonSize =lemonTree.pick()
               squeezeCount =0
//               picking_sound.start()

           }
           SQUEEZE -> {
               squeezeCount++
               lemonSize--
//               squeez_sound.start()

               lemonadeState = if (lemonSize == 0) {
                   DRINK

               }

               else {
                   SQUEEZE

               }

           }
           DRINK -> {
               lemonadeState = RESTART
               lemonSize =-1
//               drink_sound.start()

           }
           RESTART ->{
               lemonadeState =SELECT
           }
       }
setViewElements()
    }


    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        var textSqueezeCount: TextView = findViewById(R.id.squeeze_Count)


        when(lemonadeState){
            SELECT -> {
                textAction.text = resources.getText(R.string.lemon_select)
                lemonImage?.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE ->{


                textAction.text = resources.getText(R.string.lemon_squeeze)
                lemonImage?.setImageResource(R.drawable.lemon_squeeze)
                if (squeezeCount == 0 && lemonSize == 4){
                    textSqueezeCount.text = "oh big one!! "
                }
//                else if (squeezeCount == 0){
//                }

//                else {
//                    textSqueezeCount.text = ("Squeeze count: $squeezeCount,\n keep squeezing!")
//                }
                else {
                      textSqueezeCount.text = ""

                }


//                val toast = Toast.makeText(this,"Squeeze count: $squeezeCount, keep squeezing!", Toast.LENGTH_SHORT)
//                toast.show()
            }
            DRINK -> {
                textAction.text = resources.getText(R.string.lemon_drink)

                lemonImage?.setImageResource(R.drawable.lemon_drink)
                textSqueezeCount.text = ""

            }
            RESTART -> {
                textAction.text = resources.getText(R.string.lemon_empty_glass)

                lemonImage?.setImageResource(R.drawable.lemon_restart)
            }
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
