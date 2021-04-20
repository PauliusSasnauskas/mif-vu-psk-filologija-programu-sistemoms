package lt.vu.mif.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Parcel;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import java.io.Serializable;
import java.util.List;

@Model
@Getter
@Setter
@SessionScoped
public class SendParcel implements Serializable {

    private Parcel parcelToSend = new Parcel();

    private List<String> selectedOptions;

    public String goToPayment(){
        return "send3.xhtml";
    }
}
