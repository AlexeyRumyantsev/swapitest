package ru.wondering.swtest.service.swapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.wondering.swtest.model.swapi.ResponseList;
import ru.wondering.swtest.util.IntFunctionWithException;
import ru.wondering.swtest.util.LocalizedDeserializationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Utilities used in Retrofit related code
 */
@Log4j2
public final class RetrofitUtil {

    private RetrofitUtil() {
    }

    /**
     * Create Retrofit object with given parameters.
     * @param locale used to support localized numbers in API (like "234,555" for US)
     * @param baseUrl base URL of API
     */
    public static Retrofit createRetrofit(Locale locale, String baseUrl, int connectTimeout, int readTimeout) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(
                        new ObjectMapper(null, null, new LocalizedDeserializationContext(locale))
                                .registerModule(new JavaTimeModule())
                                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)))
                .client(httpClientBuilder.build())
                .build();
    }

    /**
     * Since search API will use case-insensitive partial matches on the set of search fields,
     * as described in documentation, we may need to find exact match ourselves, handling multipage result
     *
     * @param searchFunction function that performs request to server for given page number
     * @param accept         exact match test function
     * @return exact match, if found, and null otherwise
     */
    public static <T> T search(IntFunctionWithException<Response<ResponseList<T>>> searchFunction, Predicate<T> accept)
            throws IOException {
        int page = 1;
        boolean continueSearch = true;
        do {
            ResponseList<T> result = checkAndUnwrapResponse(searchFunction.apply(page));
            page++;
            if (result != null) {
                Optional<T> item = Optional.ofNullable(result.getResults()).orElse(Collections.emptyList())
                        .stream().filter(accept).findFirst();
                if (item.isPresent()) {
                    return item.get();
                }
                continueSearch = result.getNext() != null;
            }
        } while (continueSearch);
        return null;
    }

    /**
     * Check for error and unwrap API response
     */
    public static <T> T checkAndUnwrapResponse(Response<T> response) throws IOException {
        if (response.isSuccessful()) {
            return response.body();
        }
        log.error("Error calling API, response {}", response.message());
        throw new IOException("API call issue: " + response.message());
    }
}
