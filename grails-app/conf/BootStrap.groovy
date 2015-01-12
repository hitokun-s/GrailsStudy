import grailsstudy.Book
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def init = { servletContext ->
        def book = new Book([
                author:"test_author",
                title:"test_title",
                price:1500,
                publishDate:new Date()
        ])
        assert book.save()
        println "book id : ${book.id}"

        def ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
        def dataSource = ctx.getBean("dataSource")

        def sql = Sql.newInstance(dataSource)

        sql.execute("drop table Book")
    }
    def destroy = {
    }
}
