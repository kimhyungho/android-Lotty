package com.anseolab.lotty.view.main.search

import com.anseolab.lotty.view.base.ViewModelType

interface SearchViewModelType: ViewModelType<SearchViewModelType.Input, SearchViewModelType.Output> {
    interface Input: ViewModelType.Input {
    }

    interface Output: ViewModelType.Output {
    }
}