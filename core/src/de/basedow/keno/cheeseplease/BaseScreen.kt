package de.basedow.keno.cheeseplease

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport

abstract class BaseScreen(protected val game: Game) : Screen, InputProcessor {

    val viewWidth = 640f
    val viewHeight = 480f

    protected val mainStage = Stage(FitViewport(viewWidth, viewHeight))
    protected val uiStage = Stage(FitViewport(viewWidth, viewHeight))

    var isPaused = false

    fun togglePaused() {
        isPaused = !isPaused
    }

    init {
        Gdx.input.inputProcessor = InputMultiplexer(this, uiStage, mainStage)
    }

    abstract fun update(delta: Float)

    // Screen interface

    override fun render(delta: Float) {
        uiStage.act(delta)

        if (!isPaused) {
            mainStage.act(delta)
            update(delta)
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
        uiStage.draw()
    }

    override fun resize(width: Int, height: Int) {
        mainStage.viewport.update(width, height, true)
        uiStage.viewport.update(width, height, true)
    }

    override fun dispose() {
        mainStage.dispose()
        uiStage.dispose()
    }

    override fun hide() {}
    override fun show() {}
    override fun pause() {}
    override fun resume() {}

    // InputProcessor interface

    override fun keyDown(keycode: Int) = false
    override fun keyUp(keycode: Int) = false
    override fun keyTyped(character: Char) = false
    override fun mouseMoved(screenX: Int, screenY: Int) = false
    override fun scrolled(amount: Int) = false
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) = false
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int) = false
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false
}