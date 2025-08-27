package com.mfm.mockforme;

import com.mockforme.MockForMe;
import okhttp3.OkHttpClient;



public final class Network {
    private Network() {} // no instances

    /** Get the shared OkHttpClient (mocking enabled in debug). */
    public static OkHttpClient getClient() {
        return Holder.CLIENT;
    }

    /** Lazy-loaded, thread-safe holder. */
    private static final class Holder {
        static final OkHttpClient CLIENT = build();

        private static OkHttpClient build() {
            OkHttpClient.Builder b = new OkHttpClient.Builder();
            // Add MockForMe interceptor only for debug builds
            if (BuildConfig.DEBUG) {
                // Requires @JvmStatic fun install(...) in your library
                b = MockForMe.install(b);
            }
            return b.build();
        }
    }
}
