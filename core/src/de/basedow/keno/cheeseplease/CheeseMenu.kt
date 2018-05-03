package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class CheeseMenu(var game: Game) : Screen {

    private val uiStage = Stage()

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

    override fun render(delta: Float) {

        if (Gdx.input.isKeyPressed(Input.Keys.S))
            game.screen = CheeseLevel(game)

        uiStage.act(delta)

        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        uiStage.draw()
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }
}