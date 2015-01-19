import grailsstudy.Book
import grailsstudy.Country
import grailsstudy.Ebook
import grailsstudy.State
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def init = { servletContext ->

        def ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
        def dataSource = ctx.getBean("dataSource")

//        def sql = Sql.newInstance(dataSource)

//        sql.execute("drop table EBook") // for db error test

        def states1 = new State(name:"states1").save()
        def states2 = new State(name:"states2").save()
        def country = new Country(
                name:"Japan",
                states:[states1, states2]
        )
        country.save()
    }
    def destroy = {
    }
}
