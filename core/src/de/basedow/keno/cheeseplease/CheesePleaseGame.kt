package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Game
import de.basedow.keno.cheeseplease.utils.logger

class CheesePleaseGame : Game() {

    companion object {
        @JvmStatic
        private val log = logger<CheesePleaseGame>()
    }

    override fun create() {
        setScreen(CheeseMenu(this))
    }

}
