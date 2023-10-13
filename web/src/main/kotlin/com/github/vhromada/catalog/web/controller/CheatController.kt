package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.CheatConnector
import com.github.vhromada.catalog.web.connector.GameConnector
import com.github.vhromada.catalog.web.fo.CheatDataFO
import com.github.vhromada.catalog.web.fo.CheatFO
import com.github.vhromada.catalog.web.mapper.CheatMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * A class represents controller for cheats.
 *
 * @author Vladimir Hromada
 */
@Controller("cheatController")
@RequestMapping("/games/{gameUuid}/cheats")
class CheatController(
    /**
     * Connector for cheats
     */
    private val cheatConnector: CheatConnector,
    /**
     * Connector for games
     */
    private val gameConnector: GameConnector,
    /**
     * Mapper for cheats
     */
    private val mapper: CheatMapper
) {

    /**
     * Shows page with cheat.
     *
     * @param model    model
     * @param gameUuid game UUID
     * @return view for page with cheat
     */
    @GetMapping
    fun showList(model: Model, @PathVariable("gameUuid") gameUuid: String): String {
        val cheat = cheatConnector.find(game = gameUuid)
            .throwExceptionIfAny()
            .get()

        model.addAttribute("cheat", cheat)
        model.addAttribute("game", gameUuid)
        model.addAttribute("title", "Cheats")

        return "cheat/index"
    }

    /**
     * Shows page for adding cheat.
     *
     * @param model    model
     * @param gameUuid game UUID
     * @return view for page for adding cheat
     */
    @GetMapping("/add")
    fun showAdd(model: Model, @PathVariable("gameUuid") gameUuid: String): String {
        gameConnector.get(uuid = gameUuid)

        val cheat = CheatFO(
            uuid = null,
            gameSetting = null,
            cheatSetting = null,
            data = null
        )
        return createAddFormView(model = model, cheat = cheat, gameUuid = gameUuid)
    }

    /**
     * Process adding cheat.
     *
     * @param model    model
     * @param gameUuid game UUID
     * @param cheat    FO for cheat
     * @param errors   errors
     * @param request  HTTP request
     * @return view for redirect to page with cheats (no errors) or view for page for adding cheat (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping("/add")
    fun processAdd(model: Model, @PathVariable("gameUuid") gameUuid: String, @ModelAttribute("cheat") @Valid cheat: CheatFO, errors: Errors, request: HttpServletRequest): String {
        require(cheat.uuid == null) { "UUID must be null." }

        if (request.getParameter("create") != null) {
            if (errors.hasErrors()) {
                return createAddFormView(model = model, cheat = cheat, gameUuid = gameUuid)
            }

            cheatConnector.add(game = gameUuid, request = mapper.mapRequest(source = cheat))

            return getCheatsRedirectUrl(gameUuid = gameUuid)
        }

        if (request.getParameter("addCheat") != null) {
            val cheatData = if (cheat.data == null) mutableListOf() else cheat.data!!.toMutableList()
            cheatData.add(CheatDataFO())

            return createAddFormView(model = model, cheat = cheat.copy(data = cheatData), gameUuid = gameUuid)
        }

        val index = getRemoveIndex(request = request)
        if (index != null) {
            val cheatData = cheat.data!!.toMutableList()
            cheatData.removeAt(index)

            return createAddFormView(model = model, cheat = cheat.copy(data = cheatData), gameUuid = gameUuid)
        }

        return getCheatsRedirectUrl(gameUuid = gameUuid)
    }

    /**
     * Shows page for editing cheat.
     *
     * @param model    model
     * @param gameUuid game UUID
     * @return view for page for editing cheat
     */
    @GetMapping("/edit")
    fun showEdit(model: Model, @PathVariable("gameUuid") gameUuid: String): String {
        val cheat = cheatConnector.find(game = gameUuid)
            .throwExceptionIfAny()
            .get()

        return createEditFormView(model = model, cheat = mapper.map(source = cheat), gameUuid = gameUuid)
    }

    /**
     * Process editing cheat.
     *
     * @param model    model
     * @param gameUuid game UUID
     * @param cheat    FO for cheat
     * @param errors   errors
     * @param request  HTTP request
     * @return view for redirect to page with cheats (no errors) or view for page for editing cheat (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping("/edit")
    fun processEdit(model: Model, @PathVariable("gameUuid") gameUuid: String, @ModelAttribute("cheat") @Valid cheat: CheatFO, errors: Errors, request: HttpServletRequest): String {
        require(cheat.uuid != null) { "UUID mustn't be null." }

        if (request.getParameter("update") != null) {
            if (errors.hasErrors()) {
                return createEditFormView(model = model, cheat = cheat, gameUuid = gameUuid)
            }

            cheatConnector.update(game = gameUuid, uuid = cheat.uuid, request = mapper.mapRequest(source = cheat))
            return getCheatsRedirectUrl(gameUuid = gameUuid)
        }

        if (request.getParameter("addCheat") != null) {
            val cheatData = cheat.data!!.toMutableList()
            cheatData.add(CheatDataFO())

            return createEditFormView(model = model, cheat = cheat.copy(data = cheatData), gameUuid = gameUuid)
        }

        val index = getRemoveIndex(request)
        if (index != null) {
            val cheatData = cheat.data!!.toMutableList()
            cheatData.removeAt(index)

            return createEditFormView(model = model, cheat = cheat.copy(data = cheatData), gameUuid = gameUuid)
        }

        return getCheatsRedirectUrl(gameUuid = gameUuid)
    }

    /**
     * Process removing cheat.
     *
     * @param gameUuid game UUID
     * @return view for redirect to page with cheats
     */
    @GetMapping("/remove")
    fun processRemove(@PathVariable("gameUuid") gameUuid: String): String {
        val cheat = cheatConnector.find(game = gameUuid)
            .throwExceptionIfAny()
            .get()
        cheatConnector.remove(game = gameUuid, uuid = cheat.uuid)

        return "redirect:/games"
    }

    /**
     * Returns page's view with form for adding cheat.
     *
     * @param model  model
     * @param cheat  FO for cheat
     * @param gameUuid game UUID
     * @return page's view with form for adding cheat
     */
    private fun createAddFormView(model: Model, cheat: CheatFO, gameUuid: String): String {
        return createFormView(model = model, cheat = cheat, gameUuid = gameUuid, title = "Add cheat", action = "add")
    }

    /**
     * Returns page's view with form for editing cheat.
     *
     * @param model    model
     * @param cheat    FO for cheat
     * @param gameUuid game UUID
     * @return page's view with form for editing cheat
     */
    private fun createEditFormView(model: Model, cheat: CheatFO, gameUuid: String): String {
        return createFormView(model = model, cheat = cheat, gameUuid = gameUuid, title = "Edit cheat", action = "edit")
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param cheat    FO for cheat
     * @param gameUuid game UUID
     * @param title    page's title
     * @param action   action
     * @return page's view with form
     */
    private fun createFormView(model: Model, cheat: CheatFO, gameUuid: String, title: String, action: String): String {
        model.addAttribute("cheat", cheat)
        model.addAttribute("game", gameUuid)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "cheat/form"
    }

    /**
     * Returns redirect URL to cheats.
     *
     * @param gameUuid game UUID
     * @return redirect URL to cheats
     */
    private fun getCheatsRedirectUrl(gameUuid: String): String {
        return "redirect:/games/$gameUuid/cheats"
    }

    /**
     * Returns index of removing cheat.
     *
     * @param request HTTP request
     * @return index of removing cheat
     */
    private fun getRemoveIndex(request: HttpServletRequest): Int? {
        var index: Int? = null
        val names = request.parameterNames
        while (names.hasMoreElements() && index == null) {
            val name = names.nextElement()
            if (name.startsWith("removeCheat")) {
                index = (name.substring(11).toInt())
            }
        }

        return index
    }

}
