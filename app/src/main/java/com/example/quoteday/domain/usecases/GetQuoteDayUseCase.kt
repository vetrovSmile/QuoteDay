package com.example.quoteday.domain.usecases

import com.example.quoteday.domain.RepositoryQuotes
import javax.inject.Inject

class GetQuoteDayUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {
    suspend operator fun invoke() = repositoryQuotes.requestQuoteDay()
}