package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.bankingsystem.R;

import controller.UserInfoController;

public class SignupFragment extends Fragment {
    private EditText gender;
    private EditText age;
    private EditText password_signUp;
    private EditText password_confirm;
    private AppCompatButton bt_sign;
    private EditText name_signUp;
    private UserInfoController userInfoController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.signup_tap_fragment,container,false);
        userInfoController = new UserInfoController(viewGroup.getContext());
        name_signUp = viewGroup.findViewById(R.id.userName_signup);
        gender = viewGroup. findViewById(R.id.gender);
        age =viewGroup. findViewById(R.id.age);
        gender = viewGroup.findViewById(R.id.gender);
        password_signUp = viewGroup.findViewById(R.id.password_signup);
        password_confirm = viewGroup.findViewById(R.id.password_confirm);
        bt_sign = (AppCompatButton)viewGroup. findViewById(R.id.signupButt);


        bt_sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = name_signUp.getText().toString();
                String tempGender = gender.getText().toString();
                String tempAge = age.getText().toString();
                String tempPassword = password_signUp.getText().toString();
                String tempPasswordConfirmed = password_confirm.getText().toString();
                String errMsg = "";

                if (name.isEmpty() || tempGender.isEmpty() || tempAge.isEmpty() || tempPassword.isEmpty() || tempPasswordConfirmed.isEmpty()) {
                    Toast.makeText(getActivity(),"None of your input bar can be empty", Toast.LENGTH_LONG).show();
                    reset();
                    return;
                }
                errMsg = getSignupErrMsg(name,tempGender,tempAge,tempPassword,tempPasswordConfirmed);
                if(errMsg.equals("Success")) {
                    String id = userInfoController.newUser(name, tempGender.charAt(0), Integer.parseInt(tempAge), Integer.parseInt(tempPassword));
                    reset();

                }


            }
        });
        return viewGroup;
    }

    public void reset(){
        name_signUp.setText("");
        gender.setText("");
        age.setText("");
        password_confirm.setText("");
        password_signUp.setText("");
    }

    public String getSignupErrMsg(String name,String gender,String age,String password, String confirmedPassword){

        Log.e(null,gender.toLowerCase());
        if(name.length() >= 15){
            return "The name you entered is too long";
        }if(gender.length() > 1 && (gender.toLowerCase().charAt(0) != 'm' && gender.toLowerCase().charAt(0) != 'f')){
            return "The gender you entered is not the valid";
        } if(!password.equals(confirmedPassword) ){
            return "Your passwords do not match each other";
        }
        return "Success";
    }
}
