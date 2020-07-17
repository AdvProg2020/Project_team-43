package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Supporter extends User{

    public Supporter(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        setUserType();
    }
    @Override
    public void setUserType() {
        userType = UserType.Supporter;
    }
}
