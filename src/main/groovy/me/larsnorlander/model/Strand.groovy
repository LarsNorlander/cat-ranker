package me.larsnorlander.model

import com.fasterxml.jackson.annotation.JsonProperty

class Strand {

    private Requirements requirements

    private Category grades

    private Category ncae

    private Category awards

    private int preferenceIndex

    private int score

    Strand(Requirements requirements, StudentStrengths strengths, int preferenceIndex) {
        this.requirements = requirements
        this.grades = new Category(requirements.subjects, strengths.grades)
        this.ncae = strengths.ncae ? new Category(requirements.ncae, strengths.ncae) : null
        this.awards = strengths.awards ? new Category(requirements.subjects, strengths.awards) : null
        this.preferenceIndex = preferenceIndex
    }

    Category getGrades() {
        return grades
    }

    Category getNcae() {
        return ncae
    }

    Category getAwards() {
        return awards
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
