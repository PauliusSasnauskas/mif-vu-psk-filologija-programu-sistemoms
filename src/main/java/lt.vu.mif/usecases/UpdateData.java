package lt.vu.mif.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Parcel;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import org.primefaces.context.RequestContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Model
@Getter @Setter
public class UpdateData  implements Serializable {

    @Inject
    private ParcelsDAO parcelsDAO;

    private Parcel parcelToUpdate;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if(requestParameters.get("id")!=null){
            Integer parcelId = Integer.parseInt(requestParameters.get("id"));
            this.parcelToUpdate = parcelsDAO.findOne(parcelId);}
    }

    @Transactional
    public void updateParcelData() {
        try{
            Thread.sleep(6000);
            parcelsDAO.merge(this.parcelToUpdate);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("parcel.xhtml?faces-redirect=true&id="+this.parcelToUpdate.getId());
        } catch (OptimisticLockException e) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmDialog').show();");
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public String updateParcelAfterLock() {

        parcelsDAO.merge(parcelToUpdate);

        return "parcel.xhtml?faces-redirect=true&id="+this.parcelToUpdate.getId();
    }

    public String dontUpdateParcelAfterLock() {

        return "parcel.xhtml?faces-redirect=true&id="+this.parcelToUpdate.getId();
    }
}
