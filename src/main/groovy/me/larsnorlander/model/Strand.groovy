package me.larsnorlander.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

class Strand {

    private Requirements requirements

    private Map<String, SetResults> setResults

    private int preferenceIndex

    private int score

    Strand(Requirements requirements, StudentStrengths strengths, int preferenceIndex) {
        this.requirements = requirements
        this.setResults = new HashMap<>()
        this.setResults.grades = new SetResults(requirements.subjects, strengths.grades)
        this.setResults.ncae = strengths.ncae ? new SetResults(requirements.ncae, strengths.ncae) : null
        this.setResults.awards = strengths.awards ? new SetResults(requirements.subjects, strengths.awards) : null
        this.preferenceIndex = preferenceIndex
    }

    @JsonIgnore
    Map<String, SetResults> getSetResults(){
        return setResults
    }

    SetResults getGrades() {
        return setResults.grades
    }

    SetResults getNcae() {
        return setResults.ncae
    }

    SetResults getAwards() {
        return setResults.awards
    }

    @JsonProperty('preference_index')
    int getPreferenceIndex() {
        return preferenceIndex
    }

    int getScore() {
        return score
    }

    int setScore(int score){
        this.score = score
    }
}
