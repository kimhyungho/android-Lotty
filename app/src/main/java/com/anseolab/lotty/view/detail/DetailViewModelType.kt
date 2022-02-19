package com.anseolab.lotty.view.detail

import androidx.lifecycle.LiveData
import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.LotteryUiModel
import java.time.LocalDate

interface DetailViewModelType: ViewModelType<DetailViewModelType.Input, DetailViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun fetchLottery(lottery: Lottery?)

        fun onWordTextChange(word: String)

        fun onEditorAction()
    }

    interface Output: ViewModelType.Output {
        val word: LiveData<String>

        val lottery: LiveData<LotteryUiModel>

        val showNumberError: LiveData<Unit>
    }
}