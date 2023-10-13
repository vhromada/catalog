package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.common.RestResponse
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

/**
 * An interface represents connector for pictures.
 *
 * @author Vladimir Hromada
 */
interface PictureConnector {

    /**
     * Returns list of pictures.
     *
     * @return list of pictures
     */
    fun getAll(): List<String>

    /**
     * Returns list of pictures for filter.
     *
     * @param filter filter
     * @return list of pictures for filter
     */
    fun search(filter: PagingFilter): Page<String>

    /**
     * Returns picture.
     *
     * @param uuid UUID
     * @return picture
     */
    fun get(uuid: String): RestResponse<Resource>

    /**
     * Adds picture.
     *
     * @param picture file with picture
     * @return created picture
     */
    fun add(picture: MultipartFile)

    /**
     * Removes picture.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

}
