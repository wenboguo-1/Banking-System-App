package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.bankingsystem.R;

import controller.UserInfoController;

public class LoginFragment extends Fragment {

    private EditText id;
    private EditText pin;
    private AppCompatButton login_btt;
    private UserInfoController userInfoController;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.login_tap_fragment,container,false);
       id = viewGroup.findViewById(R.id.userId);
       pin = viewGroup.findViewById(R.id.password);
       login_btt = viewGroup.findViewById(R.id.login_btt);
       userInfoController = new UserInfoController(viewGroup.getContext());

    login_btt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempId = id.getText().toString();
            String tempPin = pin.getText().toString();
            if(tempId.length() <= 0){
                Toast.makeText(getActivity(),"Your id can not be empty",Toast.LENGTH_LONG).show();
                reset();
            }else if(tempPin.length() <= 0){
                Toast.makeText(getActivity(),"Your id can not be empty",Toast.LENGTH_LONG).show();
                reset();
            }else{
                userInfoController.login(Integer.parseInt(tempId),Integer.parseInt(tempPin));
                reset();
            }
        }
    });




       return viewGroup;
    }
    public boolean isInputValid(String id, String pin){
        try{
            Integer.parseInt(id);
            Integer.parseInt(pin);
        }catch (Exception e){
            return false;
        }

        return true;
    }
    public void reset(){
        id.setText("");
        pin.setText("");
    }
}
