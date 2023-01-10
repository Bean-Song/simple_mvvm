package pers.beans.smpmvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pers.beans.smpmvvm.application.MyApplication

/**
 * 以顶层函数存在的常用工具方法
 * startPolling() -> 开启一个轮询
 * sendEvent() -> 发送普通EventBus事件
 * toastShow() -> Toast
 * isNetworkAvailable() -> 检查是否连接网络
 * aRouterJump() -> 阿里路由不带参数跳转
 */
/**************************************************************************************************/
/**
 * 使用 Flow 做的简单的轮询
 * 请使用单独的协程来进行管理该 Flow
 * Flow 仍有一些操作符是实验性的 使用时需添加 @InternalCoroutinesApi 注解
 * @param intervals 轮询间隔时间/毫秒
 * @param block 需要执行的代码块
 */
suspend fun startPolling(intervals: Long, block: () -> Unit) {
    flow {
        while (true) {
            delay(intervals)
            emit(0)
        }
    }
        .catch { Log.e("flow", "startPolling: $it") }
        .flowOn(Dispatchers.Main)
        .collect { block.invoke() }
}


/**************************************************************************************************/
private var mToast: Toast? = null

/**
 * Toast
 * Android 9.0之上 已做优化
 */
fun toastShow(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        Toast.makeText(MyApplication.context, text, duration).show()
    } else {
        if (mToast != null) {
            mToast?.setText(text)
            mToast?.show()
        } else {
            mToast = Toast.makeText(MyApplication.context, text, duration)
            mToast?.show()
        }
    }
}
/**************************************************************************************************/
/**
 * toast
 * @param msg String 文案
 * @param duration Int 时间
 */
fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtils.showToast(msg, duration)
}

/**
 * toast
 * @param msgId Int String资源ID
 * @param duration Int 时间
 */
fun toast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtils.showToast(msgId, duration)
}

/**************************************************************************************************/
/**
 * 判断是否连接网络
 */
@SuppressLint("MissingPermission")
fun isNetworkAvailable(): Boolean {
    val connectivityManager: ConnectivityManager? =
        MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager == null) {
        return false
    } else {
        val allNetworkInfo: Array<NetworkInfo>? = connectivityManager.allNetworkInfo
        if (allNetworkInfo != null && allNetworkInfo.isNotEmpty()) {
            allNetworkInfo.forEach {
                if (it.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
    }
    return false
}
