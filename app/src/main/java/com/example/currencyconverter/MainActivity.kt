package com.example.currencyconverter

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.room.RateRepository
import com.example.currencyconverter.util.MyAdapter
import com.example.currencyconverter.util.NetworkChangeReceiver
import com.example.currencyconverter.util.Receiver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(),
    Receiver {

    private var isOnline: Boolean = false
    private lateinit var adapter: MyAdapter
    private lateinit var networkChange: NetworkChangeReceiver
    private lateinit var rateRepository: RateRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Network dəyişikliyini dinləmək üçün Broadcast Receiver
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        networkChange = NetworkChangeReceiver()
        this.registerReceiver(networkChange, filter)
        rateRepository = RateRepository(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyAdapter(recyclerView)
        recyclerView.adapter = adapter

        val repeatEveryXSecond = Observable.interval(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        repeatEveryXSecond.subscribe({
            if (isOnline) rateRepository.getRatesFromServer()?.observe(this, Observer {
                val rates = it.body() ?: ArrayList<RateItem>()
                lifecycleScope.launch {
                    rateRepository.insertAll(rates)
                }
            })

        }, {
            Log.e("myError", it.message.toString())
        })

        rateRepository.rates.observe(this, {
            adapter.setListAndNotify(it)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(networkChange)
    }

    override fun letMeKnowWhenNetworkStateChanged(isOnline: Boolean) {
        this.isOnline = isOnline
    }
}