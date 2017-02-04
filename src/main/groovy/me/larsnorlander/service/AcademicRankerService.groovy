package me.larsnorlander.service

import me.larsnorlander.exception.IncompletePreferencesException
import me.larsnorlander.exception.NullGradesException
import me.larsnorlander.config.MultiplierConfig
import me.larsnorlander.config.TracksConfig
import me.larsnorlander.exception.NullPreferencesException
import me.larsnorlander.model.AcademicRankerResponse
import me.larsnorlander.model.Requirements
import me.larsnorlander.model.Strand
import me.larsnorlander.model.StudentProfile
import me.larsnorlander.model.StudentStrengths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AcademicRankerService {

    @Autowired
    private MultiplierConfig multiplierConfig

    @Autowired
    private TracksConfig strandConfig

    AcademicRankerResponse rankStrands(StudentProfile profile) {
        checkErrors profile

        StudentStrengths strengths = new StudentStrengths(profile)
        Map<String, Strand> strands = new HashMap<>()

        strandConfig.academic.each { String strand, Map<String, List<String>> values ->
            Requirements requirements = new Requirements(values.subjects, values.ncae)
            Strand strandObject = new Strand(requirements, strengths, profile.preference.indexOf(strand))
            strands.put strand, strandObject
        }

        computeScoreFor 'grades', strands
        if (profile.ncae) computeScoreFor 'ncae', strands
        if (profile.awards) computeScoreFor 'awards', strands

        new AcademicRankerResponse(strengths, profile.preference, strands)
    }

    private computeScoreFor(String category, Map<String, Strand> strands) {
        strands.sort { a, b ->
            a.value.setResults[category].intersect.size() <=> b.value.setResults[category].intersect.size() ?:
                    b.value.setResults[category].difference.size() <=> a.value.setResults[category].difference.size() ?:
                            b.value.preferenceIndex <=> a.value.preferenceIndex
        }.eachWithIndex { k, v, i -> strands.get(k).score += (i + 1) * (int) multiplierConfig.getProperty(category) }
    }

    private checkErrors(StudentProfile profile){
        if(!profile.grades) throw new NullGradesException()
        if(!profile.preference) throw new NullPreferencesException()
        strandConfig.academic.each {
            if (!profile.preference.contains(it.key)) throw new IncompletePreferencesException()
        }
    }
}
