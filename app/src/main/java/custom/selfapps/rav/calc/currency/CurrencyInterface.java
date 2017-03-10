package custom.selfapps.rav.calc.currency;


import custom.selfapps.rav.calc.currency.model.Currency;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit REST interface
 */
 interface CurrencyInterface {
    @GET("/latest")
    Call<Currency> getLatest();
}
