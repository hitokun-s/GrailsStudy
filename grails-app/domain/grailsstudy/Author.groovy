package grailsstudy

class Author {

    Integer id
    String name

    // null値をdefault値で上書きしてしまわないか、の実験用プロパティ
    Integer testId = 0 // デフォルト値

    static constraints = {
        testId nullable:true
        name nullable:true
    }
}
