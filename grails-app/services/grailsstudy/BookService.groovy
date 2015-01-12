package grailsstudy

import grails.transaction.Transactional

@Transactional
class BookService {

    def save(Book book, Ebook ebook) {
        if(ebook.validate() && book.validate()){ // validation is not related with database situation
            ebook.save()
            book.save() // this must fail , because table was dropped in Bootstrap
        }
    }
}
