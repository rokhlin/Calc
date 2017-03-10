package custom.selfapps.rav.calc.currency.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import custom.selfapps.rav.calc.R;

/**
 * JSON Answer example
 * {"base":"EUR",
 * "date":"2016-12-28",
 * "rates":{"AUD":1.4492,"BGN":1.9558,"BRL":3.4026,"CAD":1.4119,"CHF":1.0714,"CNY":7.2384,"CZK":27.042,"DKK":7.4351,
 * "GBP":0.85128,"HKD":8.0694,"HRK":7.5545,"HUF":309.03,"IDR":13981.54,"ILS":4.0075,"INR":70.9685,"JPY":122.39,"KRW":1261.97,"MXN":21.57,"MYR":4.6633,
 * "NOK":9.0777,"NZD":1.505,"PHP":51.845,"PLN":4.4028,"RON":4.5415,"RUB":62.9938,"SEK":9.5783,"SGD":1.5107,"THB":37.475,"TRY":3.6882,"USD":1.0401,"ZAR":14.4535}}
 */

public class Currency implements Parcelable {
    private String base;
    private Date date;

    @SerializedName("rates")
    Map<String,Double> rates;

    public Currency() {}

    protected Currency(Parcel in) {
        this.base = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        int ratesSize = in.readInt();
        this.rates = new HashMap<String, Double>(ratesSize);
        for (int i = 0; i < ratesSize; i++) {
            String key = in.readString();
            Double value = (Double) in.readValue(Double.class.getClassLoader());
            this.rates.put(key, value);
        }
    }



    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public void setRate(String name, double value) {
        this.rates.put(name,value);
    }

    public String[] getCurrencyNames(){
        String[] res = new String[rates.size()];
        Iterator<String> iterator = rates.keySet().iterator();
        for (int i = 0; i <res.length ; i++) {
            if(iterator.hasNext())  res[i] = iterator.next();
        }
        return res;
    }


    @Override
    public String toString() {
        return "Currency{" +
                "base='" + base + '\'' +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }

    /**
     *
     * @param value how money need to convert
     * @param from which currency will convert
     * @param to which currency need return result
     * @return exchange value
     */
    public double getRelation(double value, String from, String to) {
        return (value / rates.get(from)) * rates.get(to);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.base);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.rates.size());
        for (Map.Entry<String, Double> entry : this.rates.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
    }


    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}
