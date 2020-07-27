package smworks.lt.helloandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smworks.lt.helloandroid.MainActivity;
import smworks.lt.helloandroid.User;
import smworks.lt.helloandroid.databinding.LoginScreenBinding;

public class LoginScreen extends Fragment {

    private static final Pattern emailPattern =
            Pattern.compile(
                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
            );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final LoginScreenBinding binding = LoginScreenBinding.inflate(inflater, container, false);

        MainActivity mainActivity = (MainActivity) requireActivity();

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailID.getText().toString().trim();
            String password = binding.passwordID.getText().toString().trim();

            mainActivity.getService().login(email, password).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
///
                    if ((response.isSuccessful()) && (emailPattern.matcher(email).matches())) {
                        assert user != null;
                        mainActivity.saveUser(user);
                        NavDirections action =
                                LoginScreenDirections.actionLoginScreenToDesktopScreen(email, password);
                        Navigation.findNavController(binding.getRoot()).navigate(action);

                    }else if (!emailPattern.matcher(email).matches()){
                        Toast.makeText(requireContext(), "Please enter a valid email address",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (email.equals("")){
                        Toast.makeText(requireContext(), "Email address required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (password.equals("")){
                        Toast.makeText(requireContext(), "Password required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (password.equals(password)){
                        Toast.makeText(requireContext(), "Password is incorrect",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
///
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
        binding.registrationID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action =
                        LoginScreenDirections.actionLoginScreenToRegistrationScreen();
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });
        return binding.getRoot();
    }

}

