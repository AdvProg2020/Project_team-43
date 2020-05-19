package model;

public enum State {;
    public enum ProductState{
        CONFIRMED, CREATING_PROCESS, EDITING_PROCESS
    }
    public enum OffState{
        CONFIRMED, CREATING_PROCESS, EDITING_PROCESS
    }
    public enum OpinionState{
        UNCONFIRMED, CONFIRMED, WAITING_CONFIRMATION
    }
}
