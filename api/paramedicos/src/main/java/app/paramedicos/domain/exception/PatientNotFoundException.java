package app.paramedicos.domain.exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg){
        super(msg);
    }
}
