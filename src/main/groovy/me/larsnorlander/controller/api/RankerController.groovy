package me.larsnorlander.controller.api

import com.fasterxml.jackson.databind.ObjectMapper
import me.larsnorlander.service.RankerService
import me.larsnorlander.model.StudentProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by larsjosephnorlander on 9/30/16.
 * The controller that exposes the methods for the ranker service.
 */
@RestController
@RequestMapping('api/ranker')
class RankerController {

    @Autowired
    RankerService ranker

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Map> process(@RequestBody String data) {
        new ResponseEntity<Map>(ranker.rankStrands(new ObjectMapper().readValue(data, StudentProfile)), HttpStatus.OK)
    }

}
