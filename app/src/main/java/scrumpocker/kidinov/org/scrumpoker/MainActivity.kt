package scrumpocker.kidinov.org.scrumpoker

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rlBackground.background = BackgroundDrawable(
                ContextCompat.getColor(this, R.color.magenta_background_base),
                ContextCompat.getColor(this, R.color.magenta_background_overlay))

        arrayOf(card1, card2, card3, card5, card8, card13, card21)
                .forEach { card ->
                    val shape = GradientDrawable()
                    shape.cornerRadius = 16f
                    shape.setColor(ContextCompat.getColor(this, R.color.turquoise))
                    card.background = shape
                    card.setOnClickListener { onCardClick(card) }
                }

        tvPickedCard.setOnClickListener { onPickedCardClick() }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private fun onPickedCardClick() {
        var textAnimation: ViewAnimator? = null
        if (tvPickedCard.tag != null) {
            tvPickedCard.text = tvPickedCard.tag.toString()
            tvPickedCard.tag = null
            textAnimation = ViewAnimator.animate(tvPickedCard)
                    .textColor(Color.TRANSPARENT, Color.BLACK)
                    .duration(500L)
                    .interpolator(DecelerateInterpolator())
                    .start()
        } else {
            textAnimation?.cancel()
            ViewAnimator
                    .animate(tvPickedCard)
                    .duration(300L)
                    .translationY(0f, -getScreenSize().second.toFloat())
                    .onStop {
                        tvPickedCard.background = ContextCompat.getDrawable(this, R.drawable.ic_card_back)
                        tvPickedCard.visibility = View.GONE
                    }
                    .start()
        }
    }

    private fun onCardClick(card: TextView) {
        tvPickedCard.text = ""
        tvPickedCard.tag = card.text
        ViewAnimator
                .animate(tvPickedCard)
                .duration(300L)
                .onStart { tvPickedCard.visibility = View.VISIBLE }
                .translationY(-getScreenSize().second.toFloat(), 0f)
                .onStop {
                    tvPickedCard.background = ContextCompat.getDrawable(this, R.drawable.ic_card_back)
                }
                .start()
    }

    private fun getScreenSize(): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}
