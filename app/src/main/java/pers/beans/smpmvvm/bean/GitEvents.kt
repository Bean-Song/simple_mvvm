package pers.beans.smpmvvm.bean

/**
 * @ProjectName:    Simple Mvvm Demo
 * @Package:        pers.beans.smpmvvm.bean
 * @ClassName:      GitEvents
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2023/1/9 4:42 下午
 * @Version:        1.0
 */
data class GitEvents(
    var actor: Actor?,
    var created_at: String?,
    var id: String?,
    var type: String?
)

data class Actor(
    var avatar_url: String?,
    var display_login: String?,
    var gravatar_id: String?,
    var id: Int?,
    var login: String?,
    var url: String?
)