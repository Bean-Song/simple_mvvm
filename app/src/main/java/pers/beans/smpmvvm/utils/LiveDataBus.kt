package pers.beans.smpmvvm.utils

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import pers.beans.smpmvvm.utils.LiveDataBus.ObserverWrapper
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.collections.set


/**
 * @ProjectName:    AndroidBaseMVVM
 * @Package:        com.beans.base.utils
 * @ClassName:      LiveDataBus
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2022/12/21 4:27 下午
 * @Version:        1.0
 */
class LiveDataBus constructor() : Parcelable {

    // 消息总线
    private var bus: MutableMap<String, BusMutableLiveData<Any>> = HashMap()

    constructor(parcel: Parcel) : this()

    // 单例模式（静态内部类法）
    private object SingleInstance {
        val mInstance = LiveDataBus()
    }

    fun get(): LiveDataBus {
        return SingleInstance.mInstance
    }

    fun <T> with(key: String, type: Class<T>): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData()
        }

        return bus[key] as MutableLiveData<T>
    }

    fun with(key: String): MutableLiveData<Any> {
        return with<Any>(key, Any::class.java)
    }

    private class ObserverWrapper<T> constructor(var observer: Observer<T>) : Observer<T> {

        override fun onChanged(t: T) {
            if (observer != null) {
                if (isCallOnObserve()) {
                    return
                }
                observer.onChanged(t)
            }
        }

        private fun isCallOnObserve(): Boolean {
            val stackTrace = Thread.currentThread().stackTrace
            if (stackTrace != null && stackTrace.isNotEmpty()) {
                for (element in stackTrace) {
                    if ("android.arch.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                        return true
                    }
                }
            }
            return false
        }

    }

    private class BusMutableLiveData<T> : MutableLiveData<T>() {
        private var observerMap : MutableMap<Observer<in T>, Observer<in T>>  = HashMap()

        override fun observeForever(observer: Observer<in T>) {
            if (observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            super.observeForever(observer)
        }

        override fun removeObserver(observer: Observer<in T>) {

            var realObserver: Observer<in T>? = if (observerMap.containsKey(observer)) {
                observerMap.remove(observer)
            } else {
                observer
            }

            super.removeObserver(realObserver!!)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                // 设置observer的version和LiveData一致
                hook(observer)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }


        @Throws(java.lang.Exception::class)
        private fun hook(observer: Observer<in T>) {

            // 获取livedata的class对象
            val classLiveData = LiveData::class.java
            // 获取   LiveData类的mObservers对象 （Map对象）的 Field对象
            val fieldObservers: Field = classLiveData.getDeclaredField("mObservers")
            // 将mObservers 的private设置为 public
            fieldObservers.isAccessible = true
            //  获取当前livedata的mObservers对象(map)
            val objectObservers: Any = fieldObservers.get(this)
            // 拿到mObservers（map）的class对象
            val classObservers: Class<*> = objectObservers.javaClass
            // 通过map的class对象拿到get（）的method对象
            val methodGet: Method = classObservers.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            // 通过map 的 get Method对象 拿到值 （Entry）  （arg1：map ，arg2：key ）
            val objectWrapperEntry: Any = methodGet.invoke(objectObservers, observer)
            // 拿到wrapper
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("Wrapper can not be bull!")
            }
            // 反射wrapper对象
            val classObserverWrapper: Class<*> = objectWrapper.javaClass.superclass
            // 拿到wrapper的version
            val fieldLastVersion: Field = classObserverWrapper.getDeclaredField("mLastVersion")
            fieldLastVersion.isAccessible = true
            //get livedata's version
            val fieldVersion: Field = classLiveData.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion: Any = fieldVersion.get(this)
            //set wrapper's version
            fieldLastVersion.set(objectWrapper, objectVersion)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LiveDataBus> {
        override fun createFromParcel(parcel: Parcel): LiveDataBus {
            return LiveDataBus(parcel)
        }

        override fun newArray(size: Int): Array<LiveDataBus?> {
            return arrayOfNulls(size)
        }
    }
}


/**
// 注册LiveDataBus
LiveDataBus.get().getChanel("yingyingying").observe(this, new android.arch.lifecycle.Observer<Object>() {
@Override
public void onChanged(@Nullable Object o) {
Toast.makeText(getApplicationContext(),""+o.toString(),Toast.LENGTH_LONG).show();
}
});



tv_hello.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
LiveDataBus.get().getChanel("yingyingying",String.class).postValue("嘤嘤嘤");
}
});
 */