package me.larsnorlander.model;

class Strand {

    private List<String> requiredSubjects, requiredNcae, gradesIntersect, ncaeIntersect,
            awardsIntersect, gradesDifference, ncaeDifference, awardsDifference

    private int preferenceIndex

    int score

    Strand(List<String> subjects, List<String> ncae) {
        this.requiredSubjects = subjects
        this.requiredNcae = ncae
    }

    void setProperties(List<String> grades, List<String> ncae, List<String> awards, int index) {
        gradesIntersect = this.requiredSubjects.intersect(grades)
        ncaeIntersect = this.requiredNcae.intersect(ncae)
        awardsIntersect = this.requiredSubjects.intersect(awards)

        gradesDifference = requiredSubjects - gradesIntersect
        ncaeDifference = requiredNcae - ncaeIntersect
        awardsDifference = requiredSubjects - awardsIntersect

        preferenceIndex = index
    }

    int getPreferenceIndex() {
        return preferenceIndex
    }

    List<String> getGradesIntersect() {
        return gradesIntersect
    }

    List<String> getNcaeIntersect() {
        return ncaeIntersect
    }

    List<String> getAwardsIntersect() {
        return awardsIntersect
    }

    List<String> getGradesDifference() {
        return gradesDifference
    }

    List<String> getNcaeDifference() {
        return ncaeDifference
    }

    List<String> getAwardsDifference() {
        return awardsDifference
    }
}
