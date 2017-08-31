package com.chinaredstar.core.okhttp.callback.tools;

import java.util.List;

public interface IGenericsParser {
    <T> T transformT(String response, Class<T> classOfT);

    <T> List<T> transformListT(String response, Class<T> classOfT);
}
