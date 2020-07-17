package model;


public class Supporter extends User{

    private boolean isOnline = false;

    public Supporter(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        setUserType();
    }
    @Override
    public void setUserType() {
        userType = UserType.Supporter;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void online(){
        isOnline = true;
    }
    public void offline(){
        isOnline = false;
    }
}
