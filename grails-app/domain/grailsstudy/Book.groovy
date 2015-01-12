package grailsstudy

import org.grails.databinding.BindingFormat

class Book {

    String author = ""
    String title = ""
    Integer price = 0

    @BindingFormat('MM/dd/yyyy\'T\'HH:mm:ss.SSS\'Z\'') //UTC String
    Date publishDate = new Date()

    static constraints = {
        price min: 1000
    }

    static mapping = {
        table : "Book"
    }
}
