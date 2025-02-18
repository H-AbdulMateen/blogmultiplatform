package com.example.blogmultiplatform.api

import com.example.blogmultiplatform.data.MongoDB
import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.models.UserWithoutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val userRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        val user = userRequest?.let { context.data.getValue<MongoDB>().checkUserExistence(
            User(username = it.username, password = hashPassword(it.password))
        ) }
        if (user != null){
            context.res.setBodyText(Json.encodeToString(UserWithoutPassword(_id = user._id, username = user.username)))
        }else{
            context.res.setBodyText(Json.encodeToString(Exception("User doesn't exist.")))
        }


    }catch (ex: Exception) {
        context.res.setBodyText(Json.encodeToString(Exception(ex.message)))
    }
}
@Api(routeOverride = "checkuserid")
suspend fun checkUserId(context: ApiContext){
    try {
        val idRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<String>(it) }
        val result = idRequest?.let { context.data.getValue<MongoDB>().checkUserId(it) }
        if (result != null){
            context.res.setBodyText(Json.encodeToString(result))
        }else{
            context.res.setBodyText(Json.encodeToString(false))
        }
    }catch (ex: Exception){
        context.res.setBodyText(Json.encodeToString<Boolean>(false))
    }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(password.toByteArray())
    val hexString = StringBuffer()
    for (byte in hashedBytes) {
        hexString.append(String.format("%02x", byte))
    }
    return hexString.toString()
}