package demo

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.HttpClientErrorException

data class CatData(
    @JsonProperty("_id")
    val id: String,
    @JsonProperty("mimetype")
    val mimeType: String,
    val tags: List<String>,
)

class CatsService(baseUrl: String) {
    private val restTemplate = RestTemplateBuilder()
        .rootUri(baseUrl).build()

    fun randomCat(tag: String?): CatData? =
        try {
            // TODO
//            restTemplate.getForObject<CatData>(
//                "/cat${if (tag == null) "" else "/$tag"}?json=true",
//            )
            restTemplate.getForObject(
                "/cat${if (tag == null) "" else "/$tag"}?json=true",
                CatData::class.java
            )
                ?: throw Exception("Unexpected null as response")
        } catch (e: HttpClientErrorException.NotFound) {
            null
        }
}