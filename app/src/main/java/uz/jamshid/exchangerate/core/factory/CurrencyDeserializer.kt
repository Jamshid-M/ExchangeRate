package uz.jamshid.exchangerate.core.factory

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import uz.jamshid.exchangerate.data.model.Rates
import uz.jamshid.exchangerate.data.model.RatesResponse
import java.lang.reflect.Type

class CurrencyDeserializer: JsonDeserializer<RatesResponse> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): RatesResponse {

        val jsonObject = json?.asJsonObject

        val outJson = jsonObject?.entrySet()?.toMutableList()?.get(0)?.value?.asJsonObject

        val out = outJson?.entrySet()?.toMutableList()?.get(0)?.value.toString().toFloat()

        val base = jsonObject?.entrySet()?.toMutableList()?.get(1)?.value.toString()

        return RatesResponse(base, Rates(out))
    }
}