package com.zdtc.ue.school.yw.network.model

import java.io.Serializable

class LoginBean : Serializable {


    var token: String? = null
    var dept: DeptBean? = null
    var userInfo: UserInfoBean? = null
    var listMemu: List<String>? = null
    var listMemuAll: List<limitsBean>? = null


    class limitsBean : Serializable{
        var name:String?= null
        var perms:String?= null
    }

    class DeptBean : Serializable {
        var id: Int = 0
        var text: String? = null
        var state: StateBean? = null
        var isChecked: Boolean = false
        var attributes: Any? = null
        var parentId: String? = null
        var isHasParent: Boolean = false
        var isHasChildren: Boolean = false
        var children: List<ChildrenBeanX>? = null

        class StateBean : Serializable

        class ChildrenBeanX : Serializable {
            var id: String? = null
            var text: String? = null
            var state: StateBeanX? = null
            var isChecked: Boolean = false
            var attributes: Any? = null
            var parentId: String? = null
            var isHasParent: Boolean = false
            var isHasChildren: Boolean = false
            var children: List<ChildrenBean>? = null

            class StateBeanX : Serializable

            class ChildrenBean : Serializable {

                var id: String? = null
                var text: String? = null
                var state: StateBeanXX? = null
                var isChecked: Boolean = false
                var attributes: Any? = null
                var parentId: String? = null
                var isHasParent: Boolean = false
                var isHasChildren: Boolean = false
                var children: List<*>? = null

                class StateBeanXX : Serializable
            }
        }
    }

    class UserInfoBean : Serializable {
        var userId: Int = 0
        var username: String? = null
        var name: String? = null
        var password: String? = null
        var deptId: Int = 0
        var deptName: Any? = null
        var email: String? = null
        var tel: String? = null
        var mobile: String? = null
        var status: Int = 0
        var userIdCreate: Int = 0
        var gmtCreate: String? = null
        var gmtModified: String? = null
        var roleIds: Any? = null
        var sex: Int = 0
        var birth: String? = null
        var picId: Int = 0
        var liveAddress: String? = null
        var hobby: String? = null
        var province: String? = null
        var city: String? = null
        var district: String? = null
        var headPortrait: String? = null
    }

}
