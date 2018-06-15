package de.basedow.keno.cheeseplease

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array

class CheeseLevel(game: Game) : BaseScreen(game) {

    val mapWidth = 800f
    val mapHeight = 800f

    private val mousey =  AnimatedActor()
    private val cheese = BaseActor()
    private val floor = BaseActor()
    private val winText = BaseActor()

    private var elapsedTime = 0f
    private val timeLabel: Label

    private var win = false

    init {
        floor.texture = Texture("tiles-800-800.jpg")
        floor.setPosition(0f, 0f)
        mainStage.addActor(floor)

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

        mousey.animation = anim
        mousey.setOrigin(Align.center)
        mousey.setPosition(20f, 20f)
        mainStage.addActor(mousey)

        winText.texture = Texture("you-win.png")
        winText.setPosition(170f, 60f)
        winText.isVisible = false
        uiStage.addActor(winText)

        timeLabel = Label("Time: 0", Label.LabelStyle(BitmapFont(), Color.NAVY))
        timeLabel.setFontScale(2f)
        timeLabel.setPosition(500f, 440f)
        uiStage.addActor(timeLabel)
    }

    override fun update(delta: Float) {

        mousey.velocityX = 0f
        mousey.velocityY = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) mousey.velocityX -= 100
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) mousey.velocityX += 100
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) mousey.velocityY += 100
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) mousey.velocityY -= 100

        mousey.x = MathUtils.clamp(mousey.x, 0f, mapWidth - mousey.width)
        mousey.y = MathUtils.clamp(mousey.y, (mousey.width - mousey.height) / 2f,
                mapHeight - mousey.height - (mousey.width - mousey.height) / 2f)

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
                    Actions.alpha(0f),
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

        if (!win) {
            elapsedTime += delta
            timeLabel.setText("Time: ${elapsedTime.toInt()}")
        }

        val cam = mainStage.camera
        cam.position.x = mousey.x + mousey.originX
        cam.position.y = mousey.y + mousey.originY
        cam.position.x = MathUtils.clamp(cam.position.x, viewWidth / 2f, mapWidth - viewWidth / 2f)
        cam.position.y = MathUtils.clamp(cam.position.y, viewHeight / 2f, mapHeight - viewHeight / 2f)
        cam.update()
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.M -> game.screen = CheeseMenu(game)
            Input.Keys.P -> togglePaused()
        }
        return false
    }
}
