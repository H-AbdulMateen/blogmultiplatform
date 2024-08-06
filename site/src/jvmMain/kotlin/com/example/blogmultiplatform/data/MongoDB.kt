package com.example.blogmultiplatform.data

import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.util.Constants.DATABASE_NAME
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.firstOrNull

@InitApi
fun initMongoDb(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context = context))
}

class MongoDB(private val context: InitApiContext) : MongoRepository {
    //For testing with a localhost
    private val client = MongoClient.create()
    //For remote server
    //    private val client = MongoClient.create(System.getenv("MONGODB_URI"))
    private val database = client.getDatabase(DATABASE_NAME)
    private val userCollection = database.getCollection<User>("user")

    override suspend fun checkUserExistence(user: User): User? {
    return try {
        userCollection.find(
            Filters.and(
                Filters.eq(User::username.name, user.username),
                Filters.eq(User::password.name, user.password)
            )
        ).firstOrNull()
    }catch (ex: Exception){
        context.logger.error(ex.message.toString())
        null
    }
    }
}