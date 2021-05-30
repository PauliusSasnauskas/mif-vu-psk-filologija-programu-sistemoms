package lt.vu.mif.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Parcel;
import lt.vu.mif.logic.PaymentProcessor;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model
@Getter
@Setter
@ConversationScoped
public class SendParcel implements Serializable {

    @Inject
    private ParcelsDAO parcelsDAO;

    private Parcel parcelToSend = new Parcel();

    private List<String> selectedOptions = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    @Inject
    private Conversation conversation;

    public void initConversation() {
        if(!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()){
            conversation.begin();
        }
    }

    public String goToPayment(){
        return "send3?faces-redirect=true";
    }

    public String goToParcelParameters(){
        return "send2?faces-redirect=true";
    }

    public String goToRecipientParameters(){
        return "send1?faces-redirect=true";
    }

    public String goToHomePage(){

        if(!conversation.isTransient()) {
            System.out.println("!!!!! Conversation ended at home !!!!!");
            conversation.end();
        }

        return "index.xhtml";
    }

    private final Map<String, BigDecimal> priceOptions = new HashMap<String, BigDecimal>() {{
        put("fragile", new BigDecimal("5.00"));
        put("signDocument", new BigDecimal("3.00"));
        put("donateToChildren", new BigDecimal("2.00"));
        put("sustainable", new BigDecimal("30.00"));
    }};

    private final List<String> countryOptions = new ArrayList<String>(){{
       add("Lietuva");
       add("Latvija");
       add("Kuba");
    }};

    private final Map<String, String> countryRegionsOptions = new HashMap<String, String>() {{
        put("Lietuva", "LTU");
        put("Latvija", "ES");
        put("Kuba", "NOT_ES");
    }};

    private final Map<String, BigDecimal> regionPriceOptions = new HashMap<String, BigDecimal>() {{
        put("LTU", new BigDecimal("3.00"));
        put("ES", new BigDecimal("2.00"));
        put("NOT_ES", new BigDecimal("1.00"));
    }};

    public BigDecimal getCountryPrice(){
        return regionPriceOptions.get(countryRegionsOptions.get(parcelToSend.getRecipientCountry()));
    }
    public boolean isDimensionsOverLimits(){
        return parcelToSend.getLength() > 50 || parcelToSend.getHeight() > 50 || parcelToSend.getWidth() > 50;
    }

    public void calcPrice() {
        BigDecimal price = new BigDecimal("0");
        price = price.setScale(2, RoundingMode.HALF_UP);

        price = price.add(getCountryPrice());

        if (isDimensionsOverLimits()) {
            price = price.add(new BigDecimal("10.00"));
        }

        for (String option : selectedOptions) {
            if (option.equals("fragile")) {
                BigDecimal weightBD = BigDecimal.valueOf(parcelToSend.getWeight());
                weightBD = weightBD.setScale(3, RoundingMode.HALF_UP);
                price = price.add((priceOptions.get(option)).multiply(weightBD));

                BigDecimal fragilePrice = (priceOptions.get(option)).multiply(weightBD);

                priceOptionsNames.put("fragile", "Dūžtanti siunta (+" + fragilePrice.setScale(2, RoundingMode.HALF_UP) + "€)");
            } else {
                price = price.add(priceOptions.get(option));
            }
        }

        parcelToSend.setPrice(price.setScale(2, RoundingMode.HALF_UP));

        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("senderInfoPriceForm:currentPrice");
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("parcelInfoForm:currentPrice");

    }

    private final Map<Integer, String> payOptions = new HashMap<Integer, String>() {{
        put(Parcel.PAY_OPTION_BANK, "Internetine bankininkyste");
        put(Parcel.PAY_OPTION_CASH, "Grynaisiais (paėmimo metu)");
        put(Parcel.PAY_OPTION_PAYPAL, "PayPal");
    }};

    public List<Map.Entry<Integer, String>> getPayOptions(){
        return new ArrayList<>(payOptions.entrySet());
    }

    public List<Map.Entry<String, BigDecimal>> getPriceOptions(){
        return new ArrayList<>(priceOptions.entrySet());
    }

    private final Map<String, String> priceOptionsNames = new HashMap<String, String>() {{
        put("fragile", "Dūžtanti siunta, (+5€ * kg)");
        put("signDocument", "Priimant būtina pasirašyti dokumentą (+3€)");
        put("donateToChildren", "Paremti globos namuose esančius vaikus (+2€)");
        put("sustainable", "Siųsti gamtą tausojančiu būdu (siuntą siųs kurjeris su dviračiu) (+30€)");
    }};

    @Inject
    private PaymentProcessor processor;

    public String commitSend(){
        processor.process(parcelToSend);

        parcelToSend = new Parcel();

        if(!conversation.isTransient()) {
            System.out.println("!!!!! Conversation ended at home !!!!!");
            conversation.end();
        }

        return "index.xhtml";
    }
}
