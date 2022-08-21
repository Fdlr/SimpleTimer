package com.fdlr.timer.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdlr.timer.R
import com.fdlr.timer.databinding.TurnoffTimerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TurnOffTimerBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: TurnoffTimerBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TurnoffTimerBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            turnOffBtt.setOnClickListener {
                this@TurnOffTimerBottomSheet.dismiss()
                viewModel.stopTimer(true)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onResume()
        viewModel.onDismissClick(true)
        super.onDismiss(dialog)
    }

    companion object {
        lateinit var viewModel: TimerViewModel

        fun newInstance(
            viewModel: TimerViewModel
        ): TurnOffTimerBottomSheet {
            Companion.viewModel = viewModel
            return TurnOffTimerBottomSheet()
        }

        val TAG = TurnOffTimerBottomSheet::class.simpleName
    }
}