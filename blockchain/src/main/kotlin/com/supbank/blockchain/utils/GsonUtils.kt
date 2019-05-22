package com.supbank.blockchain.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.security.PublicKey
import java.util.*

object GsonUtils {

    /**
     * Function to get a custom gson serializer / deserializer for the wallet
     */
    fun getWalletCustom() : Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(PublicKey::class.java, PublicKeySerializer())
        builder.excludeFieldsWithoutExposeAnnotation()
        return builder.create()
    }

    /**
     * Class helping us to parse public key
     */
    class PublicKeySerializer : JsonSerializer<PublicKey>, JsonDeserializer<PublicKey> {
        companion object {
            const val ENCODED_KEY_PROPERTY = "encodedKey"
        }

        override fun serialize(src: PublicKey?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            val pubKeyObject = JsonObject()
            pubKeyObject.addProperty(ENCODED_KEY_PROPERTY, Base64.getEncoder().encodeToString(src?.encoded))
            return pubKeyObject
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): PublicKey {
            val keyEncoded = json?.asJsonObject?.get(ENCODED_KEY_PROPERTY)?.asString
            return CryptoUtil.getPubKeyFromEncodedString(keyEncoded?:"")
        }
    }
}
