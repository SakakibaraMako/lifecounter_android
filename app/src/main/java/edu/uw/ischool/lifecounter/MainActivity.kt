package edu.uw.ischool.lifecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RowHolder(row : View, label: TextView) : RecyclerView.ViewHolder(row) {
    private var player : TextView
    private var lifeLabel : TextView
    private var btnPlus1 : Button
    private var btnPlus5 : Button
    private var btnMinus1 : Button
    private var btnMinus5 : Button
    private var life = 20
    private val lifeTemplate = "Life: %d"
    private val loseTemplate = "%s LOSES!"
    private var loseLabel : TextView


    init {
        player = row.findViewById(R.id.player)
        lifeLabel = row.findViewById(R.id.life)
        btnPlus1 = row.findViewById(R.id.btn_plus1)
        btnPlus5 = row.findViewById(R.id.btn_plus5)
        btnMinus1 = row.findViewById(R.id.btn_minus1)
        btnMinus5 = row.findViewById(R.id.btn_minus5)
        loseLabel = label
    }

    fun bindModel(player: String) {
        this.player.text = player
        lifeLabel.text = String.format(lifeTemplate, life)
        btnPlus1.setOnClickListener {
            life += 1
            lifeLabel.text = String.format(lifeTemplate, life)
        }
        btnPlus5.setOnClickListener {
            life += 5
            lifeLabel.text = String.format(lifeTemplate, life)
        }
        btnMinus1.setOnClickListener {
            life -= 1
            if (isLost()) playerLost(player)
            else lifeLabel.text = String.format(lifeTemplate, life)
        }
        btnMinus5.setOnClickListener {
            life -= 5
            if (isLost()) playerLost(player)
            else lifeLabel.text = String.format(lifeTemplate, life)
        }
    }

    fun isLost(): Boolean {
        return life <= 0
    }

    fun playerLost(player : String) {
        life = 0
        lifeLabel.text = String.format(lifeTemplate, life)
        btnPlus1.isEnabled = false
        btnPlus5.isEnabled = false
        btnMinus1.isEnabled = false
        btnMinus5.isEnabled = false
        loseLabel.text = String.format(loseTemplate, player)
        loseLabel.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed({
            loseLabel.visibility = View.INVISIBLE
        }, 1 * 1000)
    }
}

class PlayerAdapter(val activity: MainActivity, var players: MutableList<String>, var label: TextView) : RecyclerView.Adapter<RowHolder>() {

    override fun getItemCount(): Int { return players.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder(activity.layoutInflater.inflate(R.layout.row, parent, false), label)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindModel(players[position])
    }

}

class MainActivity : AppCompatActivity() {

    lateinit var label : TextView
    lateinit var view : RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    var players : MutableList<String> = ArrayList()
    val nameTemplate = "Player %d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var number = intent.getIntExtra("number", 0)
        Log.i("Main", number.toString())
        for (i in 1..number) {
            players.add(String.format(nameTemplate, i))
        }

        view = findViewById(R.id.view)
        label = findViewById(R.id.loseLabel)

        layoutManager = LinearLayoutManager(this)
        view.layoutManager = layoutManager

        var adapter = PlayerAdapter(this, players, label)
        view.adapter = adapter

    }
}