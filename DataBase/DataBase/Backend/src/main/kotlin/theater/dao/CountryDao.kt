package theater.dao

import theater.model.Country
import java.sql.Statement
import javax.sql.DataSource

class CountryDao(private val dataSource: DataSource) {

    fun createCountry(country: Country): Int {
        val stmt = dataSource.connection.prepareStatement(
                "INSERT INTO countries (name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
        )
        stmt.setString(1, country.name)
        stmt.executeUpdate()

        val generatedKeys = stmt.generatedKeys
        generatedKeys.next()

        return generatedKeys.getInt(1)
    }

    fun getCountry(id: Int): Country? {
        val stmt = dataSource.connection.prepareStatement(
                "SELECT id, name FROM countries WHERE id = ?"
        )
        stmt.setInt(1, id)
        val queryResult = stmt.executeQuery()

        return if (queryResult.next()) {
            Country(queryResult.getInt("id"), queryResult.getString("name"))
        } else {
            null
        }
    }

    fun getCountries(): ArrayList<Country> {
        val stmt = dataSource.connection.prepareStatement(
                "SELECT id, name FROM countries"
        )
        val queryResult = stmt.executeQuery()

        val res = ArrayList<Country>()
        while (queryResult.next()) {
            res.add(Country(queryResult.getInt("id"), queryResult.getString("name")))
        }
        return res
    }

    fun getCountryByName(name: String): Country? {
        val stmt = dataSource.connection.prepareStatement(
                "SELECT id, name FROM countries WHERE name = ?"
        )
        stmt.setString(1, name)

        val queryResult = stmt.executeQuery()

        return if (queryResult.next()) {
            Country(queryResult.getInt("id"), queryResult.getString("name"))
        } else {
            null
        }
    }

    fun updateCountry(country: Country) {
        val stmt = dataSource.connection.prepareStatement(
                "UPDATE countries SET name=? WHERE id = ? "
        )
        stmt.setString(1, country.name)
        stmt.setInt(2, country.id)
        stmt.executeUpdate()
    }

    fun deleteCountry(id: Int) {
        val stmt = dataSource.connection.prepareStatement(
                "DELETE FROM countries WHERE id = ?"
        )
        stmt.setInt(1, id)
        stmt.executeUpdate()
    }

}