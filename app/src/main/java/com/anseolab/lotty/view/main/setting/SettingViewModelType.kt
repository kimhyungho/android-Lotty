package com.anseolab.lotty.view.main.setting

import com.anseolab.lotty.view.base.ViewModelType

interface SettingViewModelType:
    ViewModelType<SettingViewModelType.Input, SettingViewModelType.Output> {
    interface Input: ViewModelType.Input {
    }

    interface Output: ViewModelType.Output {
    }
}