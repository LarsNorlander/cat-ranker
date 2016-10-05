package me.larsnorlander

import com.fasterxml.jackson.databind.ObjectMapper
import me.larsnorlander.model.Request
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
class RankerController {

    @Autowired
    RankerService ranker

    @RequestMapping(value = '/', method = RequestMethod.GET)
    String  index(){
        return "A landing page is on the way. :3"
    }

    @CrossOrigin
    @RequestMapping(value = '/', method = RequestMethod.POST)
    ResponseEntity<Map> process(@RequestBody String data) {
        new ResponseEntity<Map>(ranker.rank(new ObjectMapper().readValue(data, Request)), HttpStatus.OK)
    }

}
