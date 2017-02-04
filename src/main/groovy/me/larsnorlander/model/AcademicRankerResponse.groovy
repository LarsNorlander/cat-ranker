package me.larsnorlander.model

/**
 * Created by larsjosephnorlander on 2/4/17.
 */
class AcademicRankerResponse {
    StudentStrengths strengths

    List<String> preference

    Map<String, Strand> ranking

    AcademicRankerResponse(StudentStrengths strengths, List<String> preference, Map<String, Strand> strandMap) {
        this.strengths = strengths
        this.preference = preference
        this.ranking = strandMap.sort { a, b ->
            b.value.score <=> a.value.score ?: a.value.preferenceIndex <=> b.value.preferenceIndex
        }
    }
}
