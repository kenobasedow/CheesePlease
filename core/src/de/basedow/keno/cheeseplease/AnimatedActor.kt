package de.basedow.keno.cheeseplease

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils

open class AnimatedActor : BaseActor() {

    private var elapsedTime = 0f

    var animation: Animation<TextureRegion>? = null
        set(value) {
            field = value
            if (value == null) return
            texture = value.getKeyFrame(0f).texture
        }

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += delta
        if (velocityX != 0f || velocityY != 0f)
            rotation = MathUtils.atan2(velocityY, velocityX) * MathUtils.radiansToDegrees
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        region.setRegion(animation?.getKeyFrame(elapsedTime))
        super.draw(batch, parentAlpha)
    }
}