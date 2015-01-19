package grailsstudy

class State {

    String name

    // Cascade参照アクションを可能にする
    static belongsTo = Country

    static constraints = {
    }

    static mapping = {
        cache : true
    }
}
