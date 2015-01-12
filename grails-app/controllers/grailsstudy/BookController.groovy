package grailsstudy

import grails.converters.JSON

/**
 * Today's Lesson theme :
 * " How you can keep database consistency, when you save multiple domain classes in one action ? "
 */
class BookController {

    // OK, let's begin experiments...

    // **************************
    //           exp #1
    // **************************

    def riskySave1(){
        def book = new Book()
        bindData(book, request.JSON)
        def ebook = new Ebook()
        bindData(ebook, request.JSON)
        ebook.save() // ebook is saved in database
        book.save() // validation error occurs in validate() inside save() if price is under 1000 or price not provided
        def res  = [book, ebook]
        render res as JSON
        // only ebook was saved !
    }

    // OK, we should comlete all validate() before all save() '

    // **************************
    //           exp #2
    // **************************

    //both of ebook and book will be saved, or, both will not be saved
    def riskySave2(){
        def book = new Book()
        bindData(book, request.JSON)
        def ebook = new Ebook()
        bindData(ebook, request.JSON)
        if(ebook.validate() && book.validate()){
            ebook.save()
            book.save() // this must fail because Book table was dropped in Bootstrap
        }else{
            def errors = [book.errors,ebook.errors]
            render errors as JSON
        }
        def res  = [book, ebook]
        render res as JSON
        // only ebook was saved !
    }

    // **************************
    //           exp #3
    // **************************

    // use service class with @Transactional
    def safeSave(){
        def book = new Book()
        bindData(book, request.JSON)
        def ebook = new Ebook()
        bindData(ebook, request.JSON)
        bookService.save(book, ebook) // If saving book failed, everything would be rollbacked
        render true
    }

    def bookService

    // Lesson ends !

    // actions for live data checking

    def getBook(){
        render Book.get(params.id) as JSON
    }

    def getEbook(){
        render Ebook.get(params.id) as JSON
    }

}
