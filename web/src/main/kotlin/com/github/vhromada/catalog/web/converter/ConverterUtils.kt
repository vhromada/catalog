package com.github.vhromada.catalog.web.converter

import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.connector.entity.Genre
import com.github.vhromada.catalog.web.connector.entity.Medium
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.connector.entity.Season

/**
 * A class represents utility class for converters.
 *
 * @author Vladimir Hromada
 */
object ConverterUtils {

    /**
     * Converts strings.
     *
     * @param strings strings
     * @return converted strings
     */
    @JvmStatic
    fun convertStrings(strings: List<String>): String {
        if (strings.isEmpty()) {
            return ""
        }

        val result = StringBuilder()
        for (string in strings) {
            result.append(string)
            result.append(", ")
        }

        return result.substring(0, result.length - 2)
    }

    /**
     * Converts movie's media.
     *
     * @param media media
     * @return converted movie's media
     */
    @JvmStatic
    fun convertMedia(media: List<Medium>): String {
        return convertStrings(strings = media.map { it.formattedLength })
    }

    /**
     * Converts game's additional data.
     *
     * @param game game
     * @return converted game's additional data
     */
    @JvmStatic
    fun convertGameAdditionalData(game: Game): String {
        val result = StringBuilder()
        if (game.crack) {
            result.append("Crack")
        }
        addToResult(result = result, value = game.serialKey, data = "serial key")
        addToResult(result = result, value = game.patch, data = "patch")
        addToResult(result = result, value = game.trainer, data = "trainer")
        addToResult(result = result, value = game.trainerData, data = "data for trainer")
        addToResult(result = result, value = game.editor, data = "editor")
        addToResult(result = result, value = game.saves, data = "saves")
        if (!game.otherData.isNullOrBlank()) {
            if (result.isNotEmpty()) {
                result.append(", ")
            }
            result.append(game.otherData)
        }

        return result.toString()
    }

    /**
     * Converts content of game's additional data.
     *
     * @param game game
     * @return converted content of game's additional data
     */
    @JvmStatic
    fun convertGameAdditionalDataContent(game: Game): Boolean {
        return convertGameAdditionalData(game = game).isNotEmpty()
    }

    /**
     * Converts season's years.
     *
     * @param season season
     * @return converted season's years
     */
    @JvmStatic
    fun convertSeasonYears(season: Season): String {
        return if (season.startYear == season.endYear) season.startYear.toString() else "${season.startYear} - ${season.endYear}"
    }

    /**
     * Converts program's additional data.
     *
     * @param program program
     * @return converted program's additional data
     */
    @JvmStatic
    fun convertProgramAdditionalData(program: Program): String {
        val result = StringBuilder()
        if (program.crack) {
            result.append("Crack")
        }
        addToResult(result = result, value = program.serialKey, "serial key")
        if (!program.otherData.isNullOrBlank()) {
            if (result.isNotEmpty()) {
                result.append(", ")
            }
            result.append(program.otherData)
        }

        return result.toString()
    }

    /**
     * Converts content of program's additional data.
     *
     * @param program program
     * @return converted content of program's additional data
     */
    @JvmStatic
    fun convertProgramAdditionalDataContent(program: Program): Boolean {
        return convertProgramAdditionalData(program = program).isNotEmpty()
    }

    /**
     * Converts genres.
     *
     * @param genres genres
     * @return converted genres
     */
    @JvmStatic
    fun convertGenres(genres: List<Genre>): String {
        return convertStrings(strings = genres.map { it.name })
    }

    /**
     * Converts IMDB code.
     *
     * @param imdbCode imdb code
     * @return converted IMDB code
     */
    @JvmStatic
    fun convertImdbCode(imdbCode: Int): String {
        return imdbCode.toString().padStart(7, '0')
    }

    /**
     * Converts page.
     *
     * @param currentPage current page
     * @param totalPages count of pages
     * @return converted page
     */
    @JvmStatic
    fun convertPage(currentPage: Int, totalPages: Int): String {
        return "$currentPage / $totalPages"
    }

    /**
     * Converts authors.
     *
     * @param authors authors
     * @return converted authors
     */
    @JvmStatic
    fun convertAuthors(authors: List<Author>): String {
        return convertStrings(strings = authors.map { convertAuthorName(author = it) })
    }

    /**
     * Converts author's name.
     *
     * @param author author
     * @return converted author's name
     */
    @JvmStatic
    fun convertAuthorName(author: Author): String {
        val result = StringBuilder()
            .append(author.firstName)
        if (!author.middleName.isNullOrBlank()) {
            result.append(" ")
                .append(author.middleName)
        }
        result.append(" ")
            .append(author.lastName)
        return result.toString()
    }

    /**
     * Adds data to result.
     *
     * @param result result
     * @param value  value
     * @param data   data
     */
    private fun addToResult(result: StringBuilder, value: Boolean, data: String) {
        if (value) {
            if (result.isEmpty()) {
                result.append(data.substring(0, 1).uppercase())
                result.append(data.substring(1))
            } else {
                result.append(", ")
                result.append(data)
            }
        }
    }

}
