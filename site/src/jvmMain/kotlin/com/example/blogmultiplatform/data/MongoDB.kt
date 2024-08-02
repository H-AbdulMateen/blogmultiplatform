package com.example.blogmultiplatform.data

import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.util.Constants.DATABASE_NAME
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext

@InitApi
fun initMongoDb(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB())
}

class MongoDB : MongoRepository {
    private val client = MongoClient.create(System.getenv("MONGODB_URI"))
    private val database = client.getDatabase(DATABASE_NAME)
    private val userCollection = database.getCollection<User>("user")

    override fun checkUserExistence(): User? {
    TODO("Not yet implemented")
    }
}