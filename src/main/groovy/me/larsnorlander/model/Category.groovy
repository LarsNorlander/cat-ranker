package me.larsnorlander.model

/**
 * Created by larsjosephnorlander on 12/24/16.
 */
class Category {
    private List<String> intersect

    private List<String> difference

    Category(Iterable<String> requirements, Iterable<String> strengths){
        this.intersect = requirements.intersect(strengths)
        this.difference = requirements - this.intersect
    }

    List<String> getIntersect() {
        return intersect
    }

    List<String> getDifference() {
        return difference
    }
}
