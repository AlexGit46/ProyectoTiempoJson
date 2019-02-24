package net.azarquiel.darksky.view

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*
import net.azarquiel.darksky.R
import net.azarquiel.darksky.adapter.CustomAdapter
import net.azarquiel.darksky.model.Result
import net.azarquiel.darksky.util.Util
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG="paco"
    }

    private lateinit var result: Result
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pd=indeterminateProgressDialog("Cargando datos...")
        pd.show()
        loadData()

        title="El tiempo en El Álamo"
    }

    private fun loadData() {
        doAsync {
            val json = URL("https://api.darksky.net/forecast/21259f9de3537b4f719c53580fa39c3a/40.2328447,-3.9873046?lang=es&exclude=minutely,hourly,alerts,flags").readText()
            result = Gson().fromJson(json, Result::class.java)
            uiThread {
                pd.hide()
                ui()
            }
        }

    }

    private fun ui() {
        Log.d(TAG,result.currently.toString())
        Log.d(TAG,result.daily.data.toString())
        showCurrent()
        showRV()
    }

    private fun showCurrent() {
        tvPronoCurrent.text = result.currently.summary
        Picasso.with(this).load("https://darksky.net/images/weather-icons/${result.currently.icon}.png").into(ivCurrent)
        tvPopCurrent.text = "${result.currently.precipProbability}%"
        tvTempCurrent.text = "${Util.farToCel(result.currently.temperature)}º"
    }

    private fun showRV() {
        val adapter = CustomAdapter(this,R.layout.row,result.daily.data)
        rvDias.layoutManager = LinearLayoutManager(this)
        rvDias.adapter = adapter
    }
}
