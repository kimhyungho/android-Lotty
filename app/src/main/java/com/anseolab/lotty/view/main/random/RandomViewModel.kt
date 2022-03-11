package com.anseolab.lotty.view.main.random

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.extensions.getDrwNum
import com.anseolab.lotty.extensions.getNextSaturday
import com.anseolab.lotty.view.base.ReactorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet

@HiltViewModel
class RandomViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,
) : ReactorViewModel<RandomViewModel.Action, RandomViewModel.Mutation, RandomViewModel.State>(
    stateHandle, schedulerProvider
), RandomViewModelType, RandomViewModelType.Input, RandomViewModelType.Output {

    override fun onDrawButtonClick() =
        createAction(Action.DrawButtonClick)

    private val _aList: MutableLiveData<List<Int>> = MutableLiveData()
    override val aList: LiveData<List<Int>> get() = _aList

    private val _bList: MutableLiveData<List<Int>> = MutableLiveData()
    override val bList: LiveData<List<Int>> get() = _bList

    private val _cList: MutableLiveData<List<Int>> = MutableLiveData()
    override val cList: LiveData<List<Int>> get() = _cList

    private val _dList: MutableLiveData<List<Int>> = MutableLiveData()
    override val dList: LiveData<List<Int>> get() = _dList

    private val _eList: MutableLiveData<List<Int>> = MutableLiveData()
    override val eList: LiveData<List<Int>> get() = _eList

    private val _publishedDate: MutableLiveData<String> = MutableLiveData()
    override val publishedDate: LiveData<String> get() = _publishedDate

    private val _drawDate: MutableLiveData<String> = MutableLiveData()
    override val drawDate: LiveData<String> get() = _drawDate

    private val _dueDate:MutableLiveData<String> = MutableLiveData()
    override val dueDate: LiveData<String> get() = _dueDate

    private val _round: MutableLiveData<Long> = MutableLiveData()
    override val round: LiveData<Long> get() = _round

    override val input: RandomViewModelType.Input = this
    override val output: RandomViewModelType.Output = this

    init {
        state.select(State::aList)
            .bind(_aList)

        state.select(State::bList)
            .bind(_bList)

        state.select(State::cList)
            .bind(_cList)

        state.select(State::dList)
            .bind(_dList)

        state.select(State::eList)
            .bind(_eList)

        state.select(State::publishedDate)
            .map(this::publishedDateMapToView)
            .bind(_publishedDate)

        state.select(State::drawDate)
            .map(this::drawDateMapToView)
            .bind(_drawDate)

        state.select(State::drawDate)
            .map(this::dueDateMapToView)
            .bind(_dueDate)

        state.select(State::round)
            .bind(_round)
    }

    @SuppressLint("SimpleDateFormat")
    private fun publishedDateMapToView(date: Date): String {
        val format = SimpleDateFormat("발  행  일 : yyyy/MM/dd (E) HH:mm:ss")
        return format.format(date)
    }

    private fun drawDateMapToView(localDate: LocalDate): String {
        val format = DateTimeFormatter.ofPattern("추  첨  일 : yyyy/MM/dd (E) 20:45:00")
        return localDate.format(format)
    }

    private fun dueDateMapToView(localDate: LocalDate): String {
        val format = DateTimeFormatter.ofPattern("지급기한 : yyyy/MM/dd")
        return localDate.plusYears(1).plusDays(1).format(format)
    }
    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.DrawButtonClick -> {
                Observable.mergeArray(
                    Observable.just(Mutation.SetAList(createDrwtNo())),
                    Observable.just(Mutation.SetBList(createDrwtNo())),
                    Observable.just(Mutation.SetCList(createDrwtNo())),
                    Observable.just(Mutation.SetDList(createDrwtNo())),
                    Observable.just(Mutation.SetEList(createDrwtNo())),
                    Observable.just(Mutation.SetPublishedDate)
                )
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SetAList -> {
                state.copy(aList = mutation.aList)
            }

            is Mutation.SetBList -> {
                state.copy(bList = mutation.bList)
            }

            is Mutation.SetCList -> {
                state.copy(cList = mutation.cList)
            }

            is Mutation.SetDList -> {
                state.copy(dList = mutation.dList)
            }

            is Mutation.SetEList -> {
                state.copy(eList = mutation.eList)
            }

            is Mutation.SetPublishedDate -> {
                state.copy(
                    publishedDate = Date(),
                    round = Date().getDrwNum() + 1,
                    drawDate = LocalDate.now().getNextSaturday()
                )
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object DrawButtonClick : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetAList(val aList: List<Int>) : Mutation

        class SetBList(val bList: List<Int>) : Mutation

        class SetCList(val cList: List<Int>) : Mutation

        class SetDList(val dList: List<Int>) : Mutation

        class SetEList(val eList: List<Int>) : Mutation

        object SetPublishedDate : Mutation
    }

    private fun createDrwtNo(): List<Int> {
        val range = (1..45)
        val drwtNo = HashSet<Int>()

        while (drwtNo.size < 7) {
            drwtNo.add(range.random())
        }

        return drwtNo.sorted()
    }

    data class State(
        val aList: List<Int> = listOf(0, 0, 0, 0, 0, 0),
        val bList: List<Int> = listOf(0, 0, 0, 0, 0, 0),
        val cList: List<Int> = listOf(0, 0, 0, 0, 0, 0),
        val dList: List<Int> = listOf(0, 0, 0, 0, 0, 0),
        val eList: List<Int> = listOf(0, 0, 0, 0, 0, 0),
        val publishedDate: Date = Date(),
        val drawDate: LocalDate = LocalDate.now().getNextSaturday(),
        val round: Long = Date().getDrwNum() + 1
    ) : ReactorViewModel.State {

    }
}