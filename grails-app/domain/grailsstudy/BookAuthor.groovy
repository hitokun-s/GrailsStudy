package grailsstudy

class BookAuthor {

    Integer id
    Book book
    Author author

    // In this project, using  "cascade: all-delete-orphan" may be better than using "belongsTO"
//    static belongsTo = Book

    static constraints = {
//        author nullable : true
    }

    static mapping = {
        author lazy:false
    }
}
