package me.larsnorlander

import com.fasterxml.jackson.databind.ObjectMapper
import me.larsnorlander.model.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class AcademicRankerApplication {

    @Autowired
    Ranker ranker

    static void main(String[] args) {
        SpringApplication.run AcademicRankerApplication, args
    }

    @RequestMapping('/')
    def index(@RequestBody String data) {
        new ResponseEntity<Map>(ranker.rank(new ObjectMapper().readValue(data, Request)), HttpStatus.OK)
    }

}
