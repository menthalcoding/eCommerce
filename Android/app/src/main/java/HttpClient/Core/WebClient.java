package HttpClient.Core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Util.Constant;
import Util.SessionManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebClient {

    private static Retrofit retrofit = null;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder()
            //.registerTypeAdapter(Id.class, new IdTypeAdapter())
            //.enableComplexMapKeySerialization()
            //.serializeNulls()
            .setDateFormat("yyyy-MM-dd")
            //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            //.setPrettyPrinting()
            //.setVersion(1.0)
            .setLenient()
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .client(getUnsafeOkHttpClient())
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            /*
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                */

            //builder.client(httpClient.build());
            //retrofit = builder.build();
            //}
        }
        //builder.client(httpClient.build());
        builder.client(getUnsafeOkHttpClient());
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            //okHttpClient.interceptors().add(new APIRequestInterceptor());
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static class APIRequestInterceptor implements Interceptor {
        @Override
        // NB: OkHttp automatically adds an Accept-Encoding gzip, and transparently decompresses the response
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    //.addHeader("X-Application", ApiSessionManager.appKey)
                    .addHeader("X-Authentication", SessionManager.ACCESS_TOKEN)
                    .method(request.method(), request.body())
                    .build();

            Response response = chain.proceed(newRequest);
            return response;
        }
    }
}

