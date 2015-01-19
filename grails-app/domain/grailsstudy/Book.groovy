package grailsstudy

import org.grails.databinding.BindingFormat

class Book {

    String title = ""
    Integer price = 0

    @BindingFormat('MM/dd/yyyy\'T\'HH:mm:ss.SSS\'Z\'') //UTC String
    Date publishDate = new Date()

    static hasMany = [authors: BookAuthor]

    static constraints = {
        price min: 1000
    }

    static mapping = {
        // As for "1 : n" relation, "lazy : false" is recommended.
        // With this option, grails execute 2 query as below:
        // [sql1] fetching target Book instance from BOOK table
        // [sql2] fetching related BookAuthor instances from BOOK_AUTHOR table
        authors lazy:false
    }

}
