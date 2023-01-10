package pers.beans.smpmvvm.ktx

import android.text.InputFilter
import android.widget.EditText

/**
 * @Class: EditTextKtx
 * @Remark: EditText相关扩展方法
 */

/**
 * 过滤掉空格和回车
 */
fun EditText.filterBlankAndCarriageReturn() {
    this.filters =
        arrayOf(InputFilter { source, _, _, _, _, _ -> if (source == " " || source == "\n") "" else null })
}