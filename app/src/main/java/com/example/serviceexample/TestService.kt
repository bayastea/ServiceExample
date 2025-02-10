package com.example.serviceexample

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.concurrent.TimeUnit

class TestService: Service() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var value: String? = null

    /**
     * サービスが開始される時に呼び出される。
     * 呼び出し側で startService() を呼ぶと本メソッドが呼び出される。
     * 処理が完了したら呼び出し側で stopSelf() または stopService() を呼ぶことでサービスを停止する必要がある。
     * そうしないと Service は実行を延々と続けてしまう
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        value = intent?.getStringExtra("TEST_SERVICE'S KEY")
        // 初回実行
        handler.post(runnable)
        return START_STICKY
    }


    /**
     * 別のアプリコンポーネントがこのサービスにバインドするときに呼び出されます。
     */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    /**
     * サービスが最初に作成された時に呼び出される
     */
    override fun onCreate() {
        super.onCreate()
        runnable = Runnable {
            Log.d("TestService", value ?: "No value")
            // 5秒後に再度実行
            handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(5))
        }
    }

    /**
     * サービスが使用されなくなり破棄されると呼び出される
     * 今回はあえて何もしていない
     */
    override fun onDestroy() {
        super.onDestroy()
    }
}