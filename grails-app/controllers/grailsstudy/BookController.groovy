package grailsstudy

import grails.converters.JSON
import grails.transaction.Transactional

/**
 * Today's Lesson theme :
 * " How you can keep database consistency, when you saveBoth multiple domain classes in one action ? "
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
        book.save() // book is saved in database
        ebook.save() // validation error occurs in validate() inside saveBoth() if price is under 1000 or price not provided
        def res  = [book, ebook]
        render res as JSON
        // only book was saved !
    }

    // OK, we should comlete all validate() before all saveBoth() '

    /**
     * exp #2
     */
    def riskySave2(){
        def book = new Book()
        bindData(book, request.JSON)
        def ebook = new Ebook()
        bindData(ebook, request.JSON)

        if(ebook.validate() && book.validate()){
            try{
                book.save()
                ebook.save() // this must fail because Ebook table was dropped in Bootstrap
            }catch(Exception e){
                println e
            }
        }else{
            def errors = [book.errors,ebook.errors]
            render errors as JSON
        }
        def res  = [book, ebook]
        render res as JSON
        // only book was saved !
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
        try{
            bookService.saveBoth(book, ebook) // If saving ebook failed, everything would be rollbacked
        }catch(Exception e ){
            response.status = 500
        }
        render true
        // in this project, nothing is saved, because ebook table does not exist
    }

    def bookService

    // Lesson ends !

    // actions for live data checking

    def get(){
        def result = [ data : Book.get(params.id)]
        render result as JSON

        // 下の、getBookAuthors()参照。
        // Book -> bookAuthors lazy:false
        // BookAuthor -> author lazy:false
        // にしていると、最下層のAuthorまですべてのデータが取得される

        // ただしその場合でも、戻り値のJSONには、Authorインスタンスは含まれない。
        // つまり、Authorの詳細データは無題に取得しているともいえる・・・？
        // JSON
//        {
//            "data": {
//            "class": "grailsstudy.Book",
//            "id": 1,
//            "authors": [
//                    {
//                        "class": "grailsstudy.BookAuthor",
//                        "id": 1
//                    },
//                    {
//                        "class": "grailsstudy.BookAuthor",
//                        "id": 2
//                    }
//            ],
//            "price": 2000,
//            "publishDate": "2015-01-19T10:20:25Z",
//            "title": "testtitle2"
//        }
//        }


    }

    def getBookAuthors(){

        def res = []
        def book = Book.get(params.id as Integer)
        // Book -> bookAuthors lazy:trueだと、この時点ではBOOKのSELECT１回だけ
        // Book -> bookAuthors lazy:falseだと、この時点で、BookAuthorも取得してしまう
        // さらに、
        // BookAuthor -> author lazy:falseだと、この時点で、Authorも取得してしまう

        // 要するに、hasManyとかは関係なく、
        // - lazy:falseであれば、参照されるかどうかに関わらず関連インスタンスも取得してしまう
        // - lazy:trueであれば、関連インスタンスが参照されてはじめて、そのデータを取得する

        println "I got only Book instance here!"

        def authors = book.authors

        println "I got Book.authors here!"

        for(def author in authors){
            println author.author
        }
        // lazy:false だとここではじめてAuthorを取得する

        println "I got author of Book.author here!"

        for(def author in authors){
            println author.author.name
        }

        println "I got author.name of Book.author here!"

        render authors as JSON

        // see also:
        // http://grails.org/doc/latest/guide/GORM.html#fetching
        // http://grails.jp/doc/2.3.x/guide/GORM.html#fetching
    }


    def save(){
        def book
        // authorIds must be like [[id:1], [id:2], ...]
        def authors = Author.getAll(request.JSON.authorIds)

        // Attention!!!
        // if getAll() cannot find all Authors correspinding authorIds,
        // no exception will be thrown here.

        if(params.id){
            book = Book.get(params.id)
            book.authors.clear()
            authors.each{
                book.addToAuthors(BookAuthor.findOrCreateWhere(author:it,book:book))
                // Note:
                // Let's say, Book 1 has Author 1 via BookAuthor 1.
                // if you just want to replace Author 1 with Author 2,
                // a new BookAuthor 2 will be created, and BookAuthor 1 will be deleted.
                // Don't expect that BookAuthor 1 will be updated.
            }
        }else{
            book = new Book()
            authors.each{
//                book.addToAuthors(new BookAuthor(author:it,book:book)) // don't have to add [book:book]
                book.addToAuthors(new BookAuthor(author:it))
            }
        }
        bindData(book, request.JSON)
        bookService.save(book)
        if(book.hasErrors()){
            render book.errors as JSON
        }else{
            render true
        }

    }

    /**
     * bindDataは、saveもupdateも面倒みてくれるということを実験
     */
    def saveOrUpdate(Book book){
//        def book = bindData(new Book(), request.JSON)
        if(book){
            render book as JSON
        }else{
            render false
        }


    }

    def saveAuthor(){
        def author = new Author()
        bindData(author, request.JSON)
        author.save()
        def res = [result:true, data:author]
        render res as JSON
    }

    def findAll(){
        render Book.findAll() as JSON
    }

}
