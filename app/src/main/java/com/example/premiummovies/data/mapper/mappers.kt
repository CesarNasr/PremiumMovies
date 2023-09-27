package com.example.premiummovies.data.mapper

import com.example.premiummovies.data.localdatasource.entity.genre.GenreDataEntity
import com.example.premiummovies.data.localdatasource.entity.genre.GenreListEntity
import com.example.premiummovies.data.localdatasource.entity.moviedetails.MovieDetailsEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieDataEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieListEntity
import com.example.premiummovies.data.remotedatasource.api.dto.genre.GenreDataDto
import com.example.premiummovies.data.remotedatasource.api.dto.genre.GenreListDto
import com.example.premiummovies.data.remotedatasource.api.dto.moviedetails.MovieDetailsDto
import com.example.premiummovies.data.remotedatasource.api.dto.moviedetails.SpokenLanguageDto
import com.example.premiummovies.data.remotedatasource.api.dto.movies.MovieDataDto
import com.example.premiummovies.data.remotedatasource.api.dto.movies.MovieListDto
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.moviedetails.SpokenLanguage
import com.example.premiummovies.domain.model.movies.MovieData
import com.example.premiummovies.domain.model.movies.MovieList
import javax.inject.Inject

/**
 * Each level of our app (local data source/ remote datasource / Domain) has it's own entity/ models and this class helps us map
 * them to one another
 */
class GenresMapper @Inject constructor() : DataMapper<GenreListDto, GenreList, GenreListEntity> {

    override fun mapFromDto(dto: GenreListDto): GenreList {
        val genresList = mutableListOf<GenreData>()
        for (genre in dto.genres) {
            genresList.add(genre.toGenre())
        }
        return GenreList(
            genres = genresList
        )
    }

    override fun mapToDto(domainModel: GenreList): GenreListDto {
        val genresList = mutableListOf<GenreDataDto>()
        for (genreDto in domainModel.genres) {
            genresList.add(genreDto.toGenreDto())
        }
        return GenreListDto(
            genres = genresList
        )
    }

    override fun mapFromLocalEntity(entity: GenreListEntity): GenreList {
        val genresList = mutableListOf<GenreData>()
        for (genre in entity.genres) {
            genresList.add(genre.toGenre())
        }
        return GenreList(
            genres = genresList
        )
    }

    override fun mapToEntity(domainModel: GenreList): GenreListEntity {
        val genresList = mutableListOf<GenreDataEntity>()
        for (genreDto in domainModel.genres) {
            genresList.add(genreDto.toGenreEntity())
        }
        return GenreListEntity(
            genres = genresList
        )
    }


    private fun GenreDataEntity.toGenre(): GenreData {
        return GenreData(
            id = id,
            name = name
        )
    }

    private fun GenreData.toGenreEntity(): GenreDataEntity {
        return GenreDataEntity(
            id = id,
            name = name
        )
    }

}

private fun GenreDataDto.toGenre(): GenreData {
    return GenreData(
        id = id,
        name = name
    )
}

private fun GenreData.toGenreDto(): GenreDataDto {
    return GenreDataDto(
        id = id,
        name = name
    )
}


class MovieMapper @Inject constructor() : DataMapper<MovieDataDto, MovieData, MovieDataEntity> {

    override fun mapFromDto(dto: MovieDataDto): MovieData {
        return MovieData(
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            id = dto.id,
            originalTitle = dto.originalTitle,
            posterPath = dto.posterPath,
            releaseDate = dto.releaseDate,
            title = dto.title
        )
    }

    override fun mapToDto(domainModel: MovieData): MovieDataDto {
        return MovieDataDto(
            backdropPath = domainModel.backdropPath,
            genreIds = domainModel.genreIds,
            id = domainModel.id,
            originalTitle = domainModel.originalTitle,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            title = domainModel.title
        )
    }

    fun mapToMovieListDomain(movieListDto: MovieListDto): MovieList {
        val movieList = mutableListOf<MovieData>()
        for (movie in movieListDto.results) {
            movieList.add(mapFromDto(movie))
        }

        return MovieList(
            page = movieListDto.page,
            results = movieList,
            totalPages = movieListDto.totalPages,
            totalResults = movieListDto.totalResults
        )
    }

    override fun mapFromLocalEntity(entity: MovieDataEntity): MovieData {
        return MovieData(
            backdropPath = entity.backdropPath,
            genreIds = entity.genreIds,
            id = entity.id,
            originalTitle = entity.originalTitle,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            title = entity.title
        )
    }

    override fun mapToEntity(domainModel: MovieData): MovieDataEntity {
        return MovieDataEntity(
            backdropPath = domainModel.backdropPath,
            genreIds = domainModel.genreIds,
            id = domainModel.id,
            originalTitle = domainModel.originalTitle,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            title = domainModel.title
        )
    }

    fun mapFromMovieListEntity(movieListEntity: MovieListEntity): MovieList {
        val movieList = mutableListOf<MovieData>()
        for (movie in movieListEntity.results) {
            movieList.add(mapFromLocalEntity(movie))
        }

        return MovieList(
            page = movieListEntity.page,
            results = movieList,
            totalPages = movieListEntity.totalPages,
            totalResults = movieListEntity.totalResults
        )
    }

    fun mapToMovieListEntity(movieList: MovieList): MovieListEntity {
        val movieData = mutableListOf<MovieDataEntity>()
        for (movie in movieList.results) {
            movieData.add(mapToEntity(movie))
        }

        return MovieListEntity(
            page = movieList.page,
            results = movieData,
            totalPages = movieList.totalPages,
            totalResults = movieList.totalResults
        )
    }
}


class MovieDetailsMapper @Inject constructor() :
    DataMapper<MovieDetailsDto, MovieDetails, MovieDetailsEntity> {


    override fun mapFromDto(dto: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            backdropPath = dto.backdropPath,
            budget = dto.budget,
            genres = getGenreData(dto.genres),
            id = dto.id,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            posterPath = dto.posterPath,
            releaseDate = dto.releaseDate,
            revenue = dto.revenue,
            runtime = dto.runtime,
            spokenLanguages = getSpokenLanguages(dto.spokenLanguages),
            status = dto.status,
            tagline = dto.tagline,
            title = dto.title,
            homePage = dto.homePage
        )
    }

    override fun mapToDto(domainModel: MovieDetails): MovieDetailsDto {
        return MovieDetailsDto(
            backdropPath = domainModel.backdropPath,
            budget = domainModel.budget,
            genres = getGenreDataDto(domainModel.genres),
            id = domainModel.id,
            originalTitle = domainModel.originalTitle,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            revenue = domainModel.revenue,
            runtime = domainModel.runtime,
            spokenLanguages = getSpokenLanguagesDto(domainModel.spokenLanguages),
            status = domainModel.status,
            tagline = domainModel.tagline,
            title = domainModel.title,
            homePage = domainModel.homePage
        )
    }

    private fun getGenreData(genres: List<GenreDataDto>): MutableList<GenreData> {
        val genresList = mutableListOf<GenreData>()
        for (genre in genres) {
            genresList.add(genre.toGenre())
        }
        return genresList
    }

    private fun getSpokenLanguages(spokenLanguagesDto: List<SpokenLanguageDto>): List<SpokenLanguage> {
        val spokenLanguage = mutableListOf<SpokenLanguage>()
        for (language in spokenLanguagesDto) {
            spokenLanguage.add(
                SpokenLanguage(
                    englishName = language.englishName,
                    name = language.name
                )
            )
        }

        return spokenLanguage
    }


    private fun getGenreDataDto(genres: List<GenreData>): MutableList<GenreDataDto> {
        val genresList = mutableListOf<GenreDataDto>()
        for (genre in genres) {
            genresList.add(genre.toGenreDto())
        }
        return genresList
    }

    private fun getSpokenLanguagesDto(spokenLanguagesDto: List<SpokenLanguage>): List<SpokenLanguageDto> {
        val spokenLanguageDto = mutableListOf<SpokenLanguageDto>()
        for (language in spokenLanguagesDto) {
            spokenLanguageDto.add(
                SpokenLanguageDto(
                    englishName = language.englishName,
                    name = language.name
                )
            )
        }

        return spokenLanguageDto
    }

    override fun mapFromLocalEntity(entity: MovieDetailsEntity): MovieDetails {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domainModel: MovieDetails): MovieDetailsEntity {
        TODO("Not yet implemented")
    }

}



