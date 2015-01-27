package grailsstudy

import org.grails.databinding.BindingFormat

class Book {

    String title = ""
    Integer price = 0
    Integer categoryId

    @BindingFormat('MM/dd/yyyy\'T\'HH:mm:ss.SSS\'Z\'') //UTC String
    Date publishDate = new Date()

    static hasMany = [authors: BookAuthor]

    static constraints = {
        price min: 1000
        categoryId validator: { val, obj ->
            // check existence
            !val || Category.findByIdAndActive(val, true)
        }
    }

    static mapping = {
        // As for "1 : n" relation, "lazy : false" is recommended.
        // With this option, grails execute 2 query as below:
        // [sql1] fetching target Book instance from BOOK table
        // [sql2] fetching related BookAuthor instances from BOOK_AUTHOR table
        authors lazy:false, cascade: "all-delete-orphan"
        // It seems that "cascade: all-delete-orphan" is necessary to delete BookAuthor automatically when update Book.authors
        // see also:
        // http://grails.1312388.n4.nabble.com/Difference-between-belongsTo-and-cascade-quot-all-delete-orphan-quot-td4164742.html
    }

}
