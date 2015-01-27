import grailsstudy.Author
import grailsstudy.Book
import grailsstudy.Country
import grailsstudy.Category
import grailsstudy.Ebook
import grailsstudy.State
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def init = { servletContext ->

        def ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
        def dataSource = ctx.getBean("dataSource")

        // drop EBOOK table for rollback test in transaction error
        def sql = Sql.newInstance(dataSource)
        sql.execute("drop table EBook")

        def states1 = new State(name:"states1").save()
        def states2 = new State(name:"states2").save()
        def country = new Country(
                name:"Japan",
                states:[states1, states2]
        )
        country.save()
        println country

        def category = new Category(name:"test category 1")
        category.save()
        println category

        def bookWithoutAuthor = new Book(title:"Book without author", price:3000, categoryId:category.id)
        bookWithoutAuthor.save()
        println bookWithoutAuthor

        def author1 = new Author(name:"author1", testId : null)
        author1.save()
        def author2 = new Author(name:"author2", testId : 2)
        author2.save()
        println author1
        println author2
    }
    def destroy = {
    }
}
