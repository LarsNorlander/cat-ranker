package me.larsnorlander.controller.api

import me.larsnorlander.service.AcademicRankerService
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
@CrossOrigin
@RestController
@RequestMapping('api/ranker')
@SuppressWarnings("GroovyUnusedDeclaration")
class RankerController {

    @Autowired
    AcademicRankerService academicRankerService

    @RequestMapping(value = 'academic', method = RequestMethod.POST)
    ResponseEntity<Map> process(@RequestBody StudentProfile studentProfile) {
        new ResponseEntity(academicRankerService.rankStrands(studentProfile), HttpStatus.OK)
    }

}
