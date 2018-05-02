package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import de.basedow.keno.cheeseplease.utils.logger

class CheesePleaseGame : ApplicationAdapter() {

    companion object {
        @JvmStatic
        private val log = logger<CheesePleaseGame>()
    }

    lateinit var mainStage: Stage
    private lateinit var mousey: AnimatedActor
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
        cheese.setOrigin(Align.center)
        cheese.setPosition(400f, 300f)
        mainStage.addActor(cheese)

        val frames = Array<TextureRegion>(4)
        for (i in 0 until 4) {
            val tex = Texture("mouse$i.png")
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            frames.add(TextureRegion(tex))
        }
        val anim = Animation(0.1f, frames, Animation.PlayMode.LOOP_PINGPONG)

        mousey = AnimatedActor()
        mousey.animation = anim
        mousey.setOrigin(Align.center)
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

        if (!win && cheese.boundingRectangle.contains(mousey.boundingRectangle)) {
            win = true

            val spinShrinkFadeOut = Actions.parallel(
                    Actions.alpha(1f),
                    Actions.rotateBy(360f, 1f),
                    Actions.scaleTo(0f, 0f, 1f),
                    Actions.fadeOut(1f)
            )
            cheese.addAction(spinShrinkFadeOut)

            val fadeInColorCycleForever = Actions.parallel(
                    Actions.alpha(1f),
                    Actions.show(),
                    Actions.fadeIn(2f),
                    Actions.forever(
                            Actions.sequence(
                                    Actions.color(Color(1f, 0f, 0f, 1f), 1f),
                                    Actions.color(Color(0f, 0f, 1f, 1f), 1f)
                            )
                    )
            )
            winText.addAction(fadeInColorCycleForever)
        }

        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
    }

    override fun dispose() {
        mainStage.dispose()
    }
}
