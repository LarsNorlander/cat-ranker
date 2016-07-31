package me.larsnorlander

import me.larsnorlander.config.MultiplierConfig
import me.larsnorlander.config.StrandConfig
import me.larsnorlander.model.Request
import me.larsnorlander.model.Strand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Ranker {

    private Map<String, Strand> strands

    private List<String> sGrades, sNcae, sAwards

    @Autowired
    StrandConfig strandConfig

    @Autowired
    MultiplierConfig multiplierConfig

    Ranker() {

    }

    void initializeStrands() {
        strands = [:]
        ['stem', 'gas', 'abm', 'humss'].each {
            strands << [("$it".toString()): new Strand(
                    strandConfig.subjects.get(it),
                    strandConfig.ncae.get(it)
            )]
        }
    }

    Map rank(Request data) {
        initializeStrands()

        sGrades = getStrengths(data.grades)
        sNcae = data.ncae ? getStrengths(data.ncae) : null
        sAwards = data.awards ? getStrengths(data.awards) : null

        strands.each { k, v ->
            v.setProperties(sGrades, sNcae, sAwards, data.preference.indexOf(k))
        }

        Map gradesRank = strands.sort { a, b ->
            b.value.gradesIntersect.size() <=> a.value.gradesIntersect.size() ?:
                    a.value.gradesDifference.size() <=> b.value.gradesDifference.size() ?:
                            a.value.preferenceIndex <=> b.value.preferenceIndex
        }

        Map ncaeRank = data.ncae ? strands.sort { a, b ->
            b.value.ncaeIntersect.size() <=> a.value.ncaeIntersect.size() ?:
                    a.value.ncaeDifference.size() <=> b.value.ncaeDifference.size() ?:
                            a.value.preferenceIndex <=> b.value.preferenceIndex
        } : null

        Map awardsRank = data.awards ? strands.sort { a, b ->
            b.value.awardsIntersect.size() <=> a.value.awardsIntersect.size() ?:
                    a.value.awardsDifference.size() <=> b.value.awardsDifference.size() ?:
                            a.value.preferenceIndex <=> b.value.preferenceIndex
        } : null

        reverseMap(gradesRank).eachWithIndex { k, v, i ->
            strands["$k"].score += (i + 1) * multiplierConfig.grades
        }
        if (ncaeRank) reverseMap(ncaeRank).eachWithIndex { k, v, i ->
            strands["$k"].score += (i + 1) * multiplierConfig.ncae
        }
        if (awardsRank) reverseMap(awardsRank).eachWithIndex { k, v, i ->
            strands["$k"].score += (i + 1) * multiplierConfig.awards
        }

        strands = strands.sort { a, b ->
            b.value.score <=> a.value.score ?: a.value.preferenceIndex <=> b.value.preferenceIndex
        }

        Map responseMap = [strengths: [grades: sGrades], preference: data.preference, ranking:
        strands]

        if (data.ncae) responseMap.strengths << [ncae : sNcae]
        if (data.awards) responseMap.strengths << [awards: sAwards]

        return responseMap;
    }

    private static List<String> getStrengths(Map<String, Double> data) {
        List<String> strengths = []
        double average = 0
        data.each { k, v -> average += v }
        average /= data.size()
        data.each { k, v -> if (average <= v) strengths << k }
        strengths
    }

    private static Map reverseMap(Map map) {
        Map result = [:]
        map.reverseEach {
            result << it
        }
        result
    }
}
