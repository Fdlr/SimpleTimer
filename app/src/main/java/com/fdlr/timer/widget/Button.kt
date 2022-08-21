package com.fdlr.timer.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import com.fdlr.timer.R
import com.fdlr.timer.databinding.CustomButtonBinding
import com.fdlr.timer.views.makeGone
import com.fdlr.timer.views.makeVisible

@SuppressLint("ResourceType")
class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private var icon: Int = 0
    private var binding: CustomButtonBinding = CustomButtonBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        parseAttributes(attrs)
        setUpLayout()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        context.withStyledAttributes(
            attrs,
            R.styleable.ButtonView,
            0
        ) {
            attrs?.let {
                icon = getResourceId(R.styleable.ButtonView_icon, R.drawable.ic_play)

                if (hasValue(R.styleable.ButtonView_IsSmall)) {
                    binding.largeButton.makeGone()
                    binding.mediumButton.makeGone()
                    binding.smallButton.makeVisible()
                }
                if (hasValue(R.styleable.ButtonView_IsMedium)) {
                    binding.largeButton.makeGone()
                    binding.mediumButton.makeVisible()
                    binding.smallButton.makeGone()
                }
                if (hasValue(R.styleable.ButtonView_IsLarge)) {
                    binding.largeButton.makeVisible()
                    binding.mediumButton.makeGone()
                    binding.smallButton.makeGone()
                }
            }
        }
    }

    private fun setUpLayout() {
        binding.icon.setBackgroundResource(icon)
        binding.icon2.setBackgroundResource(icon)
        binding.icon3.setBackgroundResource(icon)
    }

    fun setOnClickListener(callbackFunction: () -> Unit) {
        binding.ripple.setOnClickListener {
            Handler().postDelayed({
                callbackFunction.invoke()
            }, 150)
        }

        binding.ripple2.setOnClickListener {
            Handler().postDelayed({
                callbackFunction.invoke()
            }, 150)
        }

        binding.ripple3.setOnClickListener {
            Handler().postDelayed({
                callbackFunction.invoke()
            }, 150)
        }
    }

}