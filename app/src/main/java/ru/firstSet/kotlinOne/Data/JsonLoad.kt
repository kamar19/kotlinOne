package ru.firstSet.kotlinOne

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.firstSet.kotlinOne.Data.Actor
import ru.firstSet.kotlinOne.Data.Movie


private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
public class JsonMovie(
    val id: Int,  // в Get Details есть
    var title: String,  // есть
    @SerialName("poster_path")  // есть
    var posterPicture: String,
    @SerialName("backdrop_path") // есть
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids") // есть
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average") // есть
    val ratings: Float, //НЕТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТТт
    val overview: String, // есть
    val adult: Boolean, // есть
    val vote_count: Int // есть
)

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
}

internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): List<Movie> {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            posterPicture = jsonMovie.posterPicture,
            backdropPicture = jsonMovie.backdropPicture,
            ratings = jsonMovie.ratings,
            runtime = jsonMovie.runtime,
            genreIds = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = jsonMovie.actors.map {
                actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
            },
            vote_count = jsonMovie.vote_count,
            adult = if (jsonMovie.adult) 16 else 13,
        )
    }
}