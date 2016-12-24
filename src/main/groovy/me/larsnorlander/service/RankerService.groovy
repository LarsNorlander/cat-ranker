package me.larsnorlander.service

import me.larsnorlander.config.MultiplierConfig
import me.larsnorlander.config.StrandConfig
import me.larsnorlander.model.Requirements
import me.larsnorlander.model.Strand
import me.larsnorlander.model.StudentProfile
import me.larsnorlander.model.StudentStrengths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RankerService {

    private static final List<String> STRANDS = ['stem', 'gas', 'abm', 'humss']

    @Autowired
    private MultiplierConfig multiplierConfig

    @Autowired
    private StrandConfig strandConfig

    Map<String, ?> rankStrands(StudentProfile profile) {
        StudentStrengths strengths = new StudentStrengths(profile)
        Map<String, Strand> strands = new HashMap<>()

        STRANDS.each { String strand ->
            Requirements requirements = new Requirements(
                    strandConfig.subjects[strand],
                    strandConfig.ncae[strand]
            )
            Strand strandObject = new Strand(
                    requirements,
                    strengths,
                    profile.preference.indexOf(strand)
            )
            strands.put(strand, strandObject)
        }

        strands.sort { a, b ->
            a.value.grades.intersect.size() <=> b.value.grades.intersect.size() ?:
                    b.value.grades.difference.size() <=> a.value.grades.difference.size() ?:
                            b.value.preferenceIndex <=> a.value.preferenceIndex
        }.eachWithIndex { k, v, i -> strands.get(k).score += (i + 1) * multiplierConfig.grades }

        if (profile.ncae) strands.sort { a, b ->
            a.value.ncae.intersect.size() <=> b.value.ncae.intersect.size() ?:
                    b.value.ncae.difference.size() <=> a.value.ncae.difference.size() ?:
                            b.value.preferenceIndex <=> a.value.preferenceIndex
        }.eachWithIndex { k, v, i -> strands.get(k).score += (i + 1) * multiplierConfig.ncae }

        if (profile.awards) strands.sort { a, b ->
            a.value.awards.intersect.size() <=> b.value.awards.intersect.size() ?:
                    b.value.awards.difference.size() <=> a.value.awards.difference.size() ?:
                            b.value.preferenceIndex <=> a.value.preferenceIndex
        }.eachWithIndex { k, v, i -> strands.get(k).score += (i + 1) * multiplierConfig.awards }

        [
                strengths : strengths,
                preference: profile.preference,
                ranking   : strands.sort { a, b -> b.value.score <=> a.value.score ?: a.value.preferenceIndex <=> b.value.preferenceIndex }
        ]
    }
}
