package lt.vu.mif.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Parcel;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Model
@Getter
@Setter
@SessionScoped
public class SendParcel implements Serializable {

    @Inject
    private ParcelsDAO parcelsDAO;

    private Parcel parcelToSend = new Parcel();

    private List<String> selectedOptions = new ArrayList<>();

    private MathContext priceMC = new MathContext(3, RoundingMode.HALF_UP);

    public String goToPayment(){
        return "send3.xhtml";
    }

    public String goToParcelParameters(){
        return "send2.xhtml";
    }

    public String goToRecipientParameters(){
        return "send1.xhtml";
    }

    public String goToHomePage(){
        return "index.xhtml";
    }

    private final Map<String, BigDecimal> priceOptions = new HashMap<String, BigDecimal>() {{
        put("fragile", new BigDecimal("5.00"));
        put("signDocument", new BigDecimal("3.00"));
        put("donateToChildren", new BigDecimal("2.00"));
        put("sustainable", new BigDecimal("30.00"));
    }};

    // Feel free to change countryOptions and CountryPriceOptions maps
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

    public void calcPrice() {
        BigDecimal price = new BigDecimal("0.00", priceMC);

        price = price.add(regionPriceOptions.get(countryRegionsOptions.get(parcelToSend.getCountry())), priceMC);
        price.setScale(2, priceMC.getRoundingMode());


        if (parcelToSend.getLength() > 50 || parcelToSend.getHeight() > 50 || parcelToSend.getWidth() > 50) {
            price = price.add(new BigDecimal("10.00"), priceMC);
        }

        for (String option : selectedOptions) {
            if (option.equals("fragile")) {
                BigDecimal weightBD = BigDecimal.valueOf(parcelToSend.getWeight());
                weightBD = weightBD.setScale(3, priceMC.getRoundingMode());
                price = price.add((priceOptions.get(option)).multiply(weightBD, priceMC), priceMC);
            } else {
                price = price.add(priceOptions.get(option), priceMC);

            }
        }

        parcelToSend.setPrice(price.setScale(2, priceMC.getRoundingMode()));
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("senderInfoForm:currentPrice");
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
        put("fragile", "Dūžtanti siunta, (+5€ * masė)");
        put("signDocument", "Priimant būtina pasirašyti dokumentą (+3€)");
        put("donateToChildren", "Paremti globos namuose esančius vaikus (+2€)");
        put("sustainable", "Siųsti gamtą tausojančiu būdu (siuntą siųs kurjeris su dviračiu) (+30€)");
    }};

    private String getTodaysDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Transactional
    public String commitSend(){
        parcelToSend.setSentDate(getTodaysDate());
        parcelToSend.setStatus("sent");        // placeholder status
        parcelsDAO.persist(parcelToSend);
        parcelToSend = new Parcel();
        System.out.println(parcelToSend.toString());
        return "index.xhtml";
    }
}
