package de.basedow.keno.cheeseplease

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor

open class BaseActor : Actor() {

    var region = TextureRegion()

    val boundingRectangle: Rectangle
        get() = Rectangle(x, y, width, height)

    var velocityX = 0f
    var velocityY = 0f

    var texture: Texture? = null
        set(value) {
            field = value
            region.setRegion(value)
            if (value == null) return;
            width = value.width.toFloat()
            height = value.height.toFloat()
        }

    override fun act(delta: Float) {
        super.act(delta)
        moveBy(velocityX * delta, velocityY * delta)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.setColor(color.r, color.g, color.b, color.a)
        if (isVisible)
            batch?.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

}
