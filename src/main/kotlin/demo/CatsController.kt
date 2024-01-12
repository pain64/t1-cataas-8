package demo

import com.fasterxml.jackson.annotation.JsonTypeInfo
import demo.dao.CatsDao
import demo.entity.Cat
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Schema(
    oneOf = [
        RandomCatResponse.Ok::class,
        RandomCatResponse.NotFound::class,
    ],
    discriminatorProperty = "type"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
sealed class RandomCatResponse {
    data class Ok(
        val id: String,
        val mimeType: String,
        val tags: List<String>,
        // val comments: List<String>,
    ) : RandomCatResponse()

    data object NotFound : RandomCatResponse()
}

@RestController
@RequestMapping("/api/v1")
class CatsController(
    val catsService: CatsService,
    val catsDao: CatsDao,
) {

    @GetMapping(path = ["/randomCat", "/randomCat/{tag}"])
    @ResponseBody
    fun getRandomCat(@PathVariable(required = false) tag: String?) =
        catsService.randomCat(tag)?.let { cd ->
            catsDao.save(
                Cat(cd.id, cd.mimeType, cd.tags.joinToString(separator = ","))
            )

            RandomCatResponse.Ok(
                cd.id, cd.mimeType, cd.tags, // comments
            )
        } ?: RandomCatResponse.NotFound

}