package view;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter  extends FragmentPagerAdapter {
     private Context context;
     int totalTaps;
     public LoginAdapter(FragmentManager fm, Context context,int totalTaps){
         super(fm);
         this.context = context;
         this.totalTaps = totalTaps;

     }

    @Override
    public int getCount() {
        return totalTaps;
    }

    public Fragment getItem(int position){
         switch (position){
             case 0:
                  LoginFragment loginFragment = new LoginFragment();
                  return loginFragment;
             case 1:
                  SignupFragment signupFragment = new SignupFragment();
                  return signupFragment;
         }
         return null;
     }
}
