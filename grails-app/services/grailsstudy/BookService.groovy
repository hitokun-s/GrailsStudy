package grailsstudy

import grails.transaction.Transactional

//@Transactional
class BookService {

    // 複数ドメインの保存中にdbエラーが起きた場合のロールバックテスト用
    def saveBoth(Book book, Ebook ebook) {
        if(ebook.validate() && book.validate()){ // validation is not related with database situation
            println "validation passed!"
            book.save()
            ebook.save() // this must fail , because EBOOK table was dropped in Bootstrap.groovy
        }
    }

    // hasmany のリレーション（BookとBookAuthorとAuuthor）のsaveの挙動チェック用
    def save(Book book){

        book.save() // これでBookAuthorも自動的にsave()されて、BOOK_AUTHORテーブルに保存される

        // このアクションによって、AUTHOR, BOOK_AUTHOR, BOOK に対して、
        // ３つのINSERT文が発行される
    }

}
