package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Game
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class CheeseMenu(game: Game) : BaseScreen(game) {

    init {
        val background = BaseActor()
        background.texture = Texture("tiles-menu.jpg")
        uiStage.addActor(background)

        val titleText = BaseActor()
        titleText.texture = Texture("cheese-please.png")
        titleText.setPosition(20f, 100f)
        uiStage.addActor(titleText)

        val instructions = Label(" Press S to start, M for main menu ", Label.LabelStyle(BitmapFont(), Color.YELLOW))
        instructions.setFontScale(2f)
        instructions.setPosition(100f, 50f)
        instructions.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(Color(1f, 1f, 0f, 1f), 0.5f),
                                Actions.delay(0.5f),
                                Actions.color(Color(0.5f, 0.5f, 0f, 1f), 0.5f)
                        )
                )
        )
        uiStage.addActor(instructions)
    }

    override fun update(delta: Float) {}

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.S)
            game.screen = CheeseLevel(game)
        return false
    }
}