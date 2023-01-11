package pers.beans.smpmvvm.page.main.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import pers.beans.smpmvvm.R
import pers.beans.smpmvvm.bean.GitEvents
import javax.inject.Inject

/**
 * @ProjectName:    My Simple Demo
 * @Package:        pers.beans.smpmvvm.page.main.adapter
 * @ClassName:      EventsAdapter
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2023/1/10 2:30 下午
 * @Version:        1.0
 */
class EventsAdapter @Inject constructor() :
    BaseQuickAdapter<GitEvents, BaseViewHolder>(R.layout.home_item_article) {
    override fun convert(holder: BaseViewHolder, item: GitEvents) {
        holder.setText(R.id.vIdTv, item.actor?.login)
        holder.setText(R.id.vAuthorTv, item.type)
        holder.setText(R.id.vDateTv, item.created_at)
        holder.setText(R.id.vUrlTv, item.actor?.url)
        val avatar = holder.getView<ImageView>(R.id.avatar)
        Glide.with(context).load(item.actor?.avatar_url).into(avatar)
    }

}








