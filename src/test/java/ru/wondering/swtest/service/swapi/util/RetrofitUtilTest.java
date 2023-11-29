package ru.wondering.swtest.service.swapi.util;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.wondering.swtest.model.swapi.ResponseList;
import ru.wondering.swtest.util.IntFunctionWithException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RetrofitUtilTest {

    @Test
    void searchSuccess() throws IOException {
        final IntFunctionWithException<Response<ResponseList<String>>> results = getSampleResponse();

        String result = RetrofitUtil.search(results, x -> x.equals("option5"));
        assertEquals("option5", result);
    }

    @Test
    void searchNotFound() throws IOException {
        final IntFunctionWithException<Response<ResponseList<String>>> results = getSampleResponse();

        String result = RetrofitUtil.search(results, x -> x.equals("very much appreciated option"));
        assertNull(result);
    }

    @NotNull
    private static IntFunctionWithException<Response<ResponseList<String>>> getSampleResponse() {
        IntFunctionWithException<Response<ResponseList<String>>> results = page -> {
            if (page == 1) {
                return Response.success(ResponseList.<String>builder()
                        .count(3)
                        .next("has next items")
                        .results(List.of("option1", "option2", "option3"))
                        .build());
            } else if (page == 2) {
                return Response.success(ResponseList.<String>builder()
                        .count(3)
                        .previous("has previous items")
                        .results(List.of("option4", "option5", "option6"))
                        .build());
            }
            throw new IllegalStateException();
        };
        return results;
    }

    @Test
    void checkAndUnwrapResponseSuccessful() throws IOException {
        Response<String> response = Response.success("ok");
        assertEquals("ok", RetrofitUtil.checkAndUnwrapResponse(response));
    }

    @Test
    void checkAndUnwrapResponseFailed() {
        ResponseBody body = mock(ResponseBody.class);
        Response<String> response = Response.error(500, body);
        assertThrows(IOException.class, () -> RetrofitUtil.checkAndUnwrapResponse(response));
    }
}