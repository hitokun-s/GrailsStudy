package grailsstudy

class BookAuthor {

    Book book
    Author author

//    static belongsTo = Book

    static constraints = {
//        author nullable : true
    }

    static mapping = {
        author lazy:false
    }
}
