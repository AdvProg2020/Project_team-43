package model;

public enum State {;
    public enum ProductState{
        confirmed, creatingProcess, editingProcess
    }
    public enum OffState{
        confirmed, creatingProcess, editingProcess
    }
    public enum OpinionState{
        unconfirmed, confirmed, waitingConfirmation
    }
    public enum offRequestState{
        edit, add
    }
}
