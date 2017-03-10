package custom.selfapps.rav.calc.currency;

import android.content.Context;
import android.content.Loader;

import custom.selfapps.rav.calc.currency.model.Currency;
import custom.selfapps.rav.calc.utils.Logs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */

public class CurrencyLoader extends Loader<Currency> {
    private Retrofit retrofit;
    private CurrencyInterface service;
    private Currency currency = null;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public CurrencyLoader(Context context) {
        super(context);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.fixer.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(CurrencyInterface.class);


    }


    @Override
    public void deliverResult(Currency data) {
        Logs.send(getContext(),"deliverResult = "+data.toString());
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        Logs.send(getContext(),"Start Loading.............");
        super.onStartLoading();

    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Logs.send(getContext(),"onForceLoad()");
        service.getLatest().enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                currency = response.body();//Parsing value to Currency.class
                currency.setRate(currency.getBase(),1.0);//Add base value in rates
                Logs.send(getContext(),"currency="+ currency.toString());
                deliverResult(currency);
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {}
        });
    }
}
