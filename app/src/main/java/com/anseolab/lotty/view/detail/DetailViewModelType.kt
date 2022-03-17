package com.anseolab.lotty.view.detail

import androidx.lifecycle.LiveData
import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.RecentLotteryUiModel
import java.time.LocalDate

interface DetailViewModelType: ViewModelType<DetailViewModelType.Input, DetailViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun fetchLottery(drwNo: Long?)

        fun onWordTextChange(word: String)

        fun onEditorAction()

        fun onClearButtonClick()

        fun onDrwNoItemClick(drwNo: Long)

        fun onDeleteItemClick(drwNo: Long)

        fun onTextClearButtonClick()
    }

    interface Output: ViewModelType.Output {
        val word: LiveData<String>

        val recentLotteries: LiveData<List<RecentLotteryUiModel>>

        val lottery: LiveData<LotteryUiModel>

        val showNumberError: LiveData<Unit>
    }
}