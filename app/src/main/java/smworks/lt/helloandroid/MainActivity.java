package smworks.lt.helloandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {


    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private ApiService service;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4567")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }

    public ApiService getService() {
        return service;
    }

    public void saveUser (User user){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.commit();
    }

    public User getUser (){
        String email = sharedPref.getString(EMAIL, "");
        String password = sharedPref.getString(PASSWORD, "");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

}

