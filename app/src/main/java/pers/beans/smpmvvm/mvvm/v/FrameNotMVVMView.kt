package pers.beans.smpmvvm.mvvm.v

import androidx.viewbinding.ViewBinding

/**
 * @Class: FrameNotMVVMView
 * @Remark: View层基类抽象
 */
interface FrameNotMVVMView<VB : ViewBinding> {
    /**
     * 初始化View
     */
    fun VB.initView()
}