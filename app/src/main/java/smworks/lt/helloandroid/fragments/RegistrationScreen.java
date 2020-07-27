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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smworks.lt.helloandroid.MainActivity;
import smworks.lt.helloandroid.databinding.RegistrationScreenBinding;

public class RegistrationScreen extends Fragment {

    private static final Pattern passwordPattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=.,*_()!])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

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
        final RegistrationScreenBinding binding = RegistrationScreenBinding.
                inflate(inflater, container, false);

        MainActivity mainActivity = (MainActivity) requireActivity();

        binding.registrationButton.setOnClickListener(v -> {
            String email = binding.emailID.getText().toString().trim();
            String password = binding.passwordID.getText().toString().trim();
            String confirmPassword = binding.passwordConfirmID.getText().toString().trim();

            mainActivity.getService().registration(email, password, confirmPassword).
                    enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if ((response.isSuccessful()) && (password.equals(confirmPassword))
                            && (passwordPattern.matcher(password).matches())) {
                        NavDirections action =
                                RegistrationScreenDirections.
                                        actionRegistrationScreenToLoginScreen(email, password, confirmPassword);
                        Navigation.findNavController(binding.getRoot()).navigate(action);

                    }
                    else if (email.equals("")){
                        Toast.makeText(requireContext(), "Email address required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (!emailPattern.matcher(email).matches()){
                        Toast.makeText(requireContext(), "Enter valid email address",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (password.equals("")){
                        Toast.makeText(requireContext(), "Password required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (confirmPassword.equals("")){
                        Toast.makeText(requireContext(), "Confirm password required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (!password.equals(confirmPassword)){
                        Toast.makeText(requireContext(), "Password does not match",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (!passwordPattern.matcher(password).matches()) {
                        Toast.makeText(requireContext(), "Password is to weak",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
///
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(requireContext(), "fail",
                            Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        });
        return binding.getRoot();

    }
}

