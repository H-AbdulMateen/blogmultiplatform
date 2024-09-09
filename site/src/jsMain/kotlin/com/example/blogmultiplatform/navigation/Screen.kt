package com.example.blogmultiplatform.navigation

import com.example.blogmultiplatform.models.Constants.UPDATED_PARAM

sealed class Screen(val route: String) {
    object AdminHome: Screen(route = "/admin/")
    object AdminLogin: Screen(route = "/admin/login")
    object AdminCreate: Screen(route = "/admin/create")
    object AdminMyPosts: Screen(route = "/admin/my-posts")
    object HomePage: Screen(route = "/")
    object AdminSuccess : Screen(route = "/admin/success"){
        fun postUpdated() = "/admin/success?${UPDATED_PARAM}=true"
    }

}