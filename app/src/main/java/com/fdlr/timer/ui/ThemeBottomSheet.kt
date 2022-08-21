package com.fdlr.timer.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdlr.timer.R
import com.fdlr.timer.databinding.ThemeBottomSheetBinding
import com.fdlr.timer.views.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ThemeBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: ThemeBottomSheetBinding
    private var selectColor = COLOR_DEFAULT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThemeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicialSetup()
        setUpListeners()
    }

    private fun inicialSetup() {
        when (viewModel.getSavedTheme()) {
            COLOR_DEFAULT -> binding.colorDefault.strokeColor = resources.getColor(R.color.white)

            COLOR_GREEN -> binding.colorGreen.strokeColor = resources.getColor(R.color.white)

            COLOR_BLUE -> binding.colorBlue.strokeColor = resources.getColor(R.color.white)

            COLOR_RED -> binding.colorRed.strokeColor = resources.getColor(R.color.white)

            COLOR_GRAY -> binding.colorGray.strokeColor = resources.getColor(R.color.white)

            else -> binding.colorDefault.strokeColor = resources.getColor(R.color.white)
        }
    }

    private fun setUpListeners() {
        binding.apply {
            colorDefault.setOnClickListener {
                colorDefault.strokeColor = resources.getColor(R.color.white)
                colorGreen.strokeColor = resources.getColor(R.color.transparent)
                colorBlue.strokeColor = resources.getColor(R.color.transparent)
                colorRed.strokeColor = resources.getColor(R.color.transparent)
                colorGray.strokeColor = resources.getColor(R.color.transparent)
                setColorBtt.makeVisible()
                selectColor = COLOR_DEFAULT
                viewModel.setTheme(selectColor)
            }

            colorGreen.setOnClickListener {
                colorDefault.strokeColor = resources.getColor(R.color.transparent)
                colorGreen.strokeColor = resources.getColor(R.color.white)
                colorBlue.strokeColor = resources.getColor(R.color.transparent)
                colorRed.strokeColor = resources.getColor(R.color.transparent)
                colorGray.strokeColor = resources.getColor(R.color.transparent)
                setColorBtt.makeVisible()
                selectColor = COLOR_GREEN
                viewModel.setTheme(selectColor)
            }

            colorBlue.setOnClickListener {
                colorDefault.strokeColor = resources.getColor(R.color.transparent)
                colorGreen.strokeColor = resources.getColor(R.color.transparent)
                colorBlue.strokeColor = resources.getColor(R.color.white)
                colorRed.strokeColor = resources.getColor(R.color.transparent)
                colorGray.strokeColor = resources.getColor(R.color.transparent)
                setColorBtt.makeVisible()
                selectColor = COLOR_BLUE
                viewModel.setTheme(selectColor)
            }

            colorRed.setOnClickListener {
                colorDefault.strokeColor = resources.getColor(R.color.transparent)
                colorGreen.strokeColor = resources.getColor(R.color.transparent)
                colorBlue.strokeColor = resources.getColor(R.color.transparent)
                colorRed.strokeColor = resources.getColor(R.color.white)
                colorGray.strokeColor = resources.getColor(R.color.transparent)
                setColorBtt.makeVisible()
                selectColor = COLOR_RED
                viewModel.setTheme(selectColor)
            }

            colorGray.setOnClickListener {
                colorDefault.strokeColor = resources.getColor(R.color.transparent)
                colorGreen.strokeColor = resources.getColor(R.color.transparent)
                colorBlue.strokeColor = resources.getColor(R.color.transparent)
                colorRed.strokeColor = resources.getColor(R.color.transparent)
                colorGray.strokeColor = resources.getColor(R.color.white)
                setColorBtt.makeVisible()
                selectColor = COLOR_GRAY
                viewModel.setTheme(selectColor)
            }
            setColorBtt.setOnClickListener {
                viewModel.saveTheme(selectColor)
                dismiss()
                viewModel.isEditing(false)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onResume()
        viewModel.checkTheme()
        viewModel.isEditing(false)
        super.onDismiss(dialog)
    }

    companion object {
        lateinit var viewModel: TimerViewModel

        fun newInstance(
            viewModel: TimerViewModel
        ): ThemeBottomSheet {
            Companion.viewModel = viewModel
            return ThemeBottomSheet()
        }

        val TAG = ThemeBottomSheet::class.simpleName
        const val COLOR_DEFAULT = "COLOR_DEFAULT"
        const val COLOR_GREEN = "COLOR_GREEN"
        const val COLOR_BLUE = "COLOR_BLUE"
        const val COLOR_RED = "COLOR_RED"
        const val COLOR_GRAY = "COLOR_GRAY"
    }
}