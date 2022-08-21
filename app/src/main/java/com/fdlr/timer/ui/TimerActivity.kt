package com.fdlr.timer.ui

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fdlr.timer.R
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_BLUE
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_DEFAULT
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_GRAY
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_GREEN
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_RED
import com.fdlr.timer.databinding.ActivityMainBinding
import com.fdlr.timer.databinding.ActivityMainBinding.inflate
import com.fdlr.timer.databinding.IncludeSelectTimeBinding
import com.fdlr.timer.model.TimerSet
import com.fdlr.timer.utils.Constants.MIN_TIMER
import com.fdlr.timer.utils.Constants.SPLIT_TIMER
import com.fdlr.timer.views.makeGone
import com.fdlr.timer.views.makeVisible
import com.fdlr.timer.views.transparentBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String
import java.util.concurrent.TimeUnit


@SuppressLint("DefaultLocale")
class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingInclude: IncludeSelectTimeBinding
    private val viewModel: TimerViewModel by viewModel()

    var countDownTimer: CountDownTimer? = null
    var timerTotal: Long = 0
    var clickSound: MediaPlayer? = null
    var timerSound: MediaPlayer? = null
    var stopSound: MediaPlayer? = null
    var setSound: MediaPlayer? = null
    var isRunning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        bindingInclude = binding.selectTimer
        setContentView(binding.root)
        val w: Window = window
        transparentBar(w)
        setUpListeners()
        setUpSounds()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        if (binding.time.text == MIN_TIMER)
            binding.play.makeGone()
    }

    private fun setUpSounds() {
        clickSound = MediaPlayer.create(this, R.raw.click)
        timerSound = MediaPlayer.create(this, R.raw.timer)
        stopSound = MediaPlayer.create(this, R.raw.stop)
        setSound = MediaPlayer.create(this, R.raw.set)
    }

    private fun setObservers() {
        viewModel.stop.observe(this, {
            if (it == true) {
                resetTimerSound()
                binding.restartBtt.makeGone()
            }
        })

        viewModel.onDismiss.observe(this, {
            if (it == true) {
                binding.turnOffBtt.makeVisible()
                viewModel.onDismissClick(false)
            }
        })

        viewModel.checkTimer.observe(this, { isAble ->
            when (isAble) {
                true -> bindingInclude.setTimerBtt.makeVisible()
                false -> bindingInclude.setTimerBtt.makeGone()
            }
        })

        viewModel.theme.observe(this, { color ->
            when (color) {
                COLOR_DEFAULT -> {
                    binding.maskProgress.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient)
                    binding.background.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient)
                }
                COLOR_GREEN -> {
                    binding.maskProgress.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_green)
                    binding.background.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_green)
                }
                COLOR_BLUE -> {
                    binding.maskProgress.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_blue)
                    binding.background.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_blue)
                }
                COLOR_RED -> {
                    binding.maskProgress.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_red)
                    binding.background.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_red)
                }
                COLOR_GRAY -> {
                    binding.maskProgress.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_gray)
                    binding.background.background =
                        ContextCompat.getDrawable(this, R.drawable.bg_gradient_gray)
                }
            }
        })
    }

    private fun setUpListeners() {
        val seconds = resources.getStringArray(R.array.seconds)
        bindingInclude.minPicker.maxValue = 60
        bindingInclude.minPicker.minValue = 0
        bindingInclude.secondPicker.minValue = 0
        bindingInclude.secondPicker.maxValue = 60
        bindingInclude.secondPicker.displayedValues = seconds

        binding.time.setOnClickListener {
            binding.stop.makeGone()
            binding.themeBtt.makeGone()
            if (isRunning) {
                countDownTimer?.let { it.cancel() }
                stopSound?.start()
                bindingInclude.root.makeVisible()
            } else {
                bindingInclude.root.makeVisible()
            }
            binding.play.makeGone()
            binding.restartBtt.makeGone()
        }

        binding.borderTimer.setOnClickListener {
            binding.stop.makeGone()
            binding.themeBtt.makeGone()
            if (isRunning) {
                countDownTimer?.let { it.cancel() }
                stopSound?.start()
                bindingInclude.root.makeVisible()
            } else {
                bindingInclude.root.makeVisible()
            }
            binding.play.makeGone()
            binding.restartBtt.makeGone()
        }

        binding.play.setOnClickListener {
            binding.stop.makeVisible()
            binding.restartBtt.makeVisible()
            binding.turnOffBtt.makeVisible()
            binding.play.makeGone()
            val timeSet = binding.time.text
            val time = timeSet.split(SPLIT_TIMER).toTypedArray()
            setTimer(time[0].toInt(), time[1].toInt())
            viewModel.savePreviewTimer(TimerSet(min = time[0].toInt(), second = time[1].toInt()))
            clickSound?.start()
        }

        binding.restartBtt.setOnClickListener {
            binding.play.makeGone()
            countDownTimer?.let { it.cancel() }
            setProgress(timerTotal)
            resetTimerSound()
            Handler().postDelayed({
                val timer = viewModel.getPreviewTimer()
                setTimer(timer.min, timer.second)
            }, 200)
            clickSound?.start()
            binding.stop.makeVisible()
            binding.turnOffBtt.makeVisible()

        }

        binding.turnOffBtt.setOnClickListener {
            resetTimerSound()
            binding.restartBtt.makeGone()
        }

        binding.stop.setOnClickListener {
            binding.play.makeVisible()
            binding.restartBtt.makeVisible()
            binding.stop.makeGone()
            (countDownTimer as CountDownTimer).cancel()
            stopSound?.start()
        }

        bindingInclude.closeBtt.setOnClickListener {
            bindingInclude.root.makeGone()
            binding.themeBtt.makeVisible()
            binding.play.makeVisible()
            if (binding.time.text == MIN_TIMER) {
                resetScene()
            }
        }

        binding.themeBtt.setOnClickListener {
            showBottomSheetTheme()
        }

        bindingInclude.setTimerBtt.setOnClickListener {
            resetTimerSound()
            bindingInclude.root.makeGone()
            binding.play.makeVisible()
            binding.themeBtt.makeVisible()
            val minutes = bindingInclude.minPicker.value
            val seconds = seconds[bindingInclude.secondPicker.value]
            countDownTimer?.let { it.cancel() }
            binding.time.text =
                String.format(getString(R.string.timer_display), minutes.toString(), seconds)
            timerTotal = minutes * 1000 * 60 + seconds.toLong() * 1000
            setProgress(timerTotal)
            setSound?.start()
            if (binding.time.text == MIN_TIMER) {
                binding.play.makeGone()
            }
        }

        bindingInclude.minPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.checkTime(newVal, bindingInclude.secondPicker.value)
        }

        bindingInclude.secondPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.checkTime(bindingInclude.minPicker.value, newVal)
        }

    }

    private fun resetScene() {
        binding.play.makeGone()
        binding.progressBar.progress = 0
        binding.pointer.rotation = 0f
        binding.maskProgress.makeVisible()
    }

    private fun resetTimerSound() {
        isRunning = false
        countDownTimer?.let { it.cancel() }
        resetScene()
        timerSound?.stop()
        timerSound = MediaPlayer.create(this, R.raw.timer)
        timerSound?.isLooping = true
        binding.turnOffBtt.makeGone()
        binding.time.text = MIN_TIMER
        binding.stop.makeGone()
        binding.play.makeGone()
        binding.borderTimer.animation = null
        binding.borderTimer.background = ContextCompat.getDrawable(this, R.drawable.ic_border_shape)
        binding.bells.makeGone()
        binding.bells.animation = null
    }

    private fun setTimer(minutes: Int, seconds: Int) {
        val totalSeconds = minutes * 1000 * 60 + seconds * 1000
        countDownTimer = object : CountDownTimer(totalSeconds.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min: Long = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val second = (millisUntilFinished / 1000 % 60)
                val formattedSecond = String.format("%02d", second)
                binding.time.text = "$min:$formattedSecond"
                setProgress(millisUntilFinished)
                isRunning = true
            }

            override fun onFinish() {
                binding.time.text = MIN_TIMER
                binding.pointer.rotation = 0f
                binding.play.makeGone()
                binding.stop.makeGone()
                binding.restartBtt.makeVisible()
                setProgress(timerTotal)
                timerSound?.isLooping = true
                timerSound?.start()
                showBottomSheetDismissTimer()
            }
        }
        (countDownTimer as CountDownTimer).start()
    }

    private fun showBottomSheetDismissTimer() {
        isRunning = false
        val bottomSheetDialog = TurnOffTimerBottomSheet.newInstance(viewModel)
        bottomSheetDialog.theme
        if (viewModel.isOnEditing() == true) {
            viewModel.onDismissClick(true)
        } else {
            bottomSheetDialog.show(supportFragmentManager, TurnOffTimerBottomSheet.TAG)
        }
        binding.borderTimer.background =
            ContextCompat.getDrawable(this, R.drawable.ic_border_shape_no_alpha)
        binding.borderTimer.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.fadein_fadeout
            )
        )
        binding.bells.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein_fadeout))
        binding.bells.makeVisible()
    }

    private fun showBottomSheetTheme() {
        val bottomSheetDialog = ThemeBottomSheet.newInstance(viewModel)
        bottomSheetDialog.theme
        bottomSheetDialog.show(supportFragmentManager, ThemeBottomSheet.TAG)
        viewModel.isEditing(true)
    }

    private fun setProgress(millisUntilFinished: Long) {
        var progress = 0.0
        progress = if (millisUntilFinished == timerTotal) {
            (((millisUntilFinished.toDouble()) / timerTotal.toDouble()) * 360)
        } else {
            (((millisUntilFinished.toDouble() - 1000) / timerTotal.toDouble()) * 360)
        }

        binding.pointer.rotation = if (progress >= 0.0) (progress.toFloat() - 360) * -1 else 0F
        binding.progressBar.progress = if (progress != 0.0) progress.toInt() else 1
        if (progress <= 180) {
            binding.maskProgress.makeGone()
        } else {
            binding.maskProgress.makeVisible()
        }
    }
}