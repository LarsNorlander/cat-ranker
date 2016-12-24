package me.larsnorlander.model
/**
 * Created by larsjosephnorlander on 12/24/16.
 */
class Requirements{

    private List<String> subjects

    private List<String> ncae

    Requirements(List<String> subjects, List<String> ncae) {
        this.subjects = subjects
        this.ncae = ncae
    }

    List<String> getSubjects() {
        return subjects
    }

    List<String> getNcae() {
        return ncae
    }

}
