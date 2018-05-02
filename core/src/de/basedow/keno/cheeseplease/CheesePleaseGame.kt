package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import de.basedow.keno.cheeseplease.utils.logger

class CheesePleaseGame : ApplicationAdapter() {

    companion object {
        @JvmStatic
        private val log = logger<CheesePleaseGame>()
    }

    lateinit var mainStage: Stage
    private lateinit var mousey: BaseActor
    private lateinit var cheese: BaseActor
    private lateinit var floor: BaseActor
    private lateinit var winText: BaseActor

    private var win = false

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        mainStage = Stage()

        floor = BaseActor()
        floor.texture = Texture("tiles.jpg")
        floor.setPosition(0f, 0f)
        mainStage.addActor(floor)

        cheese = BaseActor()
        cheese.texture = Texture("cheese.png")
        cheese.setPosition(400f, 300f)
        mainStage.addActor(cheese)

        mousey = BaseActor()
        mousey.texture = Texture("mouse.png")
        mousey.setPosition(20f, 20f)
        mainStage.addActor(mousey)

        winText = BaseActor()
        winText.texture = Texture("you-win.png")
        winText.setPosition(170f, 60f)
        winText.isVisible = false
        mainStage.addActor(winText)
    }

    override fun render() {

        mousey.velocityX = 0f
        mousey.velocityY = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) mousey.velocityX -= 100
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) mousey.velocityX += 100
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) mousey.velocityY += 100
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) mousey.velocityY -= 100

        mainStage.act(Gdx.graphics.deltaTime)

        if (cheese.boundingRectangle.contains(mousey.boundingRectangle))
            winText.isVisible = true

        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
    }

    override fun dispose() {
        mainStage.dispose()
    }
}
