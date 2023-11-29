package ru.wondering.swtest.service.swapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ru.wondering.swtest.model.swapi.People;
import ru.wondering.swtest.model.swapi.Planet;
import ru.wondering.swtest.model.swapi.ResponseList;
import ru.wondering.swtest.model.swapi.Starship;

/**
 * Model for StarWars API
 * Only methods that are used are included
 */
public interface Swapi {

    /**
     * Search people by keyword with pagination
     */
    @GET("people")
    Call<ResponseList<People>> searchPeople(@Query("search") String search, @Query("page") int page);

    /**
     * Search starship by keyword with pagination
     */
    @GET("starships")
    Call<ResponseList<Starship>> searchStarships(@Query("search") String search, @Query("page") int page);

    /**
     * Get one starship by URL
     */
    @GET
    Call<Starship> getStarship(@Url String url);

    /**
     * Search planets by keyword with pagination
     */
    @GET("planets")
    Call<ResponseList<Planet>> searchPlanets(@Query("search") String search, @Query("page") int page);

}
