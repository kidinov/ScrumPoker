package scrumpocker.kidinov.org.scrumpoker

import android.graphics.*
import android.graphics.drawable.Drawable


class BackgroundDrawable
constructor(private val backgroundComposition: BackgroundComposition, backgroundColorRes: Int, overlayColorRes: Int)
    : Drawable() {

    private val paintBackground = Paint()
    private val paintOverlay = Paint()
    private val rectBottom = RectF()
    private val rectDiagonal = RectF()

    init {
        paintBackground.setARGB(
                Color.alpha(backgroundColorRes),
                Color.red(backgroundColorRes),
                Color.green(backgroundColorRes),
                Color.blue(backgroundColorRes)
        )
        paintBackground.style = Paint.Style.FILL

        paintOverlay.setARGB(
                Color.alpha(overlayColorRes),
                Color.red(overlayColorRes),
                Color.green(overlayColorRes),
                Color.blue(overlayColorRes)
        )
        paintOverlay.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        // The background
        canvas.drawRect(bounds, paintBackground)

        // The left diagonal overlay rectangle
        rectDiagonal.bottom = bounds.bottom.toFloat()
        rectDiagonal.top = bounds.top.toFloat()
        rectDiagonal.left = bounds.left.toFloat()
        val rightCoordinate = bounds.right.toFloat() * RIGHT_COORDINATE_MULTIPLIER
        rectDiagonal.right = rightCoordinate

        canvas.save()
        canvas.rotate(22f, rightCoordinate, 0f)
        canvas.drawRect(rectDiagonal, paintOverlay)
        canvas.restore()

        when (backgroundComposition) {
        // The bottom overlay rectangle - high version
            BackgroundComposition.OVERLAP_HIGH -> {
                rectBottom.bottom = bounds.bottom.toFloat()
                rectBottom.top = bounds.bottom.toFloat() * BOTTOM_TOP_OVERLAP_HIGH_MULTIPLIER
                rectBottom.left = bounds.left.toFloat()
                rectBottom.right = bounds.right.toFloat()
                canvas.drawRect(rectBottom, paintOverlay)
            }
        // The bottom overlay rectangle - low version
            BackgroundComposition.OVERLAP_LOW -> {
                rectBottom.bottom = bounds.bottom.toFloat()
                rectBottom.top = bounds.bottom.toFloat() * BOTTOM_TOP_OVERLAP_LOW_MULTIPLIER
                rectBottom.left = bounds.left.toFloat()
                rectBottom.right = bounds.right.toFloat()
                canvas.drawRect(rectBottom, paintOverlay)
            }
        // The bottom rectangle with no overlap
            BackgroundComposition.NO_OVERLAP -> {
                rectBottom.bottom = bounds.bottom.toFloat()
                rectBottom.top = bounds.bottom.toFloat() * BOTTOM_TOP_NO_OVERLAP_MULTIPLIER
                rectBottom.left = bounds.left.toFloat()
                rectBottom.right = bounds.right.toFloat()
                canvas.drawRect(rectBottom, paintOverlay)
            }
            BackgroundDrawable.BackgroundComposition.SINGLE -> Unit
        }
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    enum class BackgroundComposition {
        OVERLAP_HIGH,
        OVERLAP_LOW,
        SINGLE,
        NO_OVERLAP
    }

    private companion object {
        const val RIGHT_COORDINATE_MULTIPLIER = 0.44f
        const val BOTTOM_TOP_OVERLAP_HIGH_MULTIPLIER = 0.15f
        const val BOTTOM_TOP_OVERLAP_LOW_MULTIPLIER = 0.35f
        const val BOTTOM_TOP_NO_OVERLAP_MULTIPLIER = 0.68f
    }
}
