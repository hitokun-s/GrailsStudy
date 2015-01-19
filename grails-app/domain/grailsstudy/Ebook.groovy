package grailsstudy

import org.grails.databinding.BindingFormat

/**
 * 本プロジェクトにおいては、エラーテストのためにBootStrapでEbookテーブルを削除している
 * よって、他の目的で、Ebookクラスを正常に使うことはできない
 */
class Ebook {
    String title = ""
    Integer price = 0

    @BindingFormat('MM/dd/yyyy\'T\'HH:mm:ss.SSS\'Z\'') //UTC String
    Date publishDate = new Date()

    static constraints = {
        price min: 1000
    }
}
