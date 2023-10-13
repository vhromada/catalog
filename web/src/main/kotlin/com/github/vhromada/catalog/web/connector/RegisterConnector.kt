package com.github.vhromada.catalog.web.connector

/**
 * An interface represents connector for registers.
 *
 * @author Vladimir Hromada
 */
interface RegisterConnector {

    /**
     * Returns list of formats for program.
     *
     * @return list of formats for program
     */
    fun getProgramFormats(): List<String>

    /**
     * Returns list of formats for book item.
     *
     * @return list of formats for book item
     */
    fun getBookItemFormats(): List<String>

    /**
     * Returns list of languages.
     *
     * @return list of languages
     */
    fun getLanguages(): List<String>

    /**
     * Returns list of subtitles.
     *
     * @return list of subtitles
     */
    fun getSubtitles(): List<String>

}
