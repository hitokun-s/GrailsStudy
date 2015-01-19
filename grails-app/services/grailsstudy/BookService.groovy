package grailsstudy

import grails.transaction.Transactional

@Transactional
class BookService {

    // 複数ドメインの保存中にdbエラーが起きた場合のロールバックテスト用
    def saveBoth(Book book, Ebook ebook) {
        if(ebook.validate() && book.validate()){ // validation is not related with database situation
            ebook.save()
            book.save() // this must fail , because table was dropped in Bootstrap
        }
    }

    // hasmany のリレーション（BookとBookAuthorとAuuthor）のsaveの挙動チェック用
    def save(Book book){
        // experiment for hasmany relation
        def author1 = new Author(name:"test_author1").save()
        def author2 = new Author(name:"test_author2").save()
        // authorをsave()しておかないと、BookAuthorのauthor_idがnullになってエラーになる

        book.addToAuthors(new BookAuthor(author:author1))
        book.addToAuthors(new BookAuthor(author:author2))
        book.save() // これでBookAuthorも自動的にsave()されて、BOOK_AUTHORテーブルに保存される

        // このアクションによって、AUTHOR, BOOK_AUTHOR, BOOK に対して、
        // ３つのINSERT文が発行される
    }

}
