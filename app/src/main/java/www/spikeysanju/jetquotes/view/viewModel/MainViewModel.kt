/*
 *
 *  *
 *  *  * MIT License
 *  *  *
 *  *  * Copyright (c) 2020 Sanju S
 *  *  *
 *  *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  * of this software and associated documentation files (the "Software"), to deal
 *  *  * in the Software without restriction, including without limitation the rights
 *  *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  * copies of the Software, and to permit persons to whom the Software is
 *  *  * furnished to do so, subject to the following conditions:
 *  *  *
 *  *  * The above copyright notice and this permission notice shall be included in all
 *  *  * copies or substantial portions of the Software.
 *  *  *
 *  *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  * SOFTWARE.
 *  *
 *
 */

package www.spikeysanju.jetquotes.view.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import www.spikeysanju.jetquotes.model.Quote
import www.spikeysanju.jetquotes.repository.MainRepository
import www.spikeysanju.jetquotes.utils.FavouriteViewState
import www.spikeysanju.jetquotes.utils.ViewState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _favState = MutableStateFlow<FavouriteViewState>(FavouriteViewState.Loading)

    // UI collects from this StateFlow to get it"s state update
    val uiState = _uiState.asStateFlow()
    val favState = _favState.asStateFlow()

    // get all quotes from assets folder
    fun getAllQuotes(context: Context) = viewModelScope.launch {
        try {

            // format/beautify Json
            val format = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }

            // read json file from assets
            val quotesJson = context.assets.open("quotes.json").bufferedReader().use {
                it.readText()
            }

            // decode list of string to Quote Item
            val quotesList = format.decodeFromString<List<Quote>>(quotesJson)
            _uiState.value = ViewState.Success(quotesList)

        } catch (e: Exception) {
            _uiState.value = ViewState.Error(exception = e)
        }
    }

    // get all favourites
    init {
        viewModelScope.launch {
            repository.getAllFavourites().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()) {
                    _favState.value = FavouriteViewState.Empty
                } else {
                    _favState.value = FavouriteViewState.Success(result)
                }
            }
        }
    }

    // insert favourite
    fun insertFavourite(quote: Quote) = viewModelScope.launch {
        repository.insert(quote)
    }

    // update favourite
    fun updateFavourite(quote: Quote) = viewModelScope.launch {
        repository.update(quote)
    }

    // delete favourite
    fun deleteFavourite(quote: Quote) = viewModelScope.launch {
        repository.delete(quote)
    }
}