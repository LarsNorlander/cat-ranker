package me.larsnorlander.model

/**
 * Created by larsjosephnorlander on 12/24/16.
 */
class StudentStrengths {
    private List<String> grades

    private List<String> ncae

    private List<String> awards

    StudentStrengths(StudentProfile profile) {
        this.grades = getStrengths(profile.grades)
        this.ncae = getStrengths(profile.ncae)
        this.awards = getStrengths(profile.awards)
    }

    private static List<String> getStrengths(Map<String, Double> category) {
        if (!category) return null
        List<String> strengths = []
        double average = 0
        category.each { k, v -> average += v }
        average /= category.size()
        category.each { k, v -> if (average <= v) strengths << k }
        strengths
    }

    List<String> getGrades() {
        return grades
    }

    List<String> getNcae() {
        return ncae
    }

    List<String> getAwards() {
        return awards
    }
}
