package me.larsnorlander.model

/**
 * Created by larsjosephnorlander on 7/23/16.
 * The request Object.
 */
class Request {

    Map<String, Double> grades

    Map<String, Double> ncae

    Map<String, Double> awards

    List<String> preference
}


//{
//    "subjects":{
//    "Math":92,
//    "Science":93
//},
//    "ncae":{
//    "Scientific Ability":92
//},
//    "awards":{
//    "Math":2
//},
//    "preference":[
//        "stem",
//        "gas",
//        "abm",
//        "humms"
//]
//}