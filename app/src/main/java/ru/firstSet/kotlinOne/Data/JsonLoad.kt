//package ru.firstSet.kotlinOne
//
//import android.content.Context
//import android.os.Parcelable
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import kotlinx.parcelize.Parcelize
//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.decodeFromString
//import kotlinx.serialization.json.Json
//import ru.firstSet.kotlinOne.Data.Actor
//import ru.firstSet.kotlinOne.Data.GenreEntity
//import ru.firstSet.kotlinOne.Data.Movie
//
//
//private val jsonFormat = Json { ignoreUnknownKeys = true }
//
//@Parcelize
//@Serializable
//class JsonGenre(val id: Int, val name: String): Parcelable
//
//@Serializable
//private class JsonActor(
//    val id: Int,
//    val name: String,
//    @SerialName("profile_path")
//    val profilePicture: String
//)
//
//@Serializable
//public class JsonMovie(
//    var id: Long,
//    var title: String,
//    @SerialName("poster_path")
//    var posterPicture: String,
//    @SerialName("backdrop_path")
//    var backdropPicture: String,
//    var runtime: Int,
//    @SerialName("genre_ids")
//    var genreIds: List<Int>,
//    var actors: List<Int>,
//    @SerialName("vote_average")
//    var ratings: Float,
//    var overview: String,
//    var adult: Boolean,
//    var vote_count: Int
//)
//
//
////@Suppress("unused")
////internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
////    val genresMap = loadGenres(context)
////    val actorsMap = loadActors(context)
////
////    val data = readAssetFileToString(context, "data.json")
////    parseMovies(data, genresMap, actorsMap)
////}
//
//private suspend fun loadGenres(context: Context): List<GenreEntity> = withContext(Dispatchers.IO) {
//    val data = readAssetFileToString(context, "genres.json")
//    parseGenres(data)
//}
//
//private fun readAssetFileToString(context: Context, fileName: String): String {
//    val stream = context.assets.open(fileName)
//    return stream.bufferedReader().readText()
//}
//
//internal fun parseGenres(jsonString: String): List<GenreEntity> {
//    val jsonGenres = jsonFormat.decodeFromString<List<GenreEntity>>(jsonString)
//    return jsonGenres.map { jsonGenre -> GenreEntity(idGenre = jsonGenre.idGenre, name = jsonGenre.name,genreMovieId = 0)}
//}
//
//private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
//    val data = readAssetFileToString(context, "people.json")
//    parseActors(data)
//}
//
//internal fun parseActors(jsonString: String): List<Actor> {
//    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(jsonString)
//    return jsonActors.map { jsonActor ->
//        Actor(
//            id = jsonActor.id,
//            name = jsonActor.name,
//            picture = jsonActor.profilePicture
//        )
//    }
//}
//
//internal fun parseMovies(
//    jsonString: String,
//    genres: List<Genre>,
//    actors: List<Actor>
//): List<Movie> {
//    val genresMap = genres.associateBy { it.idGenre }
//    val actorsMap = actors.associateBy { it.id }
//
//    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(jsonString)
//
//    return jsonMovies.map { jsonMovie ->
//        @Suppress("unused")
//        Movie(
//            id = jsonMovie.id,
//            title = jsonMovie.title,
//            overview = jsonMovie.overview,
//            posterPicture = jsonMovie.posterPicture,
//            backdropPicture = jsonMovie.backdropPicture,
//            ratings = (jsonMovie.ratings / 2),
//            vote_count = jsonMovie.vote_count,
//            runtime = jsonMovie.runtime,
//            genres = jsonMovie.genreIds.map {
//                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
//            },
//            actors = jsonMovie.actors.map { id ->
//                actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
//            },
//            adult =  if (jsonMovie.adult) 16 else 13
//        )
//    }
//}
//
//private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
//    return this ?: throw createThrowable()
//}