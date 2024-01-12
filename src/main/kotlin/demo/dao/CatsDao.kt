package demo.dao

import demo.entity.Cat
import org.springframework.data.repository.CrudRepository

interface CatsDao : CrudRepository<Cat, String> {
}