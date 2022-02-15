package com.anseolab.lotty.view.base

interface ViewModelType<IN: ViewModelType.Input, OUT: ViewModelType.Output> {
    val input: IN

    val output: OUT

    interface Input

    interface Output
}